//
// Simple utility to track nutrition intake
//
// Version history:
// 2022-10-07 v0.1   Initial test release
// 2022-10-09 v0.2   ADD Variables are saved to disk
//                   ADD RESET button has to be held to function
// 2022-10-10 v0.21  FIX Screen orientation locked to portrait (code & manifest)
// 2022-10-22 v0.22  ADD GUI improvements
// 2023-02-09 v0.3   ADD Daily fluid intake
//

package com.example.easynutritiontracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Integer calories;
    private Integer carbs;
    private Integer fluids;

    private TextView showCalories;
    private TextView showCarbs;
    private TextView showFluids;

    private EditText inputCalories;
    private EditText inputCarbs;
    private EditText inputFluids;

    private Button addDailyValues;
    private Button resetDailyValues;

    public void loadVariables() {
        calories = sharedPreferences.getInt(getResources().getString(R.string.saved_calories), getResources().getInteger(R.integer.default_calories));
        carbs = sharedPreferences.getInt(getResources().getString(R.string.saved_carbs), getResources().getInteger(R.integer.default_carbs));
        fluids = sharedPreferences.getInt(getResources().getString(R.string.saved_fluids), getResources().getInteger(R.integer.default_fluids));
    }

    public void saveVariables() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.saved_calories), calories);
        editor.putInt(getString(R.string.saved_carbs), carbs);
        editor.putInt(getString(R.string.saved_fluids), fluids);
        editor.apply();
    }

    public void runGUI() {

        showCalories = findViewById(R.id.textView_showCalories);
        showCarbs = findViewById(R.id.textView_showCarbs);
        showFluids = findViewById(R.id.textView_showFluids);

        inputCalories = findViewById(R.id.editText_enterCalories);
        inputCarbs = findViewById(R.id.editText_enterCarbs);
        inputFluids = findViewById(R.id.editText_enterFluids);

        addDailyValues = findViewById(R.id.button_addDailyValues);
        resetDailyValues = findViewById(R.id.button_resetDailyValues);

        showCalories.setText(String.valueOf(calories));
        showCarbs.setText(String.valueOf(carbs));
        showFluids.setText(String.valueOf(fluids));

        inputCalories.setCursorVisible(false);
        inputCarbs.setCursorVisible(false);
        inputFluids.setCursorVisible(false);

        addDailyValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calories += Integer.parseInt(inputCalories.getText().toString());
                } catch (Exception e) {
                    calories += 0;
                }
                showCalories.setText(String.valueOf(calories));
                inputCalories.getText().clear();

                try {
                    carbs += Integer.parseInt(inputCarbs.getText().toString());
                } catch (Exception e) {
                    carbs += 0;
                }
                showCarbs.setText(String.valueOf(carbs));
                inputCarbs.getText().clear();

                try {
                    fluids += Integer.parseInt(inputFluids.getText().toString());
                } catch (Exception e) {
                    fluids += 0;
                }
                showFluids.setText(String.valueOf(fluids));
                inputFluids.getText().clear();

                saveVariables();
            }
        });

        resetDailyValues.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                calories = 0;
                carbs = 0;
                fluids = 0;
                showCalories.setText(getResources().getString(R.string.text_init_showcalories));
                showCarbs.setText(getResources().getString(R.string.text_init_showcarbs));
                showFluids.setText(getResources().getString(R.string.text_init_showfluids));
                saveVariables();
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPreferences = getPreferences(MODE_PRIVATE);

        loadVariables();
        runGUI();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}