package com.example.krokogator.wordmasterandroid;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.krokogator.wordmasterandroid.GridSolver.Solver;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;

/**
 * Created by micha on 10.01.2018.
 */

public class TestThread implements Runnable {
    public boolean isRunning = true;
    private Context context;
    private ImageLoader img;
    private ImageAnalyzer analyzer;
    private Solver solver;

    public TestThread(Context context){
        this.context = context;
        img = new ImageLoader();
        analyzer = new ImageAnalyzer(img.getSampleLetters(context));
        solver = new Solver(context);
    }


    @Override
    public void run() {
        while(isRunning){
            /*Thread t1 = new Thread(){
                public void run(){
                    Log.i("IMAGE","t start");
                    try {
                        Thread.currentThread();
                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t1.run();
            while(t1.isAlive()){}*/

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            round();
        }
    }

    private void round(){
        Log.i("WordMasterThread","Runda Start");

        Log.i("WordMasterThread", "Analiza w toku...");
        String input = analyzer.analyzeLetters(img.getUnverifiedLetters(context));
        solver.findPaths(input.toCharArray());
        //solver.findPaths("agagagagagagagag".toCharArray());
    }


}
