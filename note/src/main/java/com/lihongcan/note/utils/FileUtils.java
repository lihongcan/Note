package com.lihongcan.note.utils;

import android.content.Context;
import android.os.Environment;

/**
 * Created by lihongcan on 2016/7/7.
 */
public class FileUtils {
    public  static String getDiskCachePath(Context context){
        String path=null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            path=context.getExternalCacheDir().getPath();
        }

        return path;
    }
}
