package com.example.krokogator.wordmasterandroid.Other;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by micha on 06.01.2018.
 */

public class CommandExecutor {

    public void sudo(String string){
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

    public void sudo(List<String> strings) {
        try{
            Process su = Runtime.getRuntime().exec("su");

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
