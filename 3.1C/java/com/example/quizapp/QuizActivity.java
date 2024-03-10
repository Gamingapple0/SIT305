package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView intro;
    private Button submitButton;
    private ProgressBar progressBar;
    private TextView questionIndex;
    private TextView questionText;
    private String question;
    private String chosenOption = "";
    private int score = 0;
    private boolean submitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        String name = getIntent().getStringExtra("Name");

        Map<String, Answer> computerQuestions = new HashMap<>();
        computerQuestions.put("What does CPU stand for?", new Answer("Central Processing Unit", new String[]{"Central Personal Unit", "Computer Processing Unit", "Central Processing Unit"}));
        computerQuestions.put("Which programming language is known as the 'mother of all languages'?", new Answer("C", new String[]{"Python", "Java", "C"}));
        computerQuestions.put("What is the full form of HTML?", new Answer("HyperText Markup Language", new String[]{"HyperText Markup Language", "HighText Machine Language", "HyperText and links Markup Language"}));
        computerQuestions.put("Which company developed the first graphical user interface (GUI)?", new Answer("Xerox", new String[]{"Microsoft", "Apple", "Xerox"}));
        computerQuestions.put("What is the purpose of a firewall in a computer network?", new Answer("To protect against unauthorized access", new String[]{"To protect against unauthorized access", "To enhance internet speed", "To store large amounts of data"}));

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        intro = findViewById(R.id.introText);
        submitButton = findViewById(R.id.nextButton);
        progressBar = findViewById(R.id.progressBar);
        questionIndex = findViewById(R.id.questionIndex);
        questionText = findViewById(R.id.questionText);

        progressBar.setMax(computerQuestions.size());

        final int[] index = {-1};
        final Object[] questions = computerQuestions.keySet().toArray();
        intro.setText("Welcome " + name + " !");

        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenOption = option1.getText().toString();
                Toast.makeText(getApplicationContext(), chosenOption, Toast.LENGTH_SHORT).show();
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenOption = option2.getText().toString();
                Toast.makeText(getApplicationContext(), chosenOption, Toast.LENGTH_SHORT).show();
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenOption = option3.getText().toString();
                Toast.makeText(getApplicationContext(), chosenOption, Toast.LENGTH_SHORT).show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index[0] >= 0) {
                    intro.setText("");
                }
                if (submitted) {
                    if (index[0] == computerQuestions.size()) {
                        Intent newIntent = new Intent(v.getContext(), FinalScore.class);
                        newIntent.putExtra("Name", name);
                        newIntent.putExtra("Score", score);
                        newIntent.putExtra("Total", computerQuestions.size());
                        startActivity(newIntent);
                    }
                    index[0] = nextQuestion(index[0], computerQuestions, questions);
                    chosenOption = "";
                    submitted = false;
                    submitButton.setText("Submit");
                    option1.setBackgroundColor(getResources().getColor(R.color.purple));
                    option2.setBackgroundColor(getResources().getColor(R.color.purple));
                    option3.setBackgroundColor(getResources().getColor(R.color.purple));
                }
                else {
                    if (index[0] == computerQuestions.size()) {
                        Intent newIntent = new Intent(v.getContext(), FinalScore.class);
                        newIntent.putExtra("Name", name);
                        newIntent.putExtra("Score", score);
                        newIntent.putExtra("Total", computerQuestions.size());
                        startActivity(newIntent);
                    }
                    if (chosenOption.equals("")) {
                        Toast.makeText(getApplicationContext(), "An Option Must be selected", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if (chosenOption.equals(computerQuestions.get(question).correctAnswer)) {
                        String selectedOption = chosenOption;

                        if (selectedOption.equals(computerQuestions.get(question).options[0])) {
                            option1.setBackgroundColor(getResources().getColor(R.color.green));
                        } else if (selectedOption.equals(computerQuestions.get(question).options[1])) {
                            option2.setBackgroundColor(getResources().getColor(R.color.green));
                        } else if (selectedOption.equals(computerQuestions.get(question).options[2])) {
                            option3.setBackgroundColor(getResources().getColor(R.color.green));

                        }

                        score++;
                    }
                    else {

                        String correctAnswer = computerQuestions.get(question).correctAnswer;
                        String option0 = computerQuestions.get(question).options[0];
                        String option1Value = computerQuestions.get(question).options[1];

                        if (chosenOption.equals(option0)) {
                            option1.setBackgroundColor(getResources().getColor(R.color.red));
                            if (option1Value.equals(correctAnswer)) {
                                option2.setBackgroundColor(getResources().getColor(R.color.green));
                            } else {
                                option3.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else if (chosenOption.equals(option1Value)) {
                            option2.setBackgroundColor(getResources().getColor(R.color.red));
                            if (option0.equals(correctAnswer)) {
                                option1.setBackgroundColor(getResources().getColor(R.color.green));
                            } else {
                                option3.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        } else if (chosenOption.equals(computerQuestions.get(question).options[2])) {
                            option3.setBackgroundColor(getResources().getColor(R.color.red));
                            if (option0.equals(correctAnswer)) {
                                option1.setBackgroundColor(getResources().getColor(R.color.green));
                            } else {
                                option2.setBackgroundColor(getResources().getColor(R.color.green));
                            }
                        }

                    }
                    submitted = true;
                    submitButton.setText("Next");
                    chosenOption = "";
                }
            }
        });
        index[0] = nextQuestion(index[0], computerQuestions, questions);
    }

    static class Answer {
        String correctAnswer;
        String[] options;

        Answer(String correctAnswer, String[] options) {
            this.correctAnswer = correctAnswer;
            this.options = options;
        }
    }

    private int nextQuestion(int index, Map<String, Answer> computerQuestions, Object[] questions) {
        index++;
        if (index == computerQuestions.size() - 1) {
            submitButton.setText("Submit");
        }
        if (index == computerQuestions.size()) {
            return computerQuestions.size();
        }
        question = (String) questions[index];
        progressBar.setProgress(index + 1);
        questionIndex.setText((index + 1) + "/" + computerQuestions.size());
        questionText.setText(question);
        option1.setText(computerQuestions.get(question).options[0]);
        option2.setText(computerQuestions.get(question).options[1]);
        option3.setText(computerQuestions.get(question).options[2]);
        return index;
    }
}
