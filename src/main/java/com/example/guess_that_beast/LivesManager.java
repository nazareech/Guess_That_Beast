package com.example.guess_that_beast;

public class LivesManager {
    private static final int MAX_LIVES = 5;
    private static final int TIME_TO_RESTORE_MINUTES = 5;

    private GameData gameData;
    private GameStateManager gameStateManager;

    public LivesManager(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.gameData = gameStateManager.loadGameData();
    }

    public int getCurrentLives() {
        checkAndRestoreLives(); // Відновлення життів
        gameStateManager.saveGameData(gameData); // Зберігаємо актуальні дані
        return gameData.getLives();

    }

    public void loseLife() {
        if (gameData.getLives() > 0) {
            gameData.setLives(gameData.getLives() - 1);
            gameData.setLastLifeLostTime(System.currentTimeMillis());
            gameStateManager.saveGameData(gameData);
        }
    }

    public void addLifes(int numberLifes){
        int addedLifes = gameData.getLives() + numberLifes;

        if(addedLifes >= MAX_LIVES){ addedLifes = MAX_LIVES;}

        System.out.println("Додано " + numberLifes + " життів");
        this.gameData = gameStateManager.loadGameData();
        gameData.setLives(addedLifes);
        gameStateManager.saveGameData(gameStateManager.loadGameData());
    }

    private void checkAndRestoreLives() {
        long now = System.currentTimeMillis();
        long millisSinceLast = now - gameData.getLastLifeLostTime();

        int livesToRestore = (int) (millisSinceLast / (TIME_TO_RESTORE_MINUTES * 60 * 1000));

        if (livesToRestore > 0 && gameData.getLives() < MAX_LIVES) {
            gameData.setLives(Math.min(MAX_LIVES, gameData.getLives() + livesToRestore));
            gameData.setLastLifeLostTime(now - (millisSinceLast % (TIME_TO_RESTORE_MINUTES * 60 * 1000)));
        }
    }

    // Додаткові методи для роботи з життями, якщо потрібно
}