package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ShopController {
//    @FXML private Button backToMenuButton;

    @FXML
    private void backToMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/guess_that_beast/view-main-scene.fxml"));
        Parent root = loader.load();

        //Підключаємо стилі
        root.getStylesheets().add(getClass().getResource("/Main_menu_style.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();
    }
}
