package com.example.krokogator.wordmasterandroid.Dictionary;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.krokogator.wordmasterandroid.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by micha on 08.01.2018.
 */

public class DictionaryLoader {
    private Context context;

    public DictionaryLoader(Context myContext){
        this.context = myContext;
    }

    public Tree loadToTree(Tree tree) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open("dictionary_pl.txt")));

            // do reading, usually loop until end of file reading

            String line;
            while ((line = reader.readLine()) != null) {
                    if(!line.equals("")) {
                        tree.addWord(line);
                    }
            }

        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return tree;
    }
}
