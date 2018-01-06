package com.example.krokogator.wordmasterandroid.TouchEmulation;

import com.example.krokogator.wordmasterandroid.Other.CommandExecutor;

import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class TouchEmulator {
    public void emulateTouch(List<Point> points){
        TouchEvent touch = new TouchEvent();
        emulate(touch.createTouchString(points));
    }

    private void emulate(String touchEvenet){
        CommandExecutor.sudo(touchEvenet);
    }
}
