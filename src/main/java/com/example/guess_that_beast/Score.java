package com.example.guess_that_beast;

public class Score {
    private int totalScore;

    public Score() {
        this.totalScore = 0;
    }

    public int getTotalScore() {
        return totalScore;
    }

    private void calculatePoints(final int incorectANswers, final int allQuestions) {


    }

    public void addPoints(int points) {
        this.totalScore += points;
    }
}
