package com.example.krokogator.wordmasterandroid.Utility;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.Socket;
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
            Log.i("CommandExecutor","Input word");
            Process su = Runtime.getRuntime().exec("su");

            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s+"\n");
                //Log.i("Master",s);
                outputStream.flush();
            }
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                su.destroy();
            }
            outputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public String sudoForResult(String...strings) {
        String res = "";
        DataOutputStream outputStream = null;
        InputStream response = null;
        try{
            Process su = Runtime.getRuntime().exec("su");
            outputStream = new DataOutputStream(su.getOutputStream());
            response = su.getInputStream();

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
            res = readFully(response);
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            closeSilently(outputStream, response);
        }
        return res;
    }
    public String readFully(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString("UTF-8");
    }
        // closeAll()
        public void closeSilently(Object... xs) {
            // Note: on Android API levels prior to 19 Socket does not implement Closeable
            for (Object x : xs) {
                if (x != null) {
                    try {
                        if (x instanceof Closeable) {
                            ((Closeable)x).close();
                        } else if (x instanceof Socket) {
                            ((Socket)x).close();
                        } else if (x instanceof DatagramSocket) {
                            ((DatagramSocket)x).close();
                        } else {
                            throw new RuntimeException("cannot close "+x);
                        }
                    } catch (Throwable e) {
                    }
                }
            }
        }

}
