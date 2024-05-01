package com.example.personalizedlearningexperiences;

import static com.example.personalizedlearningexperiences.MainActivity.generatedQuestions;
import static com.example.personalizedlearningexperiences.MainActivity.resultUrls;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MyHistoryViewAdapter extends RecyclerView.Adapter<MyHistoryViewAdapter.ViewHolder> {

    private List<Question> questionList;

    public MyHistoryViewAdapter(List<Question> questionList) {
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





    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Question question = questionList.get(position);
        // Set click listener for expand button
        holder.questionText.setText((position+1) + ". Question " + (position+1));
        holder.questionText1.setText((position+1) + ". Question " + (position+1));
        holder.itemView.findViewById(R.id.timeText).setVisibility(View.VISIBLE);
        TextView timeTextView = (TextView) holder.itemView.findViewById(R.id.timeText);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(question.timeStamp.getTime());
        timeTextView.setText(formattedDate + " | " + question.topic);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 8, 0, 8); // Adjust margins as needed

        TextView questionTextView = new TextView(holder.itemView.getContext());
        questionTextView.setText(question.question);
            if(holder.expandedContent.getChildCount() < 3) {
                holder.expandedContent.addView(questionTextView, layoutParams);
            }
        RadioGroup radioGroup = new RadioGroup(holder.itemView.getContext());
        radioGroup.setLayoutParams(layoutParams);
        for (int i = 0; i < question.options.size(); i++) {
            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            String stringifiedId = String.valueOf("1" + position + i);
            radioButton.setId(Integer.parseInt(stringifiedId)); // Set a unique ID for each radio button
            if(Objects.equals(question.chosenAnswer, question.options.get(toIndex(question.correctAnswer))) && question.chosenAnswer == question.options.get(i)){
                radioButton.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lime));
                radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.lime)));
                radioButton.setChecked(true);
            }
            else{
                if (Objects.equals(question.chosenAnswer, question.options.get(i))){
                    radioButton.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
                    radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.red)));
                }
                if(Objects.equals(question.options.get(toIndex(question.correctAnswer)), question.options.get(i))){
                    radioButton.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.lime));
                    radioButton.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.lime)));
                    radioButton.setChecked(true);
                }
            }
            radioButton.setText(question.options.get(i));
            radioButton.setLayoutParams(layoutParams);
            radioGroup.addView(radioButton);
        }
        String stringifiedId1 = String.valueOf("1" + position);
        radioGroup.setId(Integer.parseInt(stringifiedId1));


//        for (String opt: question.options){
//            if(question.chosenAnswer == question.options.get(toIndex(question.correctAnswer))){
//                    int correctAnswer = "1" + position +
////                View view = holder.itemView.findViewById(R.id.111);
//
//
//            }
//        }


        if(holder.expandedContent.getChildCount() < 3){
            Log.i("AshExpand", String.valueOf(holder.expandedContent.getChildCount()));
            holder.expandedContent.addView(radioGroup);
        }



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

