package com.example.krokogator.wordmasterandroid.TouchEmulation;

import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class TouchEvent {
    private static String sendevent = "sendevent /dev/input/event1";

    public String createTouchString(List<Point> points){
        StringBuilder touchEventString = new StringBuilder();

        touchEventString.append(touch());

        for (Point point : points) {
            move(point.getX(), point.getY());
        }

        touchEventString.append(release());

        return touchEventString.toString();
    }

    private String touch(){
        return  sendevent+" 3 57 14 &&"+
                sendevent+" 1 330 1 &&"+
                sendevent+" 1 325 1";
    }
    private String move(int x, int y){
        return  sendevent+" 3 53 "+x+" &&"+
                sendevent+" 3 54 "+y+" &&"+
                sendevent+" 0 0 0";
    }
    private String release(){
        return  sendevent+" 3 57 4294967295 &&"+
                sendevent+" 1 330 0 &&"+
                sendevent+" 1 325 0 &&"+
                sendevent+" 0 0 0";
    }
}
