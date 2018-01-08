package com.example.krokogator.wordmasterandroid.ImageController;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by micha on 08.01.2018.
 */

public class ImageAnalyzer {
    private Bitmap[] samples = new Bitmap[32];

    public ImageAnalyzer(Bitmap[] samples){
        this.samples = samples;
    }

    public String analyzeLetters(Bitmap[] letters) {
        String output = "";

        for(Bitmap image : letters){
            output=output+getChar(image);
        }
        return output;
    }

    private float compare(Bitmap img1, Bitmap img2){
        int count = 0;
        for (int x = 0; x < img1.getWidth(); x++) {
            for(int y = 0; y < img1.getHeight(); y++){
                int blue1 = Color.red(img1.getPixel(x,y));
                int blue2 = Color.red(img2.getPixel(x,y));
                if (blue1<blue2||blue1>blue2) {
                    count = count + 1;
                }
            }
        }
        return count;
    }

    private Character getChar(Bitmap image){
        float lowest=compare(image,samples[0]);
        Integer position=0;
        float percentage;
        for(int i=0;i<32;i++){
            percentage=compare(image,samples[i]);
            if(percentage<lowest){
                lowest=percentage;
                position=i;
            }
        }

        Character letter;

        Map<Integer,Character> map = new HashMap<>();
        map.put(0,'a');
        map.put(1,'b');
        map.put(2,'c');
        map.put(3,'d');
        map.put(4,'e');
        map.put(5,'f');
        map.put(6,'g');
        map.put(7,'h');
        map.put(8,'i');
        map.put(9,'j');
        map.put(10,'k');
        map.put(11,'l');
        map.put(12,'m');
        map.put(13,'n');
        map.put(14,'o');
        map.put(15,'p');
        map.put(16,'r');
        map.put(17,'s');
        map.put(18,'t');
        map.put(19,'u');
        map.put(20,'w');
        map.put(21,'y');
        map.put(22,'z');
        map.put(23,'ź');
        map.put(24,'ś');
        map.put(25,'ó');
        map.put(26,'ń');
        map.put(27,'ż');
        map.put(28,'ć');
        map.put(29,'ą');
        map.put(30,'ę');
        map.put(31,'ł');

        letter=map.get(position);
        return letter;
    }



}
