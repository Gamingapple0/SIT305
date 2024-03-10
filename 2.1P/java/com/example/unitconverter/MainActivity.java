package com.example.unitconverter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText num;
    private Spinner dropDown;
    private Spinner dropDown2;
    private Button calculateButton;
    private TextView convertedValue;
    private ImageButton lengthButton;
    private ImageButton tempButton;
    private ImageButton weightButton;
    private String selectedOption;
    private String selectedOption2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num = findViewById(R.id.value);
        dropDown = findViewById(R.id.dropDown);
        dropDown2 = findViewById(R.id.dropDown2);
        calculateButton = findViewById(R.id.calculate);
        convertedValue = findViewById(R.id.textView1);
        lengthButton = findViewById(R.id.lengthButton);
        weightButton = findViewById(R.id.weightButton);
        tempButton = findViewById(R.id.tempButton);

        String[] lengthChoices = {"Mile", "Foot", "Inches", "Yard"};
        String[] tempChoices = {"C", "K", "F", ""};
        String[] weightChoices = {"Kg", "Lbs", "Oz", "Ton"};

        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthChoices);
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tempChoices);
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightChoices);

        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        dropDown.setAdapter(weightAdapter);
        dropDown2.setAdapter(weightAdapter);

        num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculate();
                Log.i(TAG, "Calculate Done");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedOption = dropDown.getSelectedItem().toString();
                selectedOption2 = dropDown2.getSelectedItem().toString();
                calculate();
                Toast.makeText(getApplicationContext(), selectedOption, Toast.LENGTH_LONG).show();
            }
        });

        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = dropDown.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), selectedOption, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dropDown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption2 = dropDown2.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), selectedOption2, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        lengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDown.setAdapter(lengthAdapter);
                dropDown2.setAdapter(lengthAdapter);
            }
        });

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDown.setAdapter(tempAdapter);
                dropDown2.setAdapter(tempAdapter);
            }
        });

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDown.setAdapter(weightAdapter);
                dropDown2.setAdapter(weightAdapter);
            }
        });
    }

    private void calculate() {
        Log.i("MainActivity", "Calculate Triggered");
        if (num.getText().toString().isEmpty()) {
            return;
        }


        if (selectedOption.equals("Kg")) {
            double kilograms = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Lbs":
                    // Convert to pounds
                    double pounds = Double.parseDouble(String.format("%.2f", kilograms * 2.20462));
                    convertedValue.setText(pounds + " lbs");
                    break;
                case "Oz":
                    // Convert to ounces (1 pound = 16 ounces)
                    double ounces = Double.parseDouble(String.format("%.2f", kilograms * 2.20462 * 16));
                    convertedValue.setText(ounces + " Oz");
                    break;
                case "Ton":
                    // Convert to ton (1 pound = 453.592 grams)
                    double tons = Double.parseDouble(String.format("%.2f", kilograms / 907.185));
                    convertedValue.setText(tons + " Tons");
                    break;
                default:
                    convertedValue.setText(kilograms + " Kg");
            }
        } else if (selectedOption.equals("Lbs")) {
            double pounds = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Oz":
                    // Convert to ounces (1 pound = 16 ounces)
                    double ounces = Double.parseDouble(String.format("%.2f", pounds * 16));
                    convertedValue.setText(ounces + " Oz");
                    break;
                case "Ton":
                    // Convert to ton (1 pound = 453.592 grams)
                    double tons = Double.parseDouble(String.format("%.2f", pounds / 907.185));
                    convertedValue.setText(tons + " Tons");
                    break;
                case "Kg":
                    // Convert to kilograms
                    double kilograms = Double.parseDouble(String.format("%.2f", pounds / 2.20462));
                    convertedValue.setText(kilograms + " Kg");
                    break;
                default:
                    convertedValue.setText(pounds + " lbs");
            }
        } else if (selectedOption.equals("Oz")) {
            double ounces = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Lbs":
                    // Convert to pounds
                    double pounds = Double.parseDouble(String.format("%.2f", ounces / 16));
                    convertedValue.setText(pounds + " lbs");
                    break;
                case "Ton":
                    // Convert to ton (1 pound = 453.592 grams)
                    double tons = Double.parseDouble(String.format("%.2f", ounces / 35273.962));
                    convertedValue.setText(tons + " Tons");
                    break;
                case "Kg":
                    // Convert to kilograms
                    double kilograms = Double.parseDouble(String.format("%.2f", ounces / (2.20462 * 16)));
                    convertedValue.setText(kilograms + " Kg");
                    break;
                default:
                    convertedValue.setText(ounces + " Oz");
            }
        } else if (selectedOption.equals("Ton")) {
            double tons = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Lbs":
                    // Convert to pounds
                    double pounds = Double.parseDouble(String.format("%.2f", tons * 2000));
                    convertedValue.setText(pounds + " lbs");
                    break;
                case "Oz":
                    // Convert to ounces (1 pound = 16 ounces)
                    double ounces = Double.parseDouble(String.format("%.2f", tons * 2000 * 16));
                    convertedValue.setText(ounces + " Oz");
                    break;
                case "Kg":
                    // Convert to kilograms
                    double kilograms = Double.parseDouble(String.format("%.2f", tons * 907.185));
                    convertedValue.setText(kilograms + " Kg");
                    break;
                default:
                    convertedValue.setText(tons + " Tons");
            }
        }

        // Assuming selectedOption is previously defined

        if (selectedOption.equals("Mile")) {
            double miles = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Foot":
                    // Convert to feet (1 mile = 5280 feet)
                    double feet = Double.parseDouble(String.format("%.2f", miles * 5280));
                    convertedValue.setText(feet + " ft");
                    break;
                case "Inches":
                    // Convert to inches (1 mile = 5280 feet * 12 inches)
                    double inches = Double.parseDouble(String.format("%.2f", miles * 5280 * 12));
                    convertedValue.setText(inches + " inches");
                    break;
                case "Yard":
                    // Convert to yards (1 mile = 1760 yards)
                    double yards = Double.parseDouble(String.format("%.2f", miles * 1760));
                    convertedValue.setText(yards + " yards");
                    break;
                default:
                    convertedValue.setText(miles + " Miles");
            }
        } else if (selectedOption.equals("Foot")) {
            double feet = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Mile":
                    // Convert to miles
                    double miles = Double.parseDouble(String.format("%.2f", feet / 5280));
                    convertedValue.setText(miles + " Miles");
                    break;
                case "Inches":
                    // Convert to inches (1 foot = 12 inches)
                    double inches = Double.parseDouble(String.format("%.2f", feet * 12));
                    convertedValue.setText(inches + " inches");
                    break;
                case "Yard":
                    // Convert to yards (1 yard = 3 feet)
                    double yards = Double.parseDouble(String.format("%.2f", feet / 3));
                    convertedValue.setText(yards + " yards");
                    break;
                default:
                    convertedValue.setText(feet + " ft");
            }
        } else if (selectedOption.equals("Inches")) {
            double inches = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Mile":
                    // Convert to miles
                    double miles = Double.parseDouble(String.format("%.2f", inches / (5280 * 12)));
                    convertedValue.setText(miles + " Miles");
                    break;
                case "Foot":
                    // Convert to feet (1 inch = 0.0833333 feet)
                    double feet = Double.parseDouble(String.format("%.2f", inches * 0.0833333));
                    convertedValue.setText(feet + " ft");
                    break;
                case "Yard":
                    // Convert to yards (1 yard = 36 inches)
                    double yards = Double.parseDouble(String.format("%.2f", inches / 36));
                    convertedValue.setText(yards + " yards");
                    break;
                default:
                    convertedValue.setText(inches + " inches");
            }
        } else if (selectedOption.equals("Yard")) {
            double yards = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "Mile":
                    // Convert to miles (1 mile = 1760 yards)
                    double miles = Double.parseDouble(String.format("%.2f", yards / 1760));
                    convertedValue.setText(miles + " Miles");
                    break;
                case "Foot":
                    // Convert to feet (1 yard = 3 feet)
                    double feet = Double.parseDouble(String.format("%.2f", yards * 3));
                    convertedValue.setText(feet + " ft");
                    break;
                case "Inches":
                    // Convert to inches (1 foot = 12 inches)
                    double inches = Double.parseDouble(String.format("%.2f", yards * 3 * 12));
                    convertedValue.setText(inches + " inches");
                    break;
                default:
                    convertedValue.setText(yards + " yards");
            }
        }

        // Assuming selectedOption and selectedOption2 are previously defined

        if (selectedOption.equals("C")) {
            double celsius = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "K":
                    // Convert to Kelvin
                    double kelvin = Double.parseDouble(String.format("%.2f", celsius + 273.15));
                    convertedValue.setText(kelvin + " K");
                    break;
                case "F":
                    // Convert to Fahrenheit
                    double fahrenheit = Double.parseDouble(String.format("%.2f", celsius * 9 / 5 + 32));
                    convertedValue.setText(fahrenheit + " °F");
                    break;
                default:
                    convertedValue.setText(celsius + " °C");
            }
        } else if (selectedOption.equals("K")) {
            double kelvin = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "C":
                    // Convert to Celsius
                    double celsius = Double.parseDouble(String.format("%.2f", kelvin - 273.15));
                    convertedValue.setText(celsius + " °C");
                    break;
                case "F":
                    // Convert to Fahrenheit
                    double fahrenheit = Double.parseDouble(String.format("%.2f", (kelvin - 273.15) * 9 / 5 + 32));
                    convertedValue.setText(fahrenheit + " °F");
                    break;
                default:
                    convertedValue.setText(kelvin + " K");
            }
        } else if (selectedOption.equals("F")) {
            double fahrenheit = Double.parseDouble(num.getText().toString());

            switch (selectedOption2) {
                case "C":
                    // Convert to Celsius
                    double celsius = Double.parseDouble(String.format("%.2f", (fahrenheit - 32) * 5 / 9));
                    convertedValue.setText(celsius + " °C");
                    break;
                case "K":
                    // Convert to Kelvin
                    double kelvin = Double.parseDouble(String.format("%.2f", (fahrenheit - 32) * 5 / 9 + 273.15));
                    convertedValue.setText(kelvin + " K");
                    break;
                default:
                    convertedValue.setText(fahrenheit + " °F");
            }
        }
    }

}
