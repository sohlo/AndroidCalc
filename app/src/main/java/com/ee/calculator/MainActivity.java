package com.ee.calculator;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    /*
        private static final String STATE_ARRAY = "insertedArray";
        private static final String STATE_DISPLAY = "insertedArray";
        private static final String STATE_RESULT = "calulatedResult";
        private static final String STATE_CURRENT = "currentInsert";
        private static final String STATE_LAST_INSERTS = "lastInserted";
        private static final String STATE_SHOWING = "showingResult";
        private CalculatorEngine calc = new CalculatorEngine();
    */
    private static TextView calcText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate called");
        }
        setContentView(R.layout.activity_main);
        calcText = (TextView) findViewById(R.id.textViewCalc);
/*
        if (savedInstanceState != null) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Restoring state");
            }
            calc.setStringToArray(savedInstanceState.getString(STATE_ARRAY));
            calc.setCalcResult(savedInstanceState.getDouble(STATE_RESULT));
            calc.setCurrentNumber(savedInstanceState.getString(STATE_CURRENT));
            calc.setDisplayText(savedInstanceState.getString(STATE_DISPLAY));
            calc.setLastInserted(savedInstanceState.getBoolean(STATE_LAST_INSERTS));
            calc.setShowingResult(savedInstanceState.getBoolean(STATE_SHOWING));
            calc.setDisplayT();
        }

        currentInput();*/
    }

/*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState called");
        }
        calc.arrayToString(calc.getInputArray());
        savedInstanceState.putString(STATE_ARRAY, calc.getStateDisplayTextSave());
        savedInstanceState.putString(STATE_DISPLAY, calc.getDisplayText());
        savedInstanceState.putString(STATE_CURRENT, calc.getCurrentNumber());
        savedInstanceState.putDouble(STATE_RESULT, calc.getCalcResult());
        savedInstanceState.putBoolean(STATE_LAST_INSERTS, calc.isLastInserted());
        savedInstanceState.putBoolean(STATE_SHOWING, calc.isShowingResult());

        super.onSaveInstanceState(savedInstanceState);
    }
*/


/*
    public void currentInput() {
        calcText.setText(String.format("%s%s", calc.getDisplayText(), calc.getCurrentNumber()));

    }
*/

    public void buttonClicked(View view) {
        broadcastIntent(view);

/*
        Button btn = (Button) view;
        String btnIDstring = btn.getResources().getResourceName(btn.getId());
        calc.checkBtn(btnIDstring);
        currentInput();
*/
    }

    public void broadcastIntent(View view) {
        Button btn = (Button) view;
        String btnIDstring = btn.getResources().getResourceName(btn.getId());
        Intent intent = new Intent();
        intent.setAction("com.ee.calculatorRequest");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("insertedButton", btnIDstring);

        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String calcResult = getResultData();
                calcText.setText(calcResult);
            }
        }, null, Activity.RESULT_OK, null, null);
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
