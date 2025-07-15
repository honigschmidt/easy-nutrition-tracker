package com.example.easynutritiontracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Integer calories;
    private Integer carbs;
    private Integer fluids;
    private Integer proteins;

    private TextView displayCalories;
    private TextView displayCarbs;
    private TextView displayFluids;
    private TextView displayProteins;

    private EditText inputCalories;
    private EditText inputCarbs;
    private EditText inputFluids;
    private EditText inputProteins;

    private Button addDailyValues;
    private Button resetDailyValues;

    private SharedPreferences sharedPreferences;
    private final Integer MAX_DISPLAYABLE_VALUE = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getPreferences(MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ViewCompat.setOnApplyWindowInsetsListener(toolbar, (v, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(insets.left, insets.top, insets.right, insets.bottom);
            return windowInsets;
        });
        setSupportActionBar(toolbar);
        loadVariables();
        runGUI();
    }

    public void loadVariables() {
        calories = sharedPreferences.getInt(getResources().getString(R.string.saved_calories), getResources().getInteger(R.integer.default_calories));
        carbs = sharedPreferences.getInt(getResources().getString(R.string.saved_carbs), getResources().getInteger(R.integer.default_carbs));
        fluids = sharedPreferences.getInt(getResources().getString(R.string.saved_fluids), getResources().getInteger(R.integer.default_fluids));
        proteins = sharedPreferences.getInt(getResources().getString(R.string.saved_proteins), getResources().getInteger(R.integer.default_proteins));
    }

    public void saveVariables() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.saved_calories), calories);
        editor.putInt(getString(R.string.saved_carbs), carbs);
        editor.putInt(getString(R.string.saved_fluids), fluids);
        editor.putInt(getString(R.string.saved_proteins), proteins);
        editor.apply();
    }

    public boolean isMaxReached(Integer currentValue, Integer newValue) {
        return currentValue + newValue > 9999;
    }

    public void runGUI() {

        displayCalories = findViewById(R.id.textView_displayCalories);
        displayCarbs = findViewById(R.id.textView_displayCarbs);
        displayFluids = findViewById(R.id.textView_displayFluids);
        displayProteins = findViewById(R.id.textView_displayProteins);

        inputCalories = findViewById(R.id.editText_enterCalories);
        inputCarbs = findViewById(R.id.editText_enterCarbs);
        inputFluids = findViewById(R.id.editText_enterFluids);
        inputProteins = findViewById(R.id.editText_enterProteins);

        addDailyValues = findViewById(R.id.button_addDailyValues);
        resetDailyValues = findViewById(R.id.button_resetDailyValues);

        displayCalories.setText(String.valueOf(calories));
        displayCarbs.setText(String.valueOf(carbs));
        displayFluids.setText(String.valueOf(fluids));
        displayProteins.setText(String.valueOf(proteins));

        inputCalories.setCursorVisible(false);
        inputCarbs.setCursorVisible(false);
        inputFluids.setCursorVisible(false);
        inputProteins.setCursorVisible(false);

        addDailyValues.setOnClickListener(view -> {

            try {
                int newValue = Integer.parseInt(inputCalories.getText().toString());
                if (isMaxReached(calories, newValue)) {
                    calories = MAX_DISPLAYABLE_VALUE;
                } else {
                    calories += newValue;
                }
            } catch (Exception e) {
                calories += 0;
            }
            displayCalories.setText(String.valueOf(calories));
            inputCalories.getText().clear();

            try {
                int newValue = Integer.parseInt(inputCarbs.getText().toString());
                if (isMaxReached(carbs, newValue)) {
                    carbs = MAX_DISPLAYABLE_VALUE;
                } else {
                    carbs += newValue;
                }
            } catch (Exception e) {
                carbs += 0;
            }
            displayCarbs.setText(String.valueOf(carbs));
            inputCarbs.getText().clear();

            try {
                int newValue = Integer.parseInt(inputFluids.getText().toString());
                if (isMaxReached(fluids, newValue)) {
                    fluids = MAX_DISPLAYABLE_VALUE;
                } else {
                    fluids += newValue;
                }
            } catch (Exception e) {
                fluids += 0;
            }
            displayFluids.setText(String.valueOf(fluids));
            inputFluids.getText().clear();

            try {
                int newValue = Integer.parseInt(inputProteins.getText().toString());
                if (isMaxReached(proteins, newValue)) {
                    proteins = MAX_DISPLAYABLE_VALUE;
                } else {
                    proteins += newValue;
                }
            } catch (Exception e) {
                proteins += 0;
            }
            displayProteins.setText(String.valueOf(proteins));
            inputProteins.getText().clear();

            saveVariables();
        });

        resetDailyValues.setOnLongClickListener(view -> {
            calories = 0;
            carbs = 0;
            fluids = 0;
            proteins = 0;
            displayCalories.setText(getResources().getString(R.string.text_init_displaycalories));
            displayCarbs.setText(getResources().getString(R.string.text_init_displaycarbs));
            displayFluids.setText(getResources().getString(R.string.text_init_displayfluids));
            displayProteins.setText(getResources().getString(R.string.text_init_displayproteins));
            inputCalories.getText().clear();
            inputCarbs.getText().clear();
            inputFluids.getText().clear();
            inputProteins.getText().clear();
            saveVariables();
            return true;
        });
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