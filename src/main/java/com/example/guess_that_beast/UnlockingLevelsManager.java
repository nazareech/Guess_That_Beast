package com.example.guess_that_beast;

public class UnlockingLevelsManager implements Manager {

    private final int MAX_LEVELS = 6;
    private GameStateManager gameStateManager;
    private GameData gameData;

    public UnlockingLevelsManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameData = gameStateManager.loadGameData();
    }

    public boolean nextLevelIsUnlocked(float correctAnswers, int currentLevel){
        validateLevel(currentLevel); // Перевіряємо рівень

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

    public void validateLevel(int level) {
        if (level < 0 || level > MAX_LEVELS) {
            throw new InvalidLevelException("Рівень " + level + " недійсний. Дозволено лише значення між 0 і " + MAX_LEVELS + ".");
        }
    }


    @Override
    public void initialize() {
        System.out.println("UnlockingLevelsManager has been initialized!");
    }
}
