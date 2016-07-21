package com.lihongcan.note.utils;

import android.util.Log;

/**
 * Created by lihongcan on 2016/7/7.
 */
public class MLog {
    private static final String MLOG_TAG =MLog.class.getSimpleName();
    private static boolean openLog=true;

    public static void e(String msg){
        if (openLog){
            Log.e(MLOG_TAG,msg);
        }
    }
    public static void e(String TAG,String msg){
        if (openLog){
            Log.e(TAG,msg);
        }
    }
    public static void i(String msg){
        if (openLog){
            Log.e(MLOG_TAG,msg);
        }
    }
    public static void i(String TAG,String msg){
        if (openLog){
            Log.e(TAG,msg);
        }
    }
}
