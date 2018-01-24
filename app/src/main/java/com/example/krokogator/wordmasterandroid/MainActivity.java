package com.example.krokogator.wordmasterandroid;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.krokogator.wordmasterandroid.GridSolver.Solver;
import com.example.krokogator.wordmasterandroid.ImageController.ImageAnalyzer;
import com.example.krokogator.wordmasterandroid.ImageController.ImageLoader;
import com.example.krokogator.wordmasterandroid.Utility.PermissionVerifier;

import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Thread thread;
    private TestThread testT;
    private Button startButton;
    private Button stopButton;
    private ActivityManager am;
    private TextView serviceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionVerifier.isStoragePermissionGranted(this);
        startButton = findViewById(R.id.button);
        stopButton = findViewById(R.id.button2);
        serviceStatus = findViewById(R.id.serviceStatus);
        stopButton.setEnabled(false);
        updateStatus("Offline");
    }



    public void onClickStartThread(View view){
        if(testT == null){
            startButton.setEnabled(false);
            testT = new TestThread(getApplicationContext(), this);
            thread = new Thread(testT);
            thread.start();
            stopButton.setEnabled(true);
        }
    }

    public void onClickStopThread(View view){
        if(testT!=null){
            testT.isRunning=false;
            testT.isRound = false;
            testT = null;
            stopButton.setEnabled(false);
            startButton.setEnabled(true);
            updateStatus("Offline");
        }
    }

    @Override
    protected void onPause() {
        //startService(backgroundService);
        super.onPause();
    }

    @Override
    protected void onResume() {
        //stopService(backgroundService);
        super.onResume();
    }

    public ActivityManager.RunningAppProcessInfo getForegroundApp() throws PackageManager.NameNotFoundException {
        ActivityManager.RunningAppProcessInfo result = null, info = null;
        String currApp = null;
        am = (ActivityManager) getApplicationContext().getSystemService(ACTIVITY_SERVICE);
        Drawable icon = null;
        List<ActivityManager.RunningAppProcessInfo> l = am.getRunningAppProcesses();
        Iterator<ActivityManager.RunningAppProcessInfo> i = l.iterator();
        PackageManager pm = getApplicationContext().getPackageManager();

        while (i.hasNext()) {
            info = i.next();
            if (info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && getActivityForApp(info) != null) {

                try {
                    System.out.println(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    icon = pm.getApplicationIcon(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    System.out.println(getActivityForApp(info));
                } catch (PackageManager.NameNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            break;
        }

        return result;
    }

    private ComponentName getActivityForApp(ActivityManager.RunningAppProcessInfo target){
        ComponentName result=null;
        ActivityManager.RunningTaskInfo info;

        if(target==null)
            return null;

        if(am==null)
            am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List <ActivityManager.RunningTaskInfo> l = am.getRunningTasks(9999);
        Iterator <ActivityManager.RunningTaskInfo> i = l.iterator();

        while(i.hasNext()){
            info=i.next();
            if(info.baseActivity.getPackageName().equals(target.processName)){
                result=info.topActivity;
                break;
            }
        }
        return result;
    }

    private void updateStatus(String status){
        String str = "Service status: ";
        this.serviceStatus.setText(str+status);
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            updateStatus((String)msg.obj);
            super.handleMessage(msg);
        }
    };

}
