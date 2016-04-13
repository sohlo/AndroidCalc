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
    private static final String CURRENT_CALCULATION = "currentCalculation";
    private static TextView textView;
    private static String savedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate called");
        }
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textViewCalc);

        if (savedInstanceState != null) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Restoring state");
            }
            savedCalculation = savedInstanceState.getString(CURRENT_CALCULATION);
            restoreIntent();
        }
        textView.setText(savedCalculation);

    }

    private void restoreIntent() {
        Intent intent = new Intent();
        intent.setAction("com.ee.calculatorRequest");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("savedCalculation", savedCalculation);
        intent.putExtra("restoreState", true);

        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String calcResult = getResultData();
                textView.setText(calcResult);
                savedCalculation = calcResult;

            }
        }, null, Activity.RESULT_OK, null, null);

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onSaveInstanceState called");
        }
        savedInstanceState.putString(CURRENT_CALCULATION, savedCalculation);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void buttonClicked(View view) {
        broadcastIntent(view);
    }

    public void broadcastIntent(View view) {
        Button btn = (Button) view;
        String btnIDstring = btn.getResources().getResourceName(btn.getId());
        Intent intent = new Intent();
        intent.setAction("com.ee.calculatorRequest");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("insertedButton", btnIDstring);
        intent.putExtra("restoreState", false);

        sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String calcResult = getResultData();
                textView.setText(calcResult);
                savedCalculation = calcResult;
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
