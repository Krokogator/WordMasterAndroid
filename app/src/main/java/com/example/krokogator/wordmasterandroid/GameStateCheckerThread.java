package com.example.krokogator.wordmasterandroid;

/**
 * Created by micha on 08.01.2018.
 */

public class GameStateCheckerThread implements Runnable {
    private boolean isRoundPlaying;


    public GameStateCheckerThread(){
        isRoundPlaying = false;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                updateState();
                wait(1000);
            } catch (InterruptedException ex) {
                //Thread.currentThread().interrupt();
            }
        }
    }

    public boolean isRoundPlaying(){ return isRoundPlaying; }

    private void updateState(){
        //updates the state based on imageLoader and imageAnalyzer
    }
}
