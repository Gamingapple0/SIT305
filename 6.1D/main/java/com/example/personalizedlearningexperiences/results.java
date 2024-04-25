package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.currentUser;
import static com.example.personalizedlearningexperiences.MainActivity.generatedQuestions;
import static com.example.personalizedlearningexperiences.MainActivity.resultUrls;
import static com.example.personalizedlearningexperiences.dashboard.randomInterest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.example.personalizedlearningexperiences.databinding.FragmentResultsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class results extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentResultsBinding binding;

    public results() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment results.
     */
    // TODO: Rename and change types and number of parameters
    public static results newInstance(String param1, String param2) {
        results fragment = new results();
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

    public void parseJsonResponse(JSONObject response, Integer view_id) throws JSONException {
        switch (view_id){
            case 1:
                binding.questionText1.setText(generatedQuestions.get(view_id - 1).question);
                binding.answerText1.setText(response.getString("res"));
                return;
            case 2:
                binding.questionText2.setText(generatedQuestions.get(view_id - 1).question);
                binding.answerText2.setText(response.getString("res"));
                return;
            case 3:
                binding.questionText3.setText(generatedQuestions.get(view_id - 1).question);
                binding.answerText3.setText(response.getString("res"));
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentResultsBinding.inflate(inflater,container,false);

        for (Map.Entry<Integer, String> entry : resultUrls.entrySet()) {
            String url = entry.getValue();
            Integer index = entry.getKey()-10;

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        parseJsonResponse(response, index + 1);
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
        }

        return binding.getRoot();
    }
}