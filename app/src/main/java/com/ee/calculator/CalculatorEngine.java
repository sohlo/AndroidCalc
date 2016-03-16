package com.ee.calculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Silver on 7.03.2016.
 */
public class CalculatorEngine {

    private static final String TAG = "CalculatorEngine";
    private double calcResult;


    private String currentNumber = "";
    private boolean lastInserted;
    private boolean showingResult = false;
    private String displayText = "";
    private String stateDisplayTextSave = "";
    private ArrayList<String> inputArray = new ArrayList<>();

    public String getStateDisplayTextSave() {
        return stateDisplayTextSave;
    }

    public void setStateDisplayTextSave(String stateDisplayTextSave) {
        this.stateDisplayTextSave = stateDisplayTextSave;
    }

    public void setCalcResult(double calcResult) {
        this.calcResult = calcResult;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public ArrayList<String> getInputArray() {
        return inputArray;
    }

    public double getCalcResult() {
        return calcResult;
    }

    public String getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(String currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isLastInserted() {
        return lastInserted;
    }

    public void setLastInserted(boolean lastInserted) {
        this.lastInserted = lastInserted;
    }

    public boolean isShowingResult() {
        return lastInserted;
    }

    public void setShowingResult(boolean lastInserted) {
        this.lastInserted = lastInserted;
    }

    public CalculatorEngine() {
    }

    public void checkBtn(String btnID) {
        if (showingResult) {
            showingResult = false;
            clear();
        }
        if (btnID.contains("nr")) {
            if (btnID.contains("0")) {
                if (!Objects.equals(currentNumber, "")) {
                    addNumber(btnID);
                }
            } else {
                addNumber(btnID);
            }
        } else if (btnID.contains("id/point")) {

            if (Objects.equals(currentNumber, "")) {
                addPoint("0.");
            } else if (currentNumber.contains(".")) {
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, btnID + " pressed but no new point was added");
                }
            } else {
                addPoint(".");
            }

        } else if (btnID.contains("id/ans")) {
            if (!Objects.equals(currentNumber, ""))
                this.inputArray.add(currentNumber);
            currentNumber = "";
            if (BuildConfig.DEBUG) {
                Log.d(TAG, inputArray.toString() + " -- array and current is : " + currentNumber);
            }
            calculateResult();
            showingResult = true;
        } else if (btnID.contains("id/C")) {
            clear();
        } else if (!Objects.equals(currentNumber, "")) {

            int len = currentNumber.length() - 1;
            if (currentNumber.substring(len).equals(".")) {
                currentNumber = currentNumber.replaceAll("[.]", "");
            } // Remove point from number, like 12.

            if (btnID.contains("id/subtract")) {
                this.inputArray.add(currentNumber);
                this.inputArray.add("-");
                currentNumber = "";

            } else if (btnID.contains("id/plus")) {
                this.inputArray.add(currentNumber);
                this.inputArray.add("+");
                currentNumber = "";

            } else if (btnID.contains("id/multiply")) {
                this.inputArray.add(currentNumber);
                this.inputArray.add("*");
                currentNumber = "";

            } else if (btnID.contains("id/divide")) {
                this.inputArray.add(currentNumber);
                inputArray.add("/");
                currentNumber = "";
            }
            lastInserted = true;
        } else {
            if (lastInserted) {
                int len = inputArray.size() - 1;
                if (btnID.contains("id/subtract")) {
                    inputArray.set(len, inputArray.get(len).replaceAll("[-+/*]", "-"));
                } else if (btnID.contains("id/plus")) {
                    inputArray.set(len, inputArray.get(len).replaceAll("[-+/*]", "+"));
                } else if (btnID.contains("id/multiply")) {
                    inputArray.set(len, inputArray.get(len).replaceAll("[-+/*]", "*"));
                } else if (btnID.contains("id/divide")) {
                    inputArray.set(len, inputArray.get(len).replaceAll("[-+/*]", "/"));
                } else if (btnID.contains("id/ans")) {
                    inputArray.remove(len);
                    calculateResult();
                }
            }
        }
        setDisplayT();
    }

    public void setDisplayT() {
        setDisplayText("");
        if (inputArray != null) {
            for (int i = 0; i < inputArray.size(); i++) {
                displayText = displayText.concat(inputArray.get(i));
            }
        }
    }


    private void addPoint(String toAdd) {
        currentNumber = currentNumber.concat(toAdd);
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Point added");
        }
    }

    public void addNumber(String btnID) {
        currentNumber = currentNumber.concat(btnID.substring(btnID.length() - 1));
        if (BuildConfig.DEBUG) {
            Log.d(TAG, btnID + "added");
        }
    }

    public void calculateResult() {
        double midResult = 0;
        double x = 0;
        double y = 0;
        while (this.inputArray.size() > 1) {

            x = Double.parseDouble(inputArray.get(0));
            y = Double.parseDouble(inputArray.get(2));

            if (inputArray.get(1).equals("-")) {
                midResult = x - y;
            } else if (inputArray.get(1).equals("+")) {
                midResult = x + y;
            } else if (inputArray.get(1).equals("/")) {
                midResult = x / y;
            } else if (inputArray.get(1).equals("*")) {
                midResult = x * y;
            }
            int j = 0;
            while (j <= 2) {
                inputArray.remove(0);
                j++;
            }
            inputArray.add(0, Objects.toString(midResult));
        }
        calcResult = midResult;
    }

    public void clear() {
        inputArray.clear();
        currentNumber = "";
        calcResult = -1;
        lastInserted = false;
    }
    public void setStringToArray(String string){
        String temp="";
        for (int i = 0; i < string.length(); i++) {
            if (string.substring(i, i + 1).matches("[\\d.]+")){
                temp = temp.concat(string.substring(i,i+1));
            }
            if (!string.substring(i, i + 1).matches("[\\d.]+")){
                inputArray.add(temp);
                inputArray.add(string.substring(i, i + 1));
                temp = "";
            }
        }
    }
    public void arrayToString(ArrayList<String> array){
        String temp="";
        for (int i = 0; i < array.size(); i++) {
            temp = temp.concat(array.get(i));
        }
        setStateDisplayTextSave(temp);
    }
}