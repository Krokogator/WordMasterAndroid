package com.example.krokogator.wordmasterandroid.ImageLoader;

import com.example.krokogator.wordmasterandroid.Other.CommandExecutor;

/**
 * Created by micha on 06.01.2018.
 */

public class ImageLoader {

    private void screenshot(){
        CommandExecutor.sudo("/system/bin/screencap -p /sdcard/WordMaster/img.png");
    }
}
