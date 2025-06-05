package com.example.guess_that_beast;

public class GameData {
    private int lives;
    private long lastLifeLostTime;
    private int score;
    private int levelsUnlocked;

    // Геттери та сеттери
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }

    public long getLastLifeLostTime() { return lastLifeLostTime; }
    public void setLastLifeLostTime(long lastLifeLostTime) { this.lastLifeLostTime = lastLifeLostTime; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getLevelsUnlocked() { return levelsUnlocked; }
    public void setLevelsUnlocked(int levelsUnlocked) { this.levelsUnlocked = levelsUnlocked; }
}