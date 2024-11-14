/*
 * Copyright (c) Honor Device Co., Ltd. 2022-2024. All rights reserved.
 */

package com.honor.schedulingkit.demo.log;
import android.util.Log;

import com.honor.schedulingkit.demo.SchedulingKitDemo;

/**
 * 功能描述
 *
 * @since 2024-07-18
 */
public class LogThread implements Runnable {
    private static final String TAG = "LogThread";
    private final LogThreadCallback callback;
    public LogThread(LogThreadCallback callback) {
        this.callback = callback;
    }
    @Override
    public void run() {
        int tid = android.os.Process.myTid();
        Log.d(TAG, "log start ");
        if (callback != null) {
            callback.onThreadStarted(tid);
        }
        // 记录日志
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, "Logging from thread: " + i);
            try {
                // 模拟一些工作
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Thread interrupted", e);
            }
        }
    }
}
