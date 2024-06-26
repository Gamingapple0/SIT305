package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.currentUser;
import static com.example.personalizedlearningexperiences.MainActivity.generatedQuestions;
import static com.example.personalizedlearningexperiences.MainActivity.resultUrls;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.personalizedlearningexperiences.databinding.FragmentProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentProfileBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profile.
     */
    // TODO: Rename and change types and number of parameters
    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private int toIndex(String letter) {
        // Assuming correctAnswer is one of: "a", "b", "c", "d"
        switch (letter) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return -1; // Invalid input
        }
    }

    private String parseSummaryResponse(JSONObject response) throws JSONException {
        return response.getString("summary");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentProfileBinding.inflate(inflater,container,false);
        binding.usernameText.setText(currentUser.getUsername());
        binding.emailText.setText(currentUser.getEmail());

        int correctAnswerCount = 0;
        int incorrectAnswerCount = 0;
        List<Question> allQuestions = currentUser.getAllQuestions();
        for (Question ques: allQuestions){
            if(Objects.equals(ques.chosenAnswer, ques.options.get(toIndex(ques.correctAnswer)))){
                correctAnswerCount++;
            }
            else{
                incorrectAnswerCount++;
            }
        }

        binding.totalQuestionText.setText(String.valueOf(correctAnswerCount + incorrectAnswerCount));
        binding.correctAnswersText.setText(String.valueOf(correctAnswerCount));
        binding.incorrectAnswersText.setText(String.valueOf(incorrectAnswerCount));

        // Encode question and answer parameters
        String encodedParams = null;
        try {
            encodedParams = "correctQuestions=" + URLEncoder.encode(String.valueOf(correctAnswerCount), "UTF-8")
                    + "&incorrectQuestions=" + URLEncoder.encode((String.valueOf(incorrectAnswerCount)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Encode each option and concatenate them into a single string
        StringBuilder interestsBuilder = new StringBuilder();
        for (String interest : currentUser.getUserInterests()) {
            try {
                interestsBuilder.append(URLEncoder.encode(interest, "UTF-8")).append("+and+");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // Remove the trailing " or "
        String encodedInterests = interestsBuilder.substring(0, interestsBuilder.length() - 4);

        // Construct the full URL with encoded parameters
        String baseUrl = "http://10.0.2.2:5000/getSummary?";
        String fullUrl = baseUrl + encodedParams + "&topics=" + encodedInterests;
        Log.i("AshUrl",fullUrl);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, fullUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    binding.aiSummaryText.setText(parseSummaryResponse(response));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Log.i("AshRes", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("AshRes", String.valueOf(error));
            }
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                120000, // Timeout in milliseconds (2 minutes)
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getContext()).add(request);




        binding.historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profile_to_history);
            }
        });

        binding.packageButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_profile_to_upgrade);
            }
        }));

        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareContent();
            }
        });


        return binding.getRoot();
    }

    private void shareContent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareMessage = "Hi, I am " + currentUser.getUsername() + ", I've solved " + currentUser.getAllQuestions().size() + " questions so far!";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}