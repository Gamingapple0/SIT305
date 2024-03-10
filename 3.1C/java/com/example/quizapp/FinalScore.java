package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinalScore extends AppCompatActivity {
    private int score = 0;
    private int total = 0;
    private TextView scoreText;
    private Button restartQuizButton;
    private Button finishButton;
    private TextView scoreComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);

        score = getIntent().getIntExtra("Score", 0);
        total = getIntent().getIntExtra("Total", 0);
        String name = getIntent().getStringExtra("Name");

        finishButton = findViewById(R.id.finishButton);
        scoreText = findViewById(R.id.scoreText);
        restartQuizButton = findViewById(R.id.restartQuizButton);
        scoreComment = findViewById(R.id.scoreComment);

        scoreText.setText(score + "/" + total);

        double percentage = (score * 100.0) / total;
        if (percentage < 50) {
            scoreComment.setText("Sorry " + name + " but you only got a " + percentage + "% score");
        } else {
            scoreComment.setText("Congrats! " + name + " on getting a " + percentage + "% score");
        }

        restartQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("Name", name);
                startActivity(intent);
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}
