/*
 * Copyright (c) Honor Device Co., Ltd. 2022-2024. All rights reserved.
 */

package com.honor.schedulingkit.demo;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.honor.schedulingkit.demo.databinding.ActivityMainBinding;
import com.honor.schedulingkit.demo.log.LogThread;
import com.honor.schedulingkit.demo.log.LogThreadCallback;

public class MainActivity extends AppCompatActivity implements LogThreadCallback {

    private ActivityMainBinding binding;
    private LogThread logThreadRunnable;
    private Thread logThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        SchedulingKitDemo.getInstance().init();

        logThreadRunnable = new LogThread(this);
        logThread = new Thread(logThreadRunnable);
        logThread.start();

    }

    @Override
    protected void onDestroy() {
        SchedulingKitDemo.getInstance().unregisterApp();
        super.onDestroy();
    }
    @Override
    public void onThreadStarted(int tid) {
        SchedulingKitDemo.getInstance().setLogThreadTid(tid);
    }

}