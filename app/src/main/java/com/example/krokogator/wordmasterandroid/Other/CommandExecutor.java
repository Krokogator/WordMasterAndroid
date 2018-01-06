package com.example.krokogator.wordmasterandroid.Other;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class CommandExecutor {
    public static void sudo(String string){
        try{
            Process su;
            su = Runtime.getRuntime().exec("su");

            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(string+"\n");
            outputStream.flush();


            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void sudo(List<String> strings) {
        try{
            Process su;
            su = Runtime.getRuntime().exec("su");

            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s+"\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
