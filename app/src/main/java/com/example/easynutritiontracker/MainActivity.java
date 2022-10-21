// Simple utility to track calorie and carbohydrate intake
//
// Version history:
// 2022-10-07 v0.1   Initial test release
// 2022-10-09 v0.2   ADD Variables are saved to disk
//                   ADD RESET button has to be held to function
// 2022-10-10 v0.21  FIX Screen orientation locked to portrait (code & manifest)
//
// TODO: Auto-clear input fields after entering value & remove "CLEAR INPUT" button
// TODO: Add ADD_ALL button to add both calories & carbs

package com.example.easynutritiontracker;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    private int calories;
    private int carbs;
    private TextView showCalories;
    private TextView showCarbs;

    private EditText inputCalories;
    private EditText inputCarbs;

    private Button addCalories;
    private Button addCarbs;
    private Button clearInputFields;
    private Button resetDailyValues;
    private Button addAll;

    public void loadVariables() {
        Log.d("myDebug", "loadVariables");
        int defaultCalories = getResources().getInteger(R.integer.default_calories);
        int defaultCarbs = getResources().getInteger(R.integer.default_carbs);
        calories = preferences.getInt(getString(R.string.saved_calories), defaultCalories);
        carbs = preferences.getInt(getString(R.string.saved_carbs), defaultCarbs);
        Log.d("myDebug", String.valueOf(calories) + "/" + String.valueOf(carbs));
    }

    public void saveVariables() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(getString(R.string.saved_calories), calories);
        editor.putInt(getString(R.string.saved_carbs), carbs);
        editor.apply();
    }

    public void runGUI() {

        showCalories = findViewById(R.id.text_showCalories);
        showCarbs = findViewById(R.id.text_showCarbs);

        inputCalories = findViewById(R.id.text_inputCalories);
        inputCarbs = findViewById(R.id.text_inputCarbs);

        addCalories = findViewById(R.id.button_addCalories);
        addCarbs = findViewById(R.id.button_addCarbs);
        clearInputFields = findViewById(R.id.button_clearInputFields);
        resetDailyValues = findViewById(R.id.button_resetDailyValues);
        addAll = findViewById(R.id.button_addAll);

        showCalories.setText(String.valueOf(calories) + " kcal");
        showCarbs.setText(String.valueOf(carbs) + " g");

        inputCalories.setCursorVisible(false);
        inputCarbs.setCursorVisible(false);

        addCalories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calories += Integer.parseInt(inputCalories.getText().toString());
                    showCalories.setText(String.valueOf(calories) + " kcal");
                    saveVariables();
                } catch (Exception e) {
                    inputCalories.setError(getResources().getString(R.string.error_empty_input_calories));
                }
            }
        });

        addCarbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    carbs += Integer.parseInt(inputCarbs.getText().toString());
                    showCarbs.setText(String.valueOf(carbs) + " g");
                    saveVariables();
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

        resetDailyValues.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                calories = 0;
                carbs = 0;
                showCalories.setText(getResources().getString(R.string.text_init_showcalories));
                showCarbs.setText(getResources().getString(R.string.text_init_showcarbs));
                saveVariables();
                return true;
            }
        });

        addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calories += Integer.parseInt(inputCalories.getText().toString());
                    showCalories.setText(String.valueOf(calories) + " kcal");
                    inputCalories.getText().clear();
                    saveVariables();
                } catch (Exception e) {
                    // inputCalories.setError(getResources().getString(R.string.error_empty_input_calories));
                }
                try {
                    carbs += Integer.parseInt(inputCarbs.getText().toString());
                    showCarbs.setText(String.valueOf(carbs) + " g");
                    inputCarbs.getText().clear();
                    saveVariables();
                } catch (Exception e) {
                    // inputCarbs.setError(getResources().getString(R.string.error_empty_input_carbs));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        preferences = getPreferences(MODE_PRIVATE);

        loadVariables();
        runGUI();
    }
}