package com.example.krokogator.wordmasterandroid;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.TouchEmulation.Point;
import com.example.krokogator.wordmasterandroid.TouchEmulation.TouchEmulator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 20.10.2017.
 */

public class BackgroundServiceTEST extends Service {

    TouchEmulator touch = new TouchEmulator();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Point> points = new ArrayList<>();
        points.add(new Point(100,400));
        points.add(new Point(300, 400));
        points.add(new Point(500, 400));
        points.add(new Point(1000, 400));
        touch.emulateTouch(points);

        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        points.add(new Point(100,400));
        points.add(new Point(300, 400));
        points.add(new Point(500, 400));
        points.add(new Point(1000, 400));
        touch.emulateTouch(points);

        ImageLoader img = new ImageLoader();
        //img.screenshot();

        notifySomethingInToast("Notifikejszyn");
    }

    private void notifySomethingInToast(String text){
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        toast.show();
    }
}
