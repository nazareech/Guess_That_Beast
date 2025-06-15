package com.example.guess_that_beast;

public class ScoreManager implements Manager {
    private GameStateManager gameStateManager;
    private GameData gameData;

    public ScoreManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameData = gameStateManager.loadGameData();
    }

    public int getCurrentScore() {
        return gameData.getScore();
    }

    public int calculatePoints(final int incorectANswers, final int allQuestions, final int time) {
        int finalPoints;
        int timeOfReward;

        int numberOfReward = allQuestions - incorectANswers;

        if (time <= 30){
            timeOfReward = 30 - (int)time;
        }else {
            timeOfReward = 0;
        }

        finalPoints = (numberOfReward * 100) + (timeOfReward * 10);

        addPoints(finalPoints);

        return finalPoints;
    }

    @Override
    public void initialize() {
        System.out.println("ScoreManager has been initialized!");

    }

    public void addPoints(int points) {
        int newPoints = gameData.getScore() + points;
        System.out.println("Отримано балів: " + points);
        System.out.println("Всього балів: " + newPoints);
        gameData.setScore(newPoints);
        gameStateManager.saveGameData(gameData);
    }

    public void deductPoints(int points){
        int subtractPoints = gameData.getScore() - points;

        if (subtractPoints < 0) subtractPoints = 0; // Безпечна логіка

        this.gameData = gameStateManager.loadGameData();
        System.out.println("Віднято балів: " + points);
        System.out.println("Всього балів: " + subtractPoints);
        gameData.setScore(subtractPoints);
        gameStateManager.saveGameData(gameData);
    }
}
