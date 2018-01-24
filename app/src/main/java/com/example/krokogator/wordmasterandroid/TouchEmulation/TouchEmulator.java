package com.example.krokogator.wordmasterandroid.TouchEmulation;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.MotionEvent;

import com.example.krokogator.wordmasterandroid.Utility.CommandExecutor;

import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class TouchEmulator {
    private CommandExecutor cmd;

    public TouchEmulator(){cmd = new CommandExecutor();}

    public void emulate(List<String> touchCommand) {
        cmd.sudo(touchCommand);
    }
}
