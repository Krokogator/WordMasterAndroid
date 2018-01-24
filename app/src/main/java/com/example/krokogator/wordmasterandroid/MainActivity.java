package com.example.krokogator.wordmasterandroid;

import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.krokogator.wordmasterandroid.GridSolver.Solver;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.Utility.PermissionVerifier;

public class MainActivity extends AppCompatActivity {
    private Thread thread;
    private TestThread testT;
    private Button startButton;
    private Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionVerifier.isStoragePermissionGranted(this);
        startButton = findViewById(R.id.button);
        stopButton = findViewById(R.id.button2);
        stopButton.setEnabled(false);
    }

    public void onClickStartThread(View view){
        if(testT == null){
            startButton.setEnabled(false);
            testT = new TestThread(getApplicationContext());
            thread = new Thread(testT);
            thread.start();
            stopButton.setEnabled(true);
        }
    }

    public void onClickStopThread(View view){
        if(testT!=null){
            testT.isRunning=false;
            testT.isRound = false;
            testT = null;
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
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
