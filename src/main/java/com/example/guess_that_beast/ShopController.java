package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Button;

public class ShopController {
    private GameStateManager gameStateManager = new GameStateManager();
    private LivesManager livesManager = new LivesManager(gameStateManager);
    private ScoreManager scoreManager = new ScoreManager(gameStateManager);

    @FXML private Label livesLabel;
    @FXML private Label pointsLabel;
    @FXML private Label informationLabel;

    @FXML private void initialize(){
        updateLivesDisplay();
        informationLabel.setVisible(false);
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/guess_that_beast/view-main-scene.fxml"));
        Parent root = loader.load();

        //Підключаємо стилі
        root.getStylesheets().add(getClass().getResource("/Style/Main_menu_style.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void byLifes(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "oneLife":
                purchaseProcessing(1, 300);
                updateLivesDisplay();
                break;
            case "threeLifes":
                purchaseProcessing(3, 700);
                updateLivesDisplay();
                break;
            case "fiveLifes":
                purchaseProcessing(5, 1000);
                updateLivesDisplay();
                break;
        }
    }

    private void purchaseProcessing(int boughtLives, int price){
        informationLabel.setVisible(true);

        if(livesManager.getCurrentLives() != 5){
            if (scoreManager.getCurrentScore() >= price) {

                scoreManager.deductPoints(price);
                livesManager.addLifes(boughtLives);

                informationLabel.setText("You have purchased "+ boughtLives +" lives");
            }else {
                System.out.println("Недостатньо балів:(");
                informationLabel.setText("You don't have enough points :(");
            }
        }else {
            System.out.println("І так повно життів!");
            informationLabel.setText("You are already full of lives!");
        }
    }

    @FXML
    private void byPoints(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "Points500":
                break;
            case "Points1000":
                break;
            case "Points2500":
                break;
        }

    }


    private void updateLivesDisplay() {
        int currentLives = livesManager.getCurrentLives();
        int currenttPoints = scoreManager.getCurrentScore();

        livesLabel.setText(String.valueOf(currentLives));
        System.out.println("Lives: " + currentLives);
        pointsLabel.setText(String.valueOf(currenttPoints));
        System.out.println("Points: " + currenttPoints);
    }
}