package com.example.guess_that_beast;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;



public class Controller {

    @FXML private Text levelChoice;
    @FXML private Label pointsLabel;
    @FXML private Label lives;

    @FXML private Stage stage; // Поточна сцена
    @FXML private Scene scene; // Поточна сцена
    private Parent root; // Корінь нової сцени

    // індекси для вибору мемів для рівня
    private int startMemIndex;
    private int endMemIndex;

    private GameStateManager gameStateManager = new GameStateManager();
    private LivesManager livesManager = new LivesManager(gameStateManager);
    private ScoreManager scoreManager = new ScoreManager(gameStateManager);

    @FXML
    private void initialize() {
        updateLivesDisplay();

        // Відкладена ініціалізація кнопок
        Platform.runLater(() -> {
            updateLevelButtons();
        });
    }

    //Перехід до рівня
    @FXML
    private void openLevel(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        int currentLevel = 0;

        switch (buttonId) {
            case "level1":
                startMemIndex = 0;
                endMemIndex = 5;

                currentLevel = 1;
                break;

            case "level2":
                startMemIndex = 5;
                endMemIndex = 10;

                currentLevel = 2;
                break;

            case "level3":
                startMemIndex = 10;
                endMemIndex = 15;

                currentLevel = 3;
                break;

            case "level4":
                startMemIndex = 15;
                endMemIndex = 20;

                currentLevel = 4;
                break;

            case "level5":
                startMemIndex = 20;
                endMemIndex = 25;

                currentLevel = 5;
                break;

            case "level6":
                startMemIndex = 25;
                endMemIndex = 30;

                currentLevel = 6;
                break;

        }

        if(livesManager.getCurrentLives()>0) {
            String fxmlPath = "/com/example/guess_that_beast/view-level.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            root.getStylesheets().add(getClass().getResource("/Level_style.css").toExternalForm());

            List<Meme> memes = MemeLoader.loadMemes();
            LevelController levelController = loader.getController();
            levelController.initializeWithMemes(memes, startMemIndex, endMemIndex, currentLevel); // Тепер передаємо всі меми

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            System.out.println("Вибрано рівень: " + buttonId);
        }else{
            levelChoice.setText("Unfortunately, you have lost all your li");
        }
    }

    //Перехід до рівня
    @FXML
    private void openShop(ActionEvent event) throws IOException {

        String fxmlPath = "/com/example/guess_that_beast/view-shop.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

//        root.getStylesheets().add(getClass().getResource("/Level_style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        System.out.println("Перехід до магазину");

    }

    private void updateLivesDisplay(){
        int currentLives = livesManager.getCurrentLives();
        int currenttPoints = scoreManager.getCurrentScore();

        lives.setText("Lives: " + currentLives);
        System.out.println("Lives: " + currentLives);
        pointsLabel.setText("Points: " + currenttPoints);
        System.out.println("Points: " + currenttPoints);
    }

    private void updateLevelButtons() {
        if (scene == null) {
            scene = levelChoice.getScene(); // або будь-який інший @FXML елемент
        }

        GameData gameData = gameStateManager.loadGameData();
        int levelsUnlocked = gameData.getLevelsUnlocked();

        // Заблокуйте кнопки для рівнів, які ще не відкриті
        for (int i = 1; i <= 6; i++) { // Якщо у вас 6 рівнів
            Button levelButton = (Button) scene.lookup("#level" + i);
            if (levelButton != null) {
                levelButton.setDisable(i > levelsUnlocked);
            }
        }
    }
}