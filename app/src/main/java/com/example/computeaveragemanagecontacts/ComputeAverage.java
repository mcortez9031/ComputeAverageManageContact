package com.example.computeaveragemanagecontacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ComputeAverage extends AppCompatActivity {

    EditText englishGrade, filipinoGrade, mathGrade,  scienceGrade, mapehGrade;
    Button submit, back;
    Intent intent;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compute_average);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        englishGrade = findViewById(R.id.etEnglish);
        filipinoGrade = findViewById(R.id.etFilipino);
        mathGrade = findViewById(R.id.etMath);
        scienceGrade = findViewById(R.id.etScience);
        mapehGrade = findViewById(R.id.etMAPEH);
        submit = findViewById(R.id.btnSubmit);
        back = findViewById(R.id.btnBack);

        builder= new AlertDialog.Builder(this);
        computeAverage();

        intent = new Intent(ComputeAverage.this, AverageResults.class);
        back.setOnClickListener(v -> finish());

    }
    public void computeAverage() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (englishGrade.getText().toString().isEmpty() || filipinoGrade.getText().toString().isEmpty() ||
                        mathGrade.getText().toString().isEmpty() || scienceGrade.getText().toString().isEmpty() ||
                        mapehGrade.getText().toString().isEmpty()) {
                    displayMessage("Input Error!", "Please fill out all fields");
                    return;
                }

                try {

                    double doubleEnglishGrade = Double.parseDouble(englishGrade.getText().toString());
                    double doubleFilipinoGrade = Double.parseDouble(filipinoGrade.getText().toString());
                    double doubleMathGrade = Double.parseDouble(mathGrade.getText().toString());
                    double doubleScienceGrade = Double.parseDouble(scienceGrade.getText().toString());
                    double doubleMapehGrade = Double.parseDouble(mapehGrade.getText().toString());


                    if (!isGradesValid(doubleEnglishGrade, doubleFilipinoGrade, doubleMathGrade, doubleScienceGrade, doubleMapehGrade)) {
                        return;
                    }


                    double average = (doubleEnglishGrade + doubleFilipinoGrade + doubleMathGrade + doubleScienceGrade + doubleMapehGrade) / 5;


                    Intent intent = new Intent(ComputeAverage.this, AverageResults.class);
                    intent.putExtra("computedAverage", average);
                    intent.putExtra("englishGrade", doubleEnglishGrade);
                    intent.putExtra("filipinoGrade", doubleFilipinoGrade);
                    intent.putExtra("mathGrade", doubleMathGrade);
                    intent.putExtra("scienceGrade", doubleScienceGrade);
                    intent.putExtra("mapehGrade", doubleMapehGrade);
                    intent.putExtra("computedAverage", average);
                    startActivity(intent);

                } catch (NumberFormatException e) {
                    displayMessage("Format Error!", "Please enter valid numeric values.");
                }
            }
        });
    }
    public void displayMessage(String title, String message){
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public boolean isGradesValid(double englishGrade, double filipinoGrade, double mathGrade, double scienceGrade, double mapehGrade){
        if(englishGrade < 1.0 || englishGrade > 100 || filipinoGrade < 1.0 || filipinoGrade > 100 || mathGrade < 1.0 || mathGrade > 100
                || scienceGrade < 1.0 || scienceGrade > 100 || mapehGrade < 1.0 || mapehGrade > 100){
            displayMessage("Invalid Grade! ","Please enter a value between 0 and 100.");
            return false;
        }
        return true;

    }
}