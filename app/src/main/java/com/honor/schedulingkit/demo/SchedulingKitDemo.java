/*
 * Copyright (c) Honor Device Co., Ltd. 2022-2024. All rights reserved.
 */

package com.honor.schedulingkit.demo;

import android.util.Log;

import com.hihonor.mcs.system.scheduling.Scheduling;
import com.hihonor.mcs.system.scheduling.Scheduling.SchedulingCallback;

/**
 * 功能描述
 *
 * @since 2024-07-18
 */
public class SchedulingKitDemo {
    public static final String TAG = "SchedulingDemo";
    private int mAuthorizedResult = -1;
    private int mRegisteredResult = -1;
    private Scheduling scheduling;
    private SchedulingCallback schedulingCallback;
    private static volatile SchedulingKitDemo instance;

    private int logThreadTid;
    private SchedulingKitDemo() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static SchedulingKitDemo getInstance() {
        if (instance == null) {
            synchronized (SchedulingKitDemo.class) {
                if (instance == null) {
                    instance = new SchedulingKitDemo();
                }
            }
        }
        return instance;
    }

    public void init() {
        scheduling = new Scheduling();
        schedulingCallback = new SchedulingCallback() {
            @Override
            public void notifyRegisterResult(int res) {
                mAuthorizedResult = res;
                Log.d(TAG, "Resgister res = " + res);
                if (mAuthorizedResult == 0) {
                    setThreadSchedHint(logThreadTid);
                }
            }
            @Override
            public void notifySceneResult(int res) {
                Log.d(TAG, "Scene res = " + res);
            }
            @Override
            public void notifyThreadResult(int res) {
                Log.d(TAG, "Thread res = " + res);
            }
        };
        registerApp();
    }

    private int registerApp() {
        mRegisteredResult = scheduling.registerApp(schedulingCallback);
        if (mRegisteredResult != 0) {
            Log.d(TAG, "register failed");
            return -1;
        }
        return 0;
    }

    public int setSceneHint() {
        if (mRegisteredResult != 0 || mAuthorizedResult != 0) {
            Log.d(TAG, "client not register");
            return -1;
        }
        int scene = 0;
        int status = 1;
        return scheduling.setSceneHint(scene, status);
    }

    public int setThreadSchedHint(int tid) {
        if (mRegisteredResult != 0 || mAuthorizedResult != 0) {
            Log.d(TAG, "client not register");
            return -1;
        }
        int threadType = 12;
        int threadLevel = 1;
        return scheduling.setThreadSchedHint(tid, threadType, threadLevel);
    }

    public int unregisterApp() {
        if (mRegisteredResult != 0 && mAuthorizedResult != 0) {
            Log.d(TAG, "client not register");
            return -1;
        }
        return scheduling.unregisterApp();
    }

    public void setLogThreadTid(int tid) {
        logThreadTid = tid;
    }
}
