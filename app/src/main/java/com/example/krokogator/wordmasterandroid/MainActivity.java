package com.example.krokogator.wordmasterandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent msgIntent = new Intent(this, BackgroundServiceTEST.class);
        startService(msgIntent);
    }


}
