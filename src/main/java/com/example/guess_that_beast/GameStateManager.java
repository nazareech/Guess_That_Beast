package com.example.guess_that_beast;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GameStateManager {
    private static final String SAVE_FILE = "gamestate.json";
    private final ObjectMapper mapper = new ObjectMapper();

    public GameData loadGameData() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try {
                return mapper.readValue(file, GameData.class);
            } catch (IOException e) {
                System.out.println("Не вдалося зчитати стан гри: " + e.getMessage());
                return createDefaultGameData();
            }
        }
        return createDefaultGameData();
    }

    public void saveGameData(GameData gameData) {
        System.out.println("Збережено рівень життя: " + gameData.getLives());
        System.out.println("Збережено всього балів: " + gameData.getScore());
        try {
            File file = new File(SAVE_FILE);
            mapper.writeValue(file, gameData);
            System.out.println("Гра успішно збережена у " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Не вдалося зберегти стан гри: " + e.getMessage());
        }
    }

    private GameData createDefaultGameData() {
        GameData defaultData = new GameData();
        defaultData.setLives(5);
        defaultData.setLastLifeLostTime(System.currentTimeMillis());
        defaultData.setScore(0);
        defaultData.setLevelsUnlocked(1);
        return defaultData;
    }
}