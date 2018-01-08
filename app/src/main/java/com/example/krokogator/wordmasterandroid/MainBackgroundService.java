package com.example.krokogator.wordmasterandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by micha on 08.01.2018.
 */

public class MainBackgroundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Stuff to do
    }
}
