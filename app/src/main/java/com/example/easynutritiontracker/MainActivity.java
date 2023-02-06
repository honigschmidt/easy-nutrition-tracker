// Simple utility to track calorie and carbohydrate intake
//
// Version history:
// 2022-10-07 v0.1   Initial test release
// 2022-10-09 v0.2   ADD Variables are saved to disk
//                   ADD RESET button has to be held to function
// 2022-10-10 v0.21  FIX Screen orientation locked to portrait (code & manifest)
// 2022-10-22 v0.22  ADD GUI improvements

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

        showCalories = findViewById(R.id.textView_showCalories);
        showCarbs = findViewById(R.id.textView_showCarbs);

        inputCalories = findViewById(R.id.editText_enterCalories);
        inputCarbs = findViewById(R.id.editText_enterCarbs);

        addAll = findViewById(R.id.button_addAll);
        resetDailyValues = findViewById(R.id.button_resetDailyValues);

        showCalories.setText(String.valueOf(calories) + " kcal");
        showCarbs.setText(String.valueOf(carbs) + " g");

        inputCalories.setCursorVisible(false);
        inputCarbs.setCursorVisible(false);

        addAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    calories += Integer.parseInt(inputCalories.getText().toString());
                } catch (Exception e) {
                    calories += 0;
                }
                showCalories.setText(String.valueOf(calories) + " kcal");
                inputCalories.getText().clear();

                try {
                    carbs += Integer.parseInt(inputCarbs.getText().toString());
                } catch (Exception e) {
                    carbs += 0;
                }
                showCarbs.setText(String.valueOf(carbs) + " g");
                inputCarbs.getText().clear();

                saveVariables();
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