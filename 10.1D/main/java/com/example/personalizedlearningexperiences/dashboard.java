package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.currentUser;
import static com.example.personalizedlearningexperiences.MainActivity.generatedQuestions;
import static com.example.personalizedlearningexperiences.MainActivity.interestsList;

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
import com.example.personalizedlearningexperiences.databinding.FragmentDashboardBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class dashboard extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentDashboardBinding binding;
    public static String randomInterest;

    public dashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment dashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static dashboard newInstance(String param1, String param2) {
        dashboard fragment = new dashboard();
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

    private void parseJsonResponse(JSONObject response) {
        try {
            JSONArray quizArray = response.getJSONArray("quiz");

            for (int i = 0; i < quizArray.length(); i++) {
                JSONObject questionObject = quizArray.getJSONObject(i);

                String correctAnswer = questionObject.getString("correct_answer");
                JSONArray optionsArray = questionObject.getJSONArray("options");
                List<String> options = new ArrayList<>();
                for (int j = 0; j < optionsArray.length(); j++) {
                    options.add(optionsArray.getString(j));
                }
                String questionText = questionObject.getString("question");

                // Create a Question object for each question
                LocalDate localDate = LocalDate.now();
                Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(utilDate);
                // Create a Question object for each question
                Question question = new Question(correctAnswer, options, questionText, null,  randomInterest, calendar, currentUser.getPackageType());
                generatedQuestions.add(question);

                // Do something with the Question object (e.g., add it to a list)
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater,container,false);
        Log.i("AshDash",generatedQuestions.toString());

        binding.textView.setText(currentUser.getUsername());

        binding.textView5.setVisibility(View.GONE);
        binding.textView6.setVisibility(View.GONE);
        binding.imageView3.setVisibility(View.GONE);
        binding.textView7.setVisibility(View.GONE);
        binding.openTask.setVisibility(View.GONE);
        binding.imageView4.setVisibility(View.GONE);
        binding.button.setVisibility(View.GONE);
        binding.imageView2.setVisibility(View.GONE);

        binding.openTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the desired destination
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_questions);
            }
        });

        binding.generateTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatedQuestions.clear();
                binding.loadingProgressBar.setVisibility(View.VISIBLE);
                binding.overlayView.setVisibility(View.VISIBLE);
                binding.generateTaskButton.setVisibility(View.GONE);

                // Generate a random index
                Random random = new Random();
                int randomIndex = random.nextInt(currentUser.getUserInterests().size());

                // Retrieve random interest
                randomInterest = currentUser.getUserInterests().get(randomIndex);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,"http://10.0.2.2:5000/getQuiz?topic=" + randomInterest, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        binding.loadingProgressBar.setVisibility(View.GONE);
                        binding.overlayView.setVisibility(View.GONE);

                        binding.textView5.setVisibility(View.VISIBLE);
                        binding.textView6.setVisibility(View.VISIBLE);
                        binding.imageView3.setVisibility(View.VISIBLE);
                        binding.button.setVisibility(View.VISIBLE);
                        binding.imageView2.setVisibility(View.VISIBLE);

                        binding.textView7.setText("The questions in this task are related to the " + randomInterest + " topic");
                        binding.textView7.setVisibility(View.VISIBLE);

                        binding.openTask.setVisibility(View.VISIBLE);
                        binding.imageView4.setVisibility(View.VISIBLE);

                        binding.generateTaskButton.setVisibility(View.VISIBLE);

                        parseJsonResponse(response);
                        Log.i("AshRes",response.toString());
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
            }
        });

        binding.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_dashboard_to_profile);
            }
        });

        return binding.getRoot();
    }
}