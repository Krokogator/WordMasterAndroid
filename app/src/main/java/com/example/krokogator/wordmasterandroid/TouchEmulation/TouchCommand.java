package com.example.krokogator.wordmasterandroid.TouchEmulation;

import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class TouchCommand {
    private static String sendevent = "sendevent /dev/input/event1";

    public List<String> newTouchCommand(List<Point> points){
        List<String> commands = new ArrayList<>();
        commands.addAll(touch());
        for(Point point : points){
                commands.addAll(move(point.getX(), point.getY()));
        }
        commands.addAll(release());

        return commands;
    }

    private List<String> touch(){
        List<String> list = new ArrayList<>();
        list.add(sendevent+" 3 57 14");
        list.add(sendevent+" 1 330 1");
        list.add(sendevent+" 1 325 1");

        return list;
    }
    private List<String> move(int x, int y){
        List<String> list = new ArrayList<>();
        list.add(sendevent+" 3 53 "+x);
        list.add(sendevent+" 3 54 "+y);
        list.add(sendevent+" 0 0 0");
        return list;
    }

    private List<String> release(){
        List<String> list = new ArrayList<>();
        list.add(sendevent+" 3 57 4294967295");
        list.add(sendevent+" 1 330 0");
        list.add(sendevent+" 1 325 0");
        list.add(sendevent+" 0 0 0");


        return list;
    }
}
