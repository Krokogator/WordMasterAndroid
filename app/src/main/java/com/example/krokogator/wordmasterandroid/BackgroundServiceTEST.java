package com.example.krokogator.wordmasterandroid;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.FileObserver;
import android.util.Log;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static android.content.ContentValues.TAG;

/**
 * Created by micha on 20.10.2017.
 */

public class BackgroundServiceTEST extends IntentService {
    public BackgroundServiceTEST(){
        super("BackgroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Test tost :D", Toast.LENGTH_LONG);
        toast.show();
    }
}
