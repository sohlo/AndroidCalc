package com.ee.calculator;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Silver on 7.03.2016.
 */
public class CalculatorEngine {

    private static final String TAG = "CalculatorEngine";
    private double calcResult = -1;


    private String currentNumber = "";
    private boolean lastInserted;
    private boolean showingResult = false;
    private String displayText = "";
    private ArrayList<String> inputArray = new ArrayList<>();

    public void setCalcResult(double calcResult) {
        this.calcResult = calcResult;
    }

    public void setInputArray(ArrayList<String> inputArray) {
        this.inputArray = inputArray;
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
        //TODO ÜMBER SWITCH CASEKS?
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
                    Log.d(TAG, btnID + "pressed but no new point was added");
                }
            } else {
                addPoint(".");
            }

        } else if (btnID.contains("id/ans")) {
            if (!Objects.equals(currentNumber, ""))
                this.inputArray.add(currentNumber);
            currentNumber = "";
            if (BuildConfig.DEBUG) {
                Log.d(TAG, inputArray.toString() + " -- array and current is :" + currentNumber);
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
        double result = 0;
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

/*        double result = 0;
        double midResult = 0;
        double x = 0;
        double y = 0;
        boolean xHasValue = false;
        boolean yHasValue = false;
        boolean midHasValue = false;
        for (int i = 0; i < this.inputArray.size(); i++) {

            if (i % 2 == 0) {

                if (xHasValue) {
                    y = Double.parseDouble(inputArray.get(i));
                    xHasValue = false;
                    yHasValue = true;
                } else {
                    x = Double.parseDouble(inputArray.get(i));
                    xHasValue = true;
                }
                if (midHasValue) {
                    if (this.inputArray.get(i - 1).equals("-")) {
                        midResult = midResult - x;
                    } else if (this.inputArray.get(i - 1).equals("+")) {
                        midResult = midResult + x;
                    } else if (this.inputArray.get(i - 1).equals("/")) {
                        midResult = midResult / x;
                    } else if (this.inputArray.get(i - 1).equals("*")) {
                        midResult = midResult * x;
                    }
                    result = midResult;
                }
            }
            if (i > 0) {
                if (this.inputArray.get(i - 1).equals("-") && yHasValue) {
                    midResult = x - y;
                    yHasValue = false;
                    midHasValue = true;
                } else if (this.inputArray.get(i - 1).equals("+") && yHasValue) {
                    midResult = x + y;
                    yHasValue = false;
                    midHasValue = true;
                } else if (this.inputArray.get(i - 1).equals("/") && yHasValue) {
                    midResult = x / y;
                    yHasValue = false;
                    midHasValue = true;
                } else if (this.inputArray.get(i - 1).equals("*") && yHasValue) {
                    midResult = x * y;
                    midHasValue = true;
                    yHasValue = false;

                } else if (i + 1 >= this.inputArray.size()) {

                    if (this.inputArray.get(i).equals("-")) {
                        result = midResult - Double.parseDouble(inputArray.get(i));
                    } else if (this.inputArray.get(i).equals("+")) {
                        result = midResult + Double.parseDouble(inputArray.get(i));
                    } else if (this.inputArray.get(i).equals("/")) {
                        result = midResult / Double.parseDouble(inputArray.get(i));
                    } else if (this.inputArray.get(i).equals("*")) {
                        result = midResult * Double.parseDouble(inputArray.get(i));
                    }

                }
                if (this.inputArray.size() == 3)
                    result = midResult;
            }

        }
        calcResult = result;*/
    }

    public void clear() {
        inputArray.clear();
        currentNumber = "";
        calcResult = -1;
        lastInserted = false;
    }
}