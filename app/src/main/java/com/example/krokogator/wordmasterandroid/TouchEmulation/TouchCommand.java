package com.example.krokogator.wordmasterandroid.TouchEmulation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class TouchCommand {
    private static String sendevent = "sendevent /dev/input/event1";

    public List<String> newTouchCommand(List<Point> points){
        List<String> commands = new ArrayList<>();
        commands.add(touch());
        for(Point point : points){
            commands.add(move(point.getX(), point.getY()));
        }
        commands.add(release());

        return commands;
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
