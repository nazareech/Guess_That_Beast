package com.example.guess_that_beast;

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

    @FXML private Label lives;

    @FXML
    private Stage stage; // Поточна сцена
    @FXML
    private Scene scene; // Поточна сцена
    private Parent root; // Корінь нової сцени

    // індекси для вибору мемів для рівня
    private int startMemIndex;
    private int endMemIndex;

    GameStateManager gameStateManager = new GameStateManager();
    private LivesManager livesManager = new LivesManager(gameStateManager);


    @FXML
    private void initialize() {
        updateLivesDisplay();


    }

    //Перехід до рівня
    @FXML
    private void openLevel(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "level1":
                startMemIndex = 0;
                endMemIndex = 5;
                break;

            case "level2":
                startMemIndex = 5;
                endMemIndex = 10;
                break;

            case "level3":
                startMemIndex = 10;
                endMemIndex = 15;
                break;

            case "level4":
                startMemIndex = 15;
                endMemIndex = 20;
                break;

            case "level5":
                startMemIndex = 20;
                endMemIndex = 25;
                break;

            case "level6":
                startMemIndex = 25;
                endMemIndex = 30;
                break;

        }

        String fxmlPath = "/com/example/guess_that_beast/view-level.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        root.getStylesheets().add(getClass().getResource("/Level_style.css").toExternalForm());

        List<Meme> memes = MemeLoader.loadMemes();
        LevelController levelController = loader.getController();
        levelController.initializeWithMemes(memes, startMemIndex, endMemIndex); // Тепер передаємо всі меми

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        System.out.println("Вибрано рівень: " + buttonId);


    }

    private void updateLivesDisplay(){
        int getLives = livesManager.getCurrentLives();
        lives.setText("Lives: " + getLives);
        System.out.println("Lives: " + getLives);
    }

}