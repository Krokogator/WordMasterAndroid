package com.example.krokogator.wordmasterandroid.TouchEmulation;

import com.example.krokogator.wordmasterandroid.Other.CommandExecutor;

import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class TouchEmulator {
    private CommandExecutor cmd;

    public TouchEmulator(){cmd = new CommandExecutor();}

    //Emulates touches with given list of points
    public void emulateTouch(List<Point> points){
        TouchCommand command = new TouchCommand();
        emulate(command.newTouchCommand(points));
    }

private void emulate(List<String> touchCommand){
        cmd.sudo(touchCommand);
    }
}
