package com.example.krokogator.wordmasterandroid.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 08.01.2018.
 */

public class Node {
    private char letter;
    private List<Node> children = new ArrayList<>();

    public Node(char letter){
        this.letter=letter;
    }

    public Node(char letter, boolean isWord){
        this.letter = letter;
        if(isWord){ this.addNullChild(); }
    }

    public Character getLetter(){
        return Character.valueOf(letter);
    }

    public void addNullChild(){
        children.add(null);
    }

    public boolean isValid(){
        for(Node child : getChildren()){
            if(child==null){
                return true;
            }
        }
        return false;
    }

    public Node addChild(char letter, boolean isWord){
        Node child = new Node(letter,isWord);
        children.add(child);
        return child;
    }

    public List<Node> getChildren(){  return children;  }
}
