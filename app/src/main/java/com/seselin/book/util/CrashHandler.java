package com.seselin.book.util;

import android.content.Context;
import android.util.Log;

import java.lang.Thread.UncaughtExceptionHandler;


public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler INSTANCE;

    private CrashHandler() {
    }

    //获取CrashHandler实例 ,单例模式
    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("CRASH_TAG", "Error : ", ex);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}