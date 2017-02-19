package org.kikermo.thingsaudioreceiver.util;

import org.kikermo.thingsaudioreceiver.BuildConfig;



public class Log {

    public static int v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            return android.util.Log.v(tag, msg);
        return 0;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            return android.util.Log.v(tag, msg, tr);
        return 0;
    }


    public static int d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            return android.util.Log.d(tag, msg);
        return 0;
    }


    public static int d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            return android.util.Log.d(tag, msg, tr);
        return 0;
    }

    public static int i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            return android.util.Log.i(tag, msg);
        return 0;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            return android.util.Log.i(tag, msg, tr);
        return 0;
    }

    public static int w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            return android.util.Log.w(tag, msg);
        return 0;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            return android.util.Log.w(tag, msg, tr);
        return 0;
    }

    public static int w(String tag, Throwable tr) {
        if (BuildConfig.DEBUG)
            return android.util.Log.w(tag, tr);
        return 0;
    }

    public static int e(String tag, String msg) {
        if (BuildConfig.DEBUG)
            return android.util.Log.e(tag, msg);
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG)
            return android.util.Log.e(tag, msg, tr);
        return 0;
    }

    public static void logThrowable(Throwable throwable) {
        if (BuildConfig.DEBUG)
            w("Rx Error", throwable);
    }


    public static void logThrowable(Object o) {
        if (o instanceof Throwable)
            logThrowable((Throwable) o);
    }
}
