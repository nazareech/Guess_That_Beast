package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Text levelChoice;

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
    }

    @FXML
    private void openLevel(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        // Формуємо шлях до FXML на основі ID кнопки
        String fxmlPath;
        if(buttonId.equals("main")){
            fxmlPath = "/com/example/guess_that_beast/view-" + buttonId + "-scene.fxml";
        }else {
            fxmlPath = "/com/example/guess_that_beast/view-level-" + buttonId + ".fxml";
        }
        // Завантажуємо нову сцену
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        // Отримуємо поточну сцену
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}