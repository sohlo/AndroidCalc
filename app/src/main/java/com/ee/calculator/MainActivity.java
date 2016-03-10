package com.ee.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String STATE_ARRAY = "insertedArray";
    private static final String STATE_DISPLAY = "insertedArray";
    private static final String STATE_RESULT = "calulatedResult";
    private static final String STATE_CURRENT = "currentInsert";
    private static final String STATE_LAST_INSERTS = "lastInserted";
    private static final String STATE_SHOWING = "showingResult";
    private TextView calcText;
    private CalculatorEngine calc = new CalculatorEngine();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate called");
        }
        setContentView(R.layout.activity_main);
        calcText = (TextView) findViewById(R.id.textViewCalc);
        if (savedInstanceState != null){
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Restoring state");
            }
            calc.setInputArray(savedInstanceState.getStringArrayList(STATE_ARRAY));
            calc.setCalcResult(savedInstanceState.getDouble(STATE_RESULT));
            calc.setCurrentNumber(savedInstanceState.getString(STATE_CURRENT));
            calc.setDisplayText(savedInstanceState.getString(STATE_DISPLAY));
            calc.setLastInserted(savedInstanceState.getBoolean(STATE_LAST_INSERTS));
            calc.setShowingResult(savedInstanceState.getBoolean(STATE_SHOWING));
            calc.setDisplayT();
        }

        currentInput();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState called");
        }
        savedInstanceState.putStringArrayList(STATE_ARRAY,calc.getInputArray());
        //TODO array sisu
        savedInstanceState.putString(STATE_DISPLAY, calc.getDisplayText());
        savedInstanceState.putDouble(STATE_RESULT, calc.getCalcResult());
        savedInstanceState.putString(STATE_CURRENT, calc.getCurrentNumber());
        savedInstanceState.putBoolean(STATE_LAST_INSERTS, calc.isLastInserted());
        savedInstanceState.putBoolean(STATE_SHOWING, calc.isShowingResult());

        super.onSaveInstanceState(savedInstanceState);
    }


    public void currentInput() {
        //float sp= 0F;
        int len = calc.getDisplayText().length() + calc.getCurrentNumber().length();
        if (len >= 22 && len < 27)
            calcText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        if (len >= 27)
            calcText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        calcText.setText(String.format("%s%s", calc.getDisplayText(), calc.getCurrentNumber()));

    }

    public void buttonClicked(View view) {
        Button btn = (Button) view;
        String btnIDstring = btn.getResources().getResourceName(btn.getId());
        calc.checkBtn(btnIDstring);
        currentInput();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStart called");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onResume called");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onPause called");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStop called");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onDestroy called");
        }

    }
}
