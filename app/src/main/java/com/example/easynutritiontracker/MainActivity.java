// Simple utility to track calorie and carbohydrate intake
//
// Version history:
// 2022-10-07 v0.1 Initial test release
//
// TODO: Save variables
// TODO: Make RESET button harder to press (swipe or push to RESET?)

package com.example.easynutritiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    private int calories;
    private int carbs;

    SharedPreferences preferences;

    private TextView showCalories;
    private TextView showCarbs;

    private EditText inputCalories;
    private EditText inputCarbs;

    private Button addCalories;
    private Button addCarbs;
    private Button clearInputFields;
    private Button resetDailyValues;

//    public void loadVariables() {
//        int defaultCalories = getResources().getInteger(R.integer.default_calories);
//        calories = preferences.getInt(getString(R.string.saved_calories), defaultCalories);
//    }
//
//    public void saveVariables() {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt(getString(R.string.saved_calories), calories);
//        editor.apply();
//    }

    public void setupGUI() {

        showCalories = findViewById(R.id.text_showCalories);
        showCarbs = findViewById(R.id.text_showCarbs);

        inputCalories = findViewById(R.id.text_inputCalories);
        inputCarbs = findViewById(R.id.text_inputCarbs);

        addCalories = findViewById(R.id.button_addCalories);
        addCarbs = findViewById(R.id.button_addCarbs);
        clearInputFields = findViewById(R.id.button_clearInputFields);
        resetDailyValues = findViewById(R.id.button_resetDailyValues);

        addCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calories += Integer.parseInt(inputCalories.getText().toString());
                    showCalories.setText(String.valueOf(calories) + " kcal");
                } catch (Exception e) {
                    inputCalories.setError(getResources().getString(R.string.error_empty_input_calories));
                }
//                saveVariables();
            }
        });

        addCarbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try  {
                    carbs += Integer.parseInt(inputCarbs.getText().toString());
                    showCarbs.setText(String.valueOf(carbs) + " g");
                } catch (Exception e) {
                    inputCarbs.setError(getResources().getString(R.string.error_empty_input_carbs));
                }
            }
        });

        clearInputFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputCalories.getText().clear();
                inputCarbs.getText().clear();
            }
        });

        resetDailyValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calories = 0;
                carbs = 0;
                showCalories.setText(getResources().getString(R.string.text_init_showcalories));
                showCarbs.setText(getResources().getString(R.string.text_init_showcarbs));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);

//        loadVariables();
        setupGUI();
    }
}