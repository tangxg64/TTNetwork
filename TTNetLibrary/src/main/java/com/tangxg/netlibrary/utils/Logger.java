package com.tangxg.netlibrary.utils;

import android.util.Log;

import java.util.Locale;

/**
 * Created by tangxg  on 2018/12/19 0019.
 * 邮箱  369516895@QQ.com
 */

public class Logger {
    public static final boolean DEBUG = true;
    public static final String TAG = "ttnet";

    public static void debug(String message) {
        if (DEBUG) {
            Log.d(TAG, message);
        }
    }

    public static void debug(String message, Object... args) {
        if (DEBUG) {
            Log.d(TAG, String.format(Locale.getDefault(), message, args));
        }
    }


    public static void error(String message) {
        if (DEBUG) {
            Log.e(TAG, message);
        }
    }


    public static void info(String message) {
        if (DEBUG) {
            Log.i(TAG, message);
        }
    }

    public static void warn(String message) {
        if (DEBUG) {
            Log.w(TAG, message);
        }
    }
}
