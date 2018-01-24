package com.example.krokogator.wordmasterandroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.method.Touch;
import android.util.Log;

import com.example.krokogator.wordmasterandroid.GridSolver.Solver;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.TouchEmulation.TouchEmulator;
import com.example.krokogator.wordmasterandroid.Utility.CommandExecutor;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by micha on 10.01.2018.
 */

public class TestThread implements Runnable {
    public boolean isRunning = true;
    private Context context;
    private ImageLoader img;
    private ImageAnalyzer analyzer;
    private Solver solver;
    private TouchEmulator touchEmulator;
    public boolean isRound;
    public int imageBuffer;
    public int temp;
    private CommandExecutor cmd;
    private MainActivity main;
    public String status;

    public TestThread(Context context, MainActivity main){
        this.context = context;
        isRound=false;
        imageBuffer = 5;
        temp=0;
        cmd = new CommandExecutor();
        this.main = main;
    }


    @Override
    public void run() {
        updateStatus("Initializing");
        img = new ImageLoader();
        analyzer = new ImageAnalyzer(img.getSampleLetters(context));
        solver = new Solver(context);
        touchEmulator = new TouchEmulator();


        Thread thread = new Thread() {
            public void run() {
                while (isRunning) {
                    if (isRoundPlaying2()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isRound = true;
                    } else{
                        isRound = false;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();



        updateStatus("Running");
        while(isRunning){

            if(isRound){
                round();
            }

        }

        updateStatus("Offline");
        thread.interrupt();





    }

    private void round(){
        Log.i("WordMasterThread","Round Start");
        String input = analyzer.analyzeLetters(img.getUnverifiedLetters("letterScreenshot"));
        List<List<String>> paths = solver.findPaths(input.toCharArray());
        for (List<String> path: paths
             ) {
            if(!isRound){
                Log.i("WordMasterThread","Round Stop");
                break;
            }
            touchEmulator.emulate(path);
        }
        while (isRound){
            try {
                Log.i("WordMasterThread","Round Waiting");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isRoundPlaying(){
        ImageLoader img2 = new ImageLoader();
        String name = "isRoundScreenshot";
        if(temp>=imageBuffer){
            temp=0;
        } else{
            temp++;
        }
        name+=temp;
        if(analyzer.isInRound(img2.getScreenshot(name))){
            return true;
        }

        return false;
    }

    private boolean isRoundPlaying2(){
        String output = cmd.sudoForResult("dumpsys activity activities | grep mFocusedActivity");
        if(output.contains("slowotok") && output.contains("PlayActivity")){
            return true;
        }
        return false;
    }

    private void updateStatus(String status){
        Handler handler = main.handler;
        Message msg = handler.obtainMessage();
        msg.obj = status;
        handler.sendMessage(msg);
    }

}
