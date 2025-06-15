package com.example.guess_that_beast;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.List;



public class Controller {

    @FXML private Text levelChoice;
    @FXML private Label pointsLabel;
    @FXML private Label lives;

    @FXML private ImageView livesImg;
    @FXML private ImageView pointsImg;
    @FXML private ImageView shopImage;
    @FXML private ImageView mainIconImage;

    @FXML private Stage stage; // Поточна сцена
    @FXML private Scene scene; // Поточна сцена
    private Parent root; // Корінь нової сцени

    // індекси для вибору мемів для рівня
    private int startMemIndex;
    private int endMemIndex;

    private GameStateManager gameStateManager = new GameStateManager();
    private LivesManager livesManager = new LivesManager(gameStateManager);
    private ScoreManager scoreManager = new ScoreManager(gameStateManager);
    private UnlockingLevelsManager unlockingLevelsManager = new UnlockingLevelsManager(gameStateManager);

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
        loadLevel(currentLevel);

        if(livesManager.getCurrentLives()>0) {
            String fxmlPath = "/Views_fxml_files/view-level.fxml";
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            root.getStylesheets().add(getClass().getResource("/Style/Level_style.css").toExternalForm());

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

    public void loadLevel(int level) {
        try {
           unlockingLevelsManager.validateLevel(level); // Перевіряємо рівень
            System.out.println("Завантажується рівень: " + level);
        } catch (InvalidLevelException e) {
            showErrorDialog(e.getMessage());
        }
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Помилка рівня");
        alert.setHeaderText("Неможливо завантажити рівень.");
        alert.setContentText(message);
        alert.showAndWait();
    }


    //Перехід до рівня
    @FXML
    private void openShop(ActionEvent event) throws IOException {

        String fxmlPath = "/Views_fxml_files/view-shop.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        root.getStylesheets().add(getClass().getResource("/Style/Shop_style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        System.out.println("Перехід до магазину");

    }

    private void updateLivesDisplay(){
        int currentLives = livesManager.getCurrentLives();
        int currenttPoints = scoreManager.getCurrentScore();

        Image image;

        if (currentLives == 0) {
            try {
                image = new Image(getClass().getResource("/img/Interface icons/FewLive.png").toExternalForm());
                livesImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }else{
            try {
                image = new Image(getClass().getResource("/img/Interface icons/Live.png").toExternalForm());
                livesImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        if (currenttPoints == 0) {
            try {
                image = new Image(getClass().getResource("/img/Interface icons/FewPoint.png").toExternalForm());
                pointsImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }else{
            try {
                image = new Image(getClass().getResource("/img/Interface icons/Point.png").toExternalForm());
                pointsImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        try {
            image = new Image(getClass().getResource("/img/Interface icons/Shop.png").toExternalForm());
            shopImage.setImage(image);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        try {
            image = new Image(getClass().getResource("/img/Interface icons/Icon.png").toExternalForm());
            mainIconImage.setImage(image);
        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }


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