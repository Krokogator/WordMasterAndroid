package com.example.krokogator.wordmasterandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.krokogator.wordmasterandroid.Dictionary.Dictionary;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.Other.PermissionVerifier;

public class MainActivity extends AppCompatActivity {

    Intent backgroundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backgroundService = new Intent(this, BackgroundServiceTEST.class);

        PermissionVerifier.isStoragePermissionGranted(this);


        /**
         * IMAGELOADER

        ImageLoader img = new ImageLoader();
        //img.getScreenshot();
         */

        ImageLoader img = new ImageLoader();



        ImageAnalyzer analyzer = new ImageAnalyzer(img.getSampleLetters(getApplicationContext()));
        Log.i("IMAGE", "Analiza w toku...");
        Log.i("IMAGE",analyzer.analyzeLetters(img.getUnverifiedLetters(getApplicationContext())));

        /**
         * DICTIONARY

        Dictionary dict = new Dictionary(getApplicationContext());
        if(dict.checkWord("kupa")){
            Log.i("IMAGE", "kupa found");
        }
        if(!dict.checkWord("mytcohłodadm")){
            Log.i("IMAGE", "mytcohłodadm not found");
        }
         */

    }

    @Override
    protected void onPause() {
        startService(backgroundService);
        super.onPause();
    }

    @Override
    protected void onResume() {
        stopService(backgroundService);
        super.onResume();
    }




}
