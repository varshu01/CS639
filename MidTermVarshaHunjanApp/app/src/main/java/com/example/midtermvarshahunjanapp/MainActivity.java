package com.example.midtermvarshahunjanapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText literInput;
    private Button convertButton;
    private TextView resultText;

    private void convertLitersToCups() {
        try {
            double liters = Double.parseDouble(literInput.getText().toString());
            double cups = liters * 4.22;
            resultText.setText(String.format("%.2f liters is equal to %.2f cups", liters, cups));
        } catch (NumberFormatException e) {
            resultText.setText("Please enter a valid number of liters.");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        literInput = findViewById(R.id.literInput);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertLitersToCups();
            }
        });

    }

}