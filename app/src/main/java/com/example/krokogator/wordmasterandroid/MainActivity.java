package com.example.krokogator.wordmasterandroid;

import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.krokogator.wordmasterandroid.GridSolver.Solver;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.Utility.PermissionVerifier;

public class MainActivity extends AppCompatActivity {
    private Thread thread;
    private TestThread testT;
    Intent backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //backgroundService = new Intent(this, BackgroundServiceTEST.class);

        PermissionVerifier.isStoragePermissionGranted(this);
        this.testT = new TestThread(getApplicationContext());
    }

    public void onClickStartThread(View view){
        if(thread == null){
            this.thread = new Thread(testT);
            thread.start();
        } else{
            testT.isRunning = true;
            this.thread = new Thread(testT);
            thread.start();

        }

    }

    public void onClickStopThread(View view){
        if(thread!=null){
            testT.isRunning=false;
        }
    }

    @Override
    protected void onPause() {
        //startService(backgroundService);
        super.onPause();
    }

    @Override
    protected void onResume() {
        //stopService(backgroundService);
        super.onResume();
    }

}
