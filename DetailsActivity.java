package com.example.transportapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.app.DatePickerDialog;
import java.util.Calendar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private Spinner sprTrans;
    private EditText txtDistance;
    private EditText txtCost;
    private Button txtDays;
    private Button btnCalc;
    private Button btnClear;
    private Button btnBack;
    private int travelDays = 0;
    private Calendar startDate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);

        sprTrans = findViewById(R.id.sprTrans);
        txtDistance = findViewById(R.id.txtDistance);
        txtCost = findViewById(R.id.txtCost);
        txtDays = findViewById(R.id.txtDays);
        btnCalc = findViewById(R.id.btnCalc);
        btnClear = findViewById(R.id.btnClear);
        btnBack = findViewById(R.id.btnBack);

        txtDays.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    DetailsActivity.this,
                    (view, year, month, dayOfMonth) -> {


                        startDate = Calendar.getInstance();
                        startDate.set(year, month, dayOfMonth);

                        Calendar endCalendar = Calendar.getInstance();

                        DatePickerDialog endPicker = new DatePickerDialog(
                                DetailsActivity.this,
                                (endView, endYear, endMonth, endDayOfMonth) -> {

                                    Calendar endDate = Calendar.getInstance();
                                    endDate.set(
                                            endYear,
                                            endMonth,
                                            endDayOfMonth
                                    );

                                    if (endDate.before(startDate)) {

                                        txtDays.setError(
                                                "End date cannot be before start date"
                                        );

                                        travelDays = 0;
                                        return;
                                    }

                                    long difference =
                                            endDate.getTimeInMillis()
                                                    - startDate.getTimeInMillis();

                                    travelDays =
                                            (int) (difference
                                                    / (1000 * 60 * 60 * 24))
                                                    + 1;

                                    txtDays.setText(
                                            travelDays + " day(s) selected"
                                    );
                                },
                                endCalendar.get(Calendar.YEAR),
                                endCalendar.get(Calendar.MONTH),
                                endCalendar.get(Calendar.DAY_OF_MONTH)
                        );

                        endPicker.show();




                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });




        String[] transportTypes = {
                "Car",
                "Bus",
                "Taxi",
                "Train"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                transportTypes
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sprTrans.setAdapter(adapter);


        btnBack.setOnClickListener(v -> {
            finish();
        });

        btnClear.setOnClickListener(v -> {
            txtDistance.setText("");
            txtCost.setText("");
            txtDays.setText("Select Travel Dates");
            travelDays = 0;
        });

        btnCalc.setOnClickListener(v -> {
            String distanceText = txtDistance.getText().toString();
            String costText = txtCost.getText().toString();

            if (distanceText.isEmpty()) {
                txtDistance.setError("Please enter the distance");
                return;
            }

            if (costText.isEmpty()) {
                txtCost.setError("Please enter the cost per kilometre");
                return;
            }

            double distance;
            double cost;
            int days = travelDays;

            try {
                distance = Double.parseDouble(distanceText);
                cost = Double.parseDouble(costText);
            } catch (NumberFormatException e) {
                return;
            }

            if (distance <= 0) {
                txtDistance.setError("Distance must be greater than 0");
                return;
            }

            if (cost <= 0) {
                txtCost.setError("Cost must be greater than 0");
                return;
            }

            if (days <= 0) {
                txtDays.setError("Travel days must be greater than 0");
                return;
            }

            // Create the calculator
            TransportCalculator calculator = new TransportCalculator();

            // Perform the calculations
            double dailyCost =
                    calculator.calculateDailyCost(distance, cost);

            double monthlyCost =
                    calculator.calculateMonthlyCost(dailyCost, days);

            double monthlyDistance =
                    calculator.calculateMonthlyDistance(distance, days);
            String transportType = sprTrans.getSelectedItem().toString();

            Transport transport = new Transport(
                    transportType,
                    distance,
                    cost,
                    days
            );




            Intent intent = new Intent(DetailsActivity.this, ResultsActivity.class);

            intent.putExtra("transportType", transportType);
            intent.putExtra("dailyCost", dailyCost);
            intent.putExtra("monthlyCost", monthlyCost);
            intent.putExtra("monthlyDistance", monthlyDistance);

            startActivity(intent);
        });
    }
}