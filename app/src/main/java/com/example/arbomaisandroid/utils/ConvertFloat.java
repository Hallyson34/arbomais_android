package com.example.arbomaisandroid.utils;

import android.widget.Toast;

public class ConvertFloat {
    public static String floatToString(float number) {
        try {
            return Float.toString(number);
        } catch (NumberFormatException e) {
            return "";
        }
    }

    public static float stringToFloat(String numberStr) {
        try {
            return Float.parseFloat(numberStr);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
