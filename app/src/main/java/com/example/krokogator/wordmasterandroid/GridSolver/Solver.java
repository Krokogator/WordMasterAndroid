package com.example.krokogator.wordmasterandroid.GridSolver;

import android.content.Context;
import android.util.Log;

import com.example.krokogator.wordmasterandroid.Dictionary.Dictionary;
import com.example.krokogator.wordmasterandroid.Dictionary.Tree;
import com.example.krokogator.wordmasterandroid.TouchEmulation.Point;
import com.example.krokogator.wordmasterandroid.TouchEmulation.TouchCommand;
import com.example.krokogator.wordmasterandroid.TouchEmulation.TouchEmulator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by micha on 08.01.2018.
 */

public class Solver {
    private Dictionary dictionary;
    private TouchEmulator touchEmulator;

    private char[] grid = new char[16];
    private List<Box> boxes= new ArrayList<>();
    private List<List<Pair>> paths = new ArrayList<>();

    public Solver(Context context){
        this.dictionary = new Dictionary(context);
        this.touchEmulator = new TouchEmulator();



    }

    //runs my custom DFS algorithm on each letter/box
    public List<List<String>> findPaths(char[] grid){
        this.grid = grid;
        List<Integer> noDeadBoxes = new ArrayList<>();
        this.boxes = initBoxes(noDeadBoxes);
        paths.clear();
        for(int i=0;i<16;i++) {
            List<Pair> currentPath = new ArrayList<>();
            Box box = boxes.get(i);
            List<Integer> indexDead = new ArrayList<>();
            indexDead.add(i+1);
            List<Box> newboxes = initBoxes(indexDead);
            customDFS(box, newboxes, paths, currentPath);
        }
        //displayListOfListsOfPairs(paths);
        return displaySorted(deleteDuplicates(paths));
    }

    /**
     * custom DFS algorithm that looks for words > 3 && < 17 in a 4x4 letter grid
     *
     * - each call checks if current path creates a valid (meaningful) word
     * - each call checks if current path(word) + box(letter) that can be visited can create a subword of a valid word
     */
    private List<List<Pair>> customDFS(Box box, List<Box> aliveBoxes, List<List<Pair>> validPaths, List<Pair> possiblePath){

        List<Integer> deadBoxes = getDead(aliveBoxes);
        deadBoxes.add(box.getId());
        List<Box> nextAliveBoxes = initBoxes(deadBoxes);

        List<Pair> currentPath = new ArrayList<>();
        currentPath.addAll(possiblePath);
        currentPath.add(new Pair(box.getId(),box.getLetter()));
        if(currentPath.size()>2&&dictionary.isWord(pairsToWord(currentPath))){
            validPaths.add(currentPath);
        }

        for(Box alive : nextAliveBoxes){
            if(contains(box.getNeighbours(),alive.getId())){

                List<Pair> tempPath = new ArrayList<>();
                tempPath.addAll(currentPath);
                tempPath.add(new Pair(alive.getId(),alive.getLetter()));

                if(dictionary.isWordBegining(pairsToWord(tempPath))){
                    customDFS(alive, nextAliveBoxes, paths, currentPath);
                }
            }
        }
        return validPaths;
    }

    //Translates list of pairs into a word
    private String pairsToWord(List<Pair> pairs){
        String result="";
        for(Pair pair : pairs){
            result=result+pair.getLetter();
        }
        return result;
    }

    private List<Integer> pairsToPaths(List<Pair> pairs){
        List<Integer> paths = new ArrayList<>();
        for(Pair pair : pairs){
            paths.add(pair.getId());
        }
        return paths;
    }

    private List<List<Pair>> deleteDuplicates(List<List<Pair>> paths){
        List<List<Pair>> noDups = new ArrayList<>();
        Set<List<Pair>> toDelete = new HashSet<>();
        System.out.println(paths.size());
        for(List<Pair> path : paths){
            for(List<Pair> path2 : paths){
                if(!pairsToPaths(path).equals(pairsToPaths(path2))&&!(toDelete.contains(path)||toDelete.contains(path2))){
                    if(pairsToWord(path).equals(pairsToWord(path2))){
                        toDelete.add(path);
                        System.out.println("usuwam: = "+pairsToWord(path));
                    }
                }
            }
        }

        paths.removeAll(toDelete);
        System.out.println(paths.size());
        return paths;
    }

    //Returns "dead" (used/seen) letters in each call of customDFS
    private List<Integer>getDead(List<Box> Alive){
        List<Integer> alive = new ArrayList<>();
        List<Integer> dead = new ArrayList<>();

        for(Box box : Alive){
            alive.add(box.getId());
        }

        for(int i=1;i<17;i++){
            if(!alive.contains(i)){
                dead.add(i);
            }
        }

        return dead;
    }

    private List<List<String>> displaySorted(List<List<Pair>> listOfLists) {   //List<List<DictionaryController.Pair>> listOfLists
        List<String> listOfStrings = new ArrayList<>();
        List<List<Integer>> listOfInts = new ArrayList<>();
        for (List<Pair> list : listOfLists) {
            listOfStrings.add(pairsToWord(list));
            listOfInts.add(pairsToPaths(list));
        }


        List<String> sorted = sortList(listOfStrings);

        for (String word : sorted) {
            System.out.println(word);
        }

        List<List<Integer>> ordered = new ArrayList<>();
        int length=16;
        List<List<Integer>> toRemove = new ArrayList<>();
        while(!listOfInts.isEmpty()) {
            for (List<Integer> path : listOfInts) {
                if (path.size() == length) {
                    ordered.add(path);
                    toRemove.add(path);
                }
                listOfInts.remove(toRemove);
                toRemove.clear();

            }
            length--;
            if(length<3){ break;}
        }

        List<List<String>> commands = new ArrayList<>();
        for (List<Integer> path : ordered) {
        List<Point> points = createTouchSequence(path);
        TouchCommand command = new TouchCommand();
        commands.add(command.newTouchCommand(points));
        }

        return commands;
    }

