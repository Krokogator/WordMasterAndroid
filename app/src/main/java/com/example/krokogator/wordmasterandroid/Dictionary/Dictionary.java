package com.example.krokogator.wordmasterandroid.Dictionary;

import android.content.Context;

/**
 * Created by micha on 08.01.2018.
 */

public class Dictionary {
    private Tree dictionaryTree;

    public Dictionary(Context context){
        dictionaryTree = new Tree();
        DictionaryLoader loader = new DictionaryLoader(context);

        //Builds tree implementation of dictionary
        loader.loadToTree(dictionaryTree);

    }

    public boolean checkWord(String word){
        return dictionaryTree.checkWord(word);
    }
}
