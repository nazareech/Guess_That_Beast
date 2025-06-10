package com.example.guess_that_beast;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class GameStateManager {
    private static final boolean DEV_MODE = true; // Увімкнути режим розробки

    private static final String SAVE_FILE = "gamestate.json";
    private static final String ENCRYPTION_KEY = "YourStrongKey123"; // 16 символів
    private final ObjectMapper mapper = new ObjectMapper();

    public GameData loadGameData() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try {
                if (DEV_MODE) {
                    // У режимі розробника читаємо файл без будь-яких перевірок
                    return mapper.readValue(file, GameData.class);
                } else {
                    // У звичайному режимі – захищене читання
                    String encryptedData = new String(Files.readAllBytes(file.toPath()));
                    String decryptedData = decrypt(encryptedData);
                    return mapper.readValue(decryptedData, GameData.class);
                }
            } catch (Exception e) {
                System.out.println("Не вдалося зчитати стан гри: " + e.getMessage());
                return createDefaultGameData();
            }
        }
        return createDefaultGameData();
    }


    public void saveGameData(GameData gameData) {
        try {
            if (DEV_MODE) {
                // У режимі розробника записуємо без шифрування
                mapper.writeValue(new File(SAVE_FILE), gameData);
            } else {
                // У звичайному режимі – захищений запис
                String jsonData = mapper.writeValueAsString(gameData);
                String encryptedData = encrypt(jsonData);
                Files.write(new File(SAVE_FILE).toPath(), encryptedData.getBytes());
            }
            System.out.println("Гра успішно збережена у: " + SAVE_FILE);
        } catch (Exception e) {
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

    // Метод шифрування
    private String encrypt(String data) throws Exception {
        SecretKey key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Метод розшифрування
    private String decrypt(String encryptedData) throws Exception {
        SecretKey key = new SecretKeySpec(ENCRYPTION_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }
}
