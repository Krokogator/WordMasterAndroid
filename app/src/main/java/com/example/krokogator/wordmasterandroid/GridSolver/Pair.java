package com.example.krokogator.wordmasterandroid.GridSolver;

/**
 * Created by micha on 08.01.2018.
 */

class Pair{

    /**
     * Pairs (letter,id)
     * Each path made of those pairs
     */

    Character letter;
    int id;
    public Pair(int id, Character letter){
        this.letter=letter;
        this.id=id;
    }

    public Character getLetter(){
        return letter;
    }
    public int getId() { return  id; }
}
