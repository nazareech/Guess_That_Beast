package com.example.guess_that_beast;

public class UnlockingLevelsManager {

    private GameStateManager gameStateManager;
    private GameData gameData;

    private final int MAX_LEVELS = 6;

    public UnlockingLevelsManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameData = gameStateManager.loadGameData();
    }

    public boolean nextLevelIsUnlocked(float correctAnswers, int currentLevel){

        this.gameData = gameStateManager.loadGameData();
        if (currentLevel == gameData.getLevelsUnlocked()) {
            if (correctAnswers >= 50) {
                unlockNextLevel();
                return true;
            }
        }
        return false;
    }

    private void unlockNextLevel(){
        if(gameData.getLevelsUnlocked() < MAX_LEVELS){
            gameData.setLevelsUnlocked(gameData.getLevelsUnlocked() + 1);
            gameStateManager.saveGameData(gameData);
        }
    }
}
