package com.example.guess_that_beast;

public class GameState {
    private static GameState instance;
    private int lives = 5;
    private long[] lostLivesTimetamps = new long[2]; // Зберігає час втрати кожного серця

    private GameState() {}

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public int getLives() {
        return lives;
    }

    private void loseIives() {
        if (lives > 0) {
            lostLivesTimetamps[lives -1] = System.currentTimeMillis();
            lives -= 1;
        }
    }

    private void checkAndRestoreLives(){
        long now = System.currentTimeMillis();
        for(int i = 0; i < lostLivesTimetamps.length; i++){
            if (lives < 3 && lostLivesTimetamps[i] > 0) {
                if(now - lostLivesTimetamps[i] >= 5 * 6 * 1000) {
                    lives++;
                    lostLivesTimetamps[i] = 0;
                }
            }
        }
    }

}
