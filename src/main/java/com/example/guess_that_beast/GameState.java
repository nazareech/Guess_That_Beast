package com.example.guess_that_beast;

public class GameState {
    private static final GameState instance = new GameState();

    private int lives = 5;
    private long lastLifeLostTime = System.currentTimeMillis();
    private final int MAX_LIVES = 5;

    private GameState() {}

    public static GameState getInstance() {
        return instance;
    }

    public int getLives() {
        checkAndRestoreLives(); // Автоматичне відновлення
        return lives;
    }

    public void loseLife() {
        if (lives > 0) {
            lives--;
            lastLifeLostTime = System.currentTimeMillis();
        }
    }

    public void checkAndRestoreLives() {
        long now = System.currentTimeMillis();
        long millisSinceLast = now - lastLifeLostTime;

        int livesToRestore = (int) (millisSinceLast / (1 * 60 * 1000)); // 5 хв на 1 життя

        if (livesToRestore > 0 && lives < MAX_LIVES) {
            lives = Math.min(MAX_LIVES, lives + livesToRestore);
            lastLifeLostTime = now - (millisSinceLast % (5 * 60 * 1000));
        }
    }

}
