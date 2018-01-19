package com.example.krokogator.wordmasterandroid;

import android.content.ContentValues;
import android.content.Context;
import android.text.method.Touch;
import android.util.Log;

import com.example.krokogator.wordmasterandroid.GridSolver.Solver;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.TouchEmulation.TouchEmulator;

import java.util.List;

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
    private boolean isRound;
    public int imageBuffer;
    public int temp;

    public TestThread(Context context){
        this.context = context;
        isRound=false;
        imageBuffer = 5;
        temp=0;
    }


    @Override
    public void run() {
        img = new ImageLoader();
        analyzer = new ImageAnalyzer(img.getSampleLetters(context));
        solver = new Solver(context);
        touchEmulator = new TouchEmulator();

        Thread thread = new Thread() {
            public void run() {
                while (isRunning) {
                    if (isRoundPlaying()) {
                        isRound = true;
                    } else{
                        isRound = false;
                    }
                }
            }
        };
        thread.start();

        while(isRunning){
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isRound){
                round();
            }

        }

        thread.interrupt();
    }

    private void round(){
        Log.i("WordMasterThread","Runda Start");
        String input = analyzer.analyzeLetters(img.getUnverifiedLetters("letterScreenshot"));
        List<List<String>> paths = solver.findPaths(input.toCharArray());
        for (List<String> path: paths
             ) {
            if(!isRound){
                Log.i("WordMasterThread","Runda ForceStop");
                break;
            }
            touchEmulator.emulate(path);
        }
        while (isRound){
            try {
                Log.i("WordMasterThread","Runda Waiting");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //solver.findPaths("agagagagagagagag".toCharArray());
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


}
