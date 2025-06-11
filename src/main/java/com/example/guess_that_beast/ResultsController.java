package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultsController {
    @FXML private Label correctAnswersLabel;
    @FXML private Label totalTimeLabel;
    @FXML private Label scoreLabel;
    @FXML private Label newLevelLabel;

    @FXML private ImageView accurecyImage;
    @FXML private ImageView timeImage;
    @FXML private ImageView pointsImage;



    public void setResults(float correctAnswers, long seconds, int points, UnlockingLevelsManager unlockingLevelsManager, int currentLevel) {
        setIcons();

        float totalTime;
        if(seconds > 59){
            int currentTime = (int) Math.max(seconds, 3);
            System.out.println(currentTime);
            totalTime= currentTime/60;
        }else {
            totalTime = (int)seconds;
        }

        correctAnswersLabel.setText(String.valueOf(correctAnswers));
        totalTimeLabel.setText(String.format(totalTime + " seconds"));
        scoreLabel.setText(String.format(points + " points"));

        boolean isUnlockedLevel = unlockingLevelsManager.nextLevelIsUnlocked(correctAnswers, currentLevel);
        newLevelLabel.setVisible(isUnlockedLevel);
    }

    private void setIcons(){
        Image image;
        try {
            image = new Image(getClass().getResource("/img/Interface icons/Accuracy.png").toExternalForm());
            accurecyImage.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/Time.png").toExternalForm());
            timeImage.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/Point.png").toExternalForm());
            pointsImage.setImage(image);

        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views_fxml_files/view-main-scene.fxml"));
        Parent root = loader.load();

        //Підключаємо стилі
        root.getStylesheets().add(getClass().getResource("/Style/Main_menu_style.css").toExternalForm());

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Установлення позиції та розміру зі збереженого стану
        WindowStateManager stateManager = Main.getWindowStateManager();
        stage.setX(stateManager.getX());
        stage.setY(stateManager.getY());
        stage.setWidth(stateManager.getWidth());
        stage.setHeight(stateManager.getHeight());


        stage.setScene(new Scene(root));
        stage.show();
    }
}
