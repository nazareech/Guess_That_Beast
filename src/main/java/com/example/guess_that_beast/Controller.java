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

    @FXML private Label points;

    @FXML
    private Stage stage; // Поточна сцена
    @FXML
    private Scene scene; // Поточна сцена
    private Parent root; // Корінь нової сцени

    @FXML
    private void chooseAlevel(ActionEvent event) {

        Button clickedButton = (Button) event.getSource(); // Отримуємо кнопку, яка викликала подію
        String buttonId = clickedButton.getId();

        if ("level1".equals(buttonId)) {

            levelChoice.setText("Level 1");
        }else if ("level2".equals(buttonId)) {
            levelChoice.setText("Level 2");
        }else if ("level3".equals(buttonId)) {
            levelChoice.setText("Level 3");
        }else if ("level4".equals(buttonId)) {
            levelChoice.setText("Level 4");
        }
        else if ("level5".equals(buttonId)) {
            levelChoice.setText("Level 5");
        }
        // Аналогічно для інших рівнів
        System.out.println("Вибрано рівень: " + buttonId);
        viewPoints();
    }

    //Перехід до рівня
    @FXML
    private void openLevel(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        String fxmlPath = "/com/example/guess_that_beast/view-level.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        List<Meme> memes = MemeLoader.loadMemes();
        LevelController levelController = loader.getController();
        levelController.initializeWithMemes(memes); // Тепер передаємо всі меми

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void viewPoints(){
        points.setText("Points: 5");
        System.out.println("Points: 5");
    }

}