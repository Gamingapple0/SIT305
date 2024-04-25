package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.currentUser;
import static com.example.personalizedlearningexperiences.MainActivity.generatedQuestions;
import static com.example.personalizedlearningexperiences.MainActivity.resultUrls;
import static com.example.personalizedlearningexperiences.dashboard.randomInterest;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyQuestionViewAdapter extends RecyclerView.Adapter<MyQuestionViewAdapter.ViewHolder> {

    private List<Question> questionList;

    public MyQuestionViewAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question, parent, false);
        return new ViewHolder(view);
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





    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Question question = questionList.get(position);
        // Set click listener for expand button
        holder.questionText.setText((position+1) + ". Question " + (position+1));
        holder.questionText1.setText((position+1) + ". Question " + (position+1));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 0, 8); // Adjust margins as needed

        TextView questionTextView = new TextView(holder.itemView.getContext());
        questionTextView.setText(question.question);
        holder.expandedContent.addView(questionTextView, layoutParams);

        RadioGroup radioGroup = new RadioGroup(holder.itemView.getContext());
        radioGroup.setLayoutParams(layoutParams);
        for (int i = 0; i < question.options.size(); i++) {
            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            String stringifiedId = String.valueOf("1" + position + i);
            radioButton.setId(Integer.parseInt(stringifiedId)); // Set a unique ID for each radio button
            radioButton.setText(question.options.get(i));
            radioButton.setLayoutParams(layoutParams);
            radioGroup.addView(radioButton);
        }
        String stringifiedId1 = String.valueOf("1" + position);
        radioGroup.setId(Integer.parseInt(stringifiedId1));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioGroupId = group.getId();
                String stringifiedId = String.valueOf(checkedId);
                RadioButton selectedRadioButton = group.findViewById(Integer.parseInt(stringifiedId));
                String selectedOption = selectedRadioButton.getText().toString();

                // Encode question and answer parameters
                String encodedParams = null;
                try {
                    encodedParams = "question=" + URLEncoder.encode(question.question, "UTF-8")
                            + "&answer=" + URLEncoder.encode(selectedOption, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // Encode each option and concatenate them into a single string
                StringBuilder optionsBuilder = new StringBuilder();
                for (String option : question.options) {
                    try {
                        optionsBuilder.append(URLEncoder.encode(option, "UTF-8")).append("+or+");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                // Remove the trailing " or "
                String encodedOptions = optionsBuilder.substring(0, optionsBuilder.length() - 4);

                // Construct the full URL with encoded parameters
                String baseUrl = "http://10.0.2.2:5000/getRes?";
                String fullUrl = baseUrl + encodedParams + "&options=" + encodedOptions;
                Log.i("AshUrl",fullUrl);
                resultUrls.put(radioGroupId,fullUrl);
                generatedQuestions.get(radioGroupId-10).chosenAnswer = selectedOption;

//                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,fullUrl, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
////                        parseJsonResponse(response);
//                        Log.i("AshRes",response.toString());
//                    }
//
//
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("AshRes", String.valueOf(error));
//                    }
//                });
//
//                request.setRetryPolicy(new DefaultRetryPolicy(
//                        120000, // Timeout in milliseconds (2 minutes)
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // Number of retries
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//                Volley.newRequestQueue(group.getContext()).add(request);

                // Compare the selected option with the correct answer
                if (selectedOption.equals(question.options.get(toIndex(question.correctAnswer)))) {
                    // Correct answer selected
                    Log.i("AshSel","Correct");
                    Log.i("AshSel",selectedOption);
                    Log.i("AshSel",question.correctAnswer);

                } else {
                    // Incorrect answer selected
                    Log.i("AshSel",selectedOption);
                    Log.i("AshSel",question.correctAnswer);

                }
            }
        });

        holder.expandedContent.addView(radioGroup);



        holder.expandButton.setOnClickListener(v -> {
            if (holder.expandedContent.getVisibility() == View.VISIBLE) {
                holder.expandedContent.setVisibility(View.GONE);
                holder.expandButton.setText("Expand");
            }
            else {
                holder.expandedContent.setVisibility(View.VISIBLE);
                holder.expandButton.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button expandButton;
        public LinearLayout expandedContent;
        TextView questionText;
        TextView questionText1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.questionText);
            questionText1 = itemView.findViewById(R.id.questionText1);
            expandButton = itemView.findViewById(R.id.expandButton);
            expandedContent = itemView.findViewById(R.id.expandedContent);
        }
    }
}

