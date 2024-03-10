package com.example.quizapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText name = findViewById(R.id.name);
        if (getIntent().getStringExtra("Name") != null) {
            name.setText(getIntent().getStringExtra("Name"));
        }
        findViewById(R.id.startQuizbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                intent.putExtra("Name", name.getText().toString());
                startActivity(intent);
            }
        });
    }
}

