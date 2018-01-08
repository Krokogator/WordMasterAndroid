package com.example.krokogator.wordmasterandroid.Other;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

/**
 * Created by micha on 08.01.2018.
 */

public class PermissionVerifier {
    public static boolean isStoragePermissionGranted(Activity activity) {

        if (Build.VERSION.SDK_INT >= 23) {

            if (activity.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                return true;

            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                return false;

            }
        } else {

            return true;

        }
    }
}
