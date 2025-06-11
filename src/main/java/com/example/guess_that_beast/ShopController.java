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
import javafx.scene.control.Button;

public class ShopController {
    private GameStateManager gameStateManager = new GameStateManager();
    private LivesManager livesManager = new LivesManager(gameStateManager);
    private ScoreManager scoreManager = new ScoreManager(gameStateManager);

    @FXML private Label livesLabel;
    @FXML private Label pointsLabel;
    @FXML private Label informationLabel;

    @FXML private ImageView PointsImage1;
    @FXML private ImageView PointsImage2;
    @FXML private ImageView PointsImage3;

    @FXML private ImageView oneLifeImage;
    @FXML private ImageView threeLifesImage;
    @FXML private ImageView fiveLifesImage;

    @FXML private ImageView backToMenuButtonImage;
    @FXML private ImageView shopImage;

    @FXML private ImageView livesLabelImg;
    @FXML private ImageView pointsLabelImage;

    @FXML private void initialize(){
        updateLivesDisplay();
        informationLabel.setVisible(false);

        setIcons();
    }

    private void setIcons(){
        Image image;
        try {
            image = new Image(getClass().getResource("/img/Interface icons/FewPoint.png").toExternalForm());
            PointsImage1.setImage(image);
            PointsImage2.setImage(image);
            PointsImage3.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/Live.png").toExternalForm());
            oneLifeImage.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/ThreeLives.png").toExternalForm());
            threeLifesImage.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/FiveLives.png").toExternalForm());
            fiveLifesImage.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/Back.png").toExternalForm());
            backToMenuButtonImage.setImage(image);

            image = new Image(getClass().getResource("/img/Interface icons/Shop.png").toExternalForm());
            shopImage.setImage(image);


        } catch (NullPointerException | IllegalArgumentException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views_fxml_files/view-main-scene.fxml"));
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

    private void updateLivesDisplay() {
        int currentLives = livesManager.getCurrentLives();
        int currenttPoints = scoreManager.getCurrentScore();

        Image image;
        if (currentLives == 0) {
            try {
                image = new Image(getClass().getResource("/img/Interface icons/FewLive.png").toExternalForm());
                livesLabelImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }else{
            try {
                image = new Image(getClass().getResource("/img/Interface icons/Live.png").toExternalForm());
                livesLabelImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        if (currenttPoints == 0) {
            try {
                image = new Image(getClass().getResource("/img/Interface icons/FewPoint.png").toExternalForm());
                pointsLabelImage.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }else{
            try {
                image = new Image(getClass().getResource("/img/Interface icons/Point.png").toExternalForm());
                pointsLabelImage.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        livesLabel.setText(String.valueOf(currentLives));
        System.out.println("Lives: " + currentLives);
        pointsLabel.setText(String.valueOf(currenttPoints));
        System.out.println("Points: " + currenttPoints);
    }


    @FXML
    private void byPoints(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "Points500":
                openDonationPage("https://donatello.to/nazareech_");
                break;
            case "Points1000":
                openDonationPage("https://donatello.to/nazareech_");
                break;
            case "Points2500":
                openDonationPage("https://donatello.to/nazareech_");
                break;
        }

    }

    // Метод відкриття URL у браузері
    private void openDonationPage(String url) {
        try {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                java.net.URI uri = new java.net.URI(url);
                desktop.browse(uri);
            }
        } catch (Exception e) {
            System.err.println("Не вдалося відкрити URL: " + e.getMessage());
            informationLabel.setText("Could not open the link. Please check your internet connection.");
            informationLabel.setVisible(true);
        }
    }

}
