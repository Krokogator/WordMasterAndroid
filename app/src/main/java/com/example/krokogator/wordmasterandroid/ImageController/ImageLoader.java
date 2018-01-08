package com.example.krokogator.wordmasterandroid.ImageController;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.krokogator.wordmasterandroid.Other.CommandExecutor;

import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Created by micha on 06.01.2018.
 */

public class ImageLoader {
    CommandExecutor cmd = new CommandExecutor();

    private void screenshot(){
        cmd.sudo("/system/bin/screencap -p /sdcard/WordMaster/img.png");
    }

    public Bitmap getScreenshot(){
        Thread t1 = new Thread(){
            public void run(){
                screenshot();
                Log.i("IMAGE","t start");
                try {
                    Thread.currentThread();
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.run();
        while(t1.isAlive()){}
        return getImage("/WordMaster/img.png");
    }

    public Bitmap[] getSampleLetters(Context context){
        Bitmap[] letters = new Bitmap[32];
        try {
            for(int i=0;i<=31;i++){
                letters[i] = BitmapFactory.decodeStream(context.getAssets().open("sampleLetters/"+ (i+1)+".png"));
                letters[i] = Bitmap.createBitmap(letters[i], 80, 80,166,224);
                Log.i("IMAGE", "Loaded sample letter" + (i+1) + ",size: "+letters[i].getWidth()+"x"+letters[i].getHeight());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return letters;
    }

    public Bitmap[] getUnverifiedLetters(Context context){
        return sliceIntoLetters(getImage("/WordMaster/Temp/screencap.png"));
    }

    private Bitmap[] sliceIntoLetters(Bitmap bitmap){
        Bitmap[] letters = new Bitmap[16];

        int x=115;
        int y=936;
        int counter=0;
        for(int i=1;i<5;i++) {
            for(int j=1;j<5;j++){
                letters[counter]=Bitmap.createBitmap(bitmap, x, y,166,224);
                Log.i("IMAGE", "Loaded unrecognised letter" + (counter) + ",size: "+letters[counter].getWidth()+"x"+letters[counter].getHeight());
                x=x+348;
                counter++;
            }
            x=115;
            y=y+423;
        }

        return letters;
    }

    private Bitmap getImage(String directory){
        //BufferedImage img = null;
        Bitmap img = null;

        String absolute = Environment.getExternalStorageDirectory().getAbsolutePath();
        String path = absolute.concat(directory);
        //tries to load an image from given path
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            img = BitmapFactory.decodeFile(path, options);
        }
        catch (Exception e) { }
        return img;
    }


}
