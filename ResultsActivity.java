package com.example.transportapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    private TextView txtDailyCost;
    private TextView txtMonthlyCost;
    private TextView txtMonthlyDistance;
    private TextView txtCategory;
    private TextView txtRecom;
    private Button btnSave;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);

        txtDailyCost = findViewById(R.id.txtDailyCost);
        txtMonthlyCost = findViewById(R.id.txtMonthlyCost);
        txtMonthlyDistance = findViewById(R.id.txtMonthlyDistance);
        txtCategory = findViewById(R.id.txtCategory);
        txtRecom = findViewById(R.id.txtRecom);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);


        btnBack.setOnClickListener(v ->
        {
            finish();

        });


        double dailyCost = getIntent().getDoubleExtra("dailyCost", 0);
        double monthlyCost = getIntent().getDoubleExtra("monthlyCost", 0);
        double monthlyDistance = getIntent().getDoubleExtra("monthlyDistance", 0);

        String transportType = getIntent().getStringExtra("transportType");


        btnSave.setOnClickListener(v -> {

            Database database = new Database(ResultsActivity.this);

            database.saveReport(
                    transportType,
                    dailyCost,
                    monthlyCost,
                    monthlyDistance
            );

            database.close();

            Toast.makeText(
                    ResultsActivity.this,
                    "Report saved successfully!",
                    Toast.LENGTH_SHORT
            ).show();
        });

        txtDailyCost.setText(String.format("R%.2f", dailyCost));
        txtMonthlyCost.setText(String.format("R%.2f", monthlyCost));
        txtMonthlyDistance.setText(String.format("%.2f km", monthlyDistance));

        Recommendation recommendation = new Recommendation();

        String category = recommendation.getCategory(monthlyCost);
        String recommendationText = recommendation.getRecommendation(category);

        txtCategory.setText(category);
        txtRecom.setText(recommendationText);

    }

}