    private String xInput(int x){
        return "adb shell sendevent /dev/input/event1 3 53 "+x;
    }

    private String yInput(int y){
        return "adb shell sendevent /dev/input/event1 3 54 "+y;
    }

    private List<Point> createTouchSequence(List<Integer> path){
        int x1=200,x2=550,x3=900,x4=1250;
        int y1=1050,y2=1470,y3=1890,y4=2310;

        List<Point> points = new ArrayList<>();

        for(Integer id : path){
            if(id==1){points.add(new Point(x1,y1));}
            else if(id==2){points.add(new Point(x2,y1));}
            else if(id==3){points.add(new Point(x3,y1));}
            else if(id==4){points.add(new Point(x4,y1));}
            else if(id==5){points.add(new Point(x1,y2));}
            else if(id==6){points.add(new Point(x2,y2));}
            else if(id==7){points.add(new Point(x3,y2));}
            else if(id==8){points.add(new Point(x4,y2));}
            else if(id==9){points.add(new Point(x1,y3));}
            else if(id==10){points.add(new Point(x2,y3));}
            else if(id==11){points.add(new Point(x3,y3));}
            else if(id==12){points.add(new Point(x4,y3));}
            else if(id==13){points.add(new Point(x1,y4));}
            else if(id==14){points.add(new Point(x2,y4));}
            else if(id==15){points.add(new Point(x3,y4));}
            else if(id==16){points.add(new Point(x4,y4));}
        }

        Log.i("IMAGE","Word length: "+points.size());

        return points;
    }

    public static long timer(int length){
        long timer=0;
        //*250 +1050 - ac
        timer=(length-2) *190 +1100;
        long start = System.currentTimeMillis();
        long elapsed = System.currentTimeMillis() - start;
        while(elapsed < timer){
            elapsed = System.currentTimeMillis() - start;
        }
        return elapsed;
    }

    private void runPath(List<Integer> path){


        //touchEmulator.emulateTouch(points);
    }

    public List<String> sortList(List<String> mylist){

        Comparator<String> x = new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                if(o1.length() > o2.length())
                    return 1;

                if(o2.length() > o1.length())
                    return -1;

                return 0;
            }
        };

        Collections.sort(mylist,  x);

        List<String> noDups = new ArrayList<>();
        for(String w1 : mylist){
            if(!noDups.contains(w1)){
                noDups.add(w1);
            }
        }

        return noDups;
    }

    //Displays on standard output list of lists of pairs
    private void displayListOfListsOfPairs(List<List<Pair>> listOfLists){
        for(List<Pair> list : listOfLists){
            System.out.println(pairsToWord(list));
        }
    }

    //Checks if list of boxes contains given id
    private boolean contains(List<Box> boxes, int id){
        for(Box box : boxes){
            if(box.getId()==id){
                return true;
            }
        }
        return false;
    }

    //Returns box of given id
    private Box getBox(List<Box> boxes, int id){
        for(Box box:boxes){
            if(box.getId()==id){
                return box;
            }
        }
        return null;
    }

    //initialize given 4x4 grid. In each call of customDFS it creates smaller grid (without "dead" boxes)
    private List<Box> initBoxes(List<Integer> deadBoxes){
        List<Box> boxes = new ArrayList<>();

        for(int x=0;x<16;x++){
            boxes.add(new Box(x + 1, grid[x]));
        }


        /**
         *   1  2  3  4
         *   5  6  7  8
         *   9  10 11 12
         *   13 14 15 16
         */

        //first row
        addNeighbours(1, new int[]{2,5,6}, boxes);
        addNeighbours(2,new int[]{1,3,5,6,7}, boxes);
        addNeighbours(3,new int[]{2,4,6,7,8}, boxes);
        addNeighbours(4,new int[]{3,7,8}, boxes);
        addNeighbours(5,new int[]{1,2,6,9,10}, boxes);
        addNeighbours(6,new int[]{1,2,3,5,7,9,10,11}, boxes);
        addNeighbours(7,new int[]{2,3,4,6,8,10,11,12}, boxes);
        addNeighbours(8,new int[]{3,4,7,11,12}, boxes);
        addNeighbours(9,new int[]{5,6,10,13,14}, boxes);
        addNeighbours(10,new int[]{5,6,7,9,11,13,14,15}, boxes);
        addNeighbours(11,new int[]{6,7,8,10,12,14,15,16}, boxes);
        addNeighbours(12,new int[]{7,8,11,15,16}, boxes);
        addNeighbours(13,new int[]{9,10,14}, boxes);
        addNeighbours(14,new int[]{9,10,11,13,15}, boxes);
        addNeighbours(15,new int[]{10,11,12,14,16}, boxes);
        addNeighbours(16,new int[]{11,12,15}, boxes);

        return removeBoxes(boxes,deadBoxes);
    }

    //gives each box (letter) information about its neighbourhood (surrounding letters)
    private void addNeighbours(int index, int[] neighbours, List<Box> boxes){
        index=index - 1;
        for(int neighbour : neighbours) {
            neighbour=neighbour-1;
            boxes.get(index).addNeighbours(boxes.get(neighbour));
        }
    }

    //removes elements from list of boxes
    private List<Box> removeBoxes(List<Box> boxes, List<Integer> toKillList){
        for(int toKill : toKillList){
            if(contains(boxes,toKill)){
                boxes.remove(getBox(boxes,toKill));
            }
        }
        return boxes;
    }
}
