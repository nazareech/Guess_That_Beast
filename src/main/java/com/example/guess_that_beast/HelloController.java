package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class HelloController {
    @FXML
    protected void onHelloButtonClick() {}
    @FXML
    private Text levelChoice;

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
            levelChoice.setText("Level5");
        }
        // Аналогічно для інших рівнів
        System.out.println("Вибрано рівень: " + buttonId);
    }
}