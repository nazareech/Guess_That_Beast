package com.example.guess_that_beast;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage root) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-main-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        root.setTitle("Brain rot Animals!");
        root.setScene(scene);
        root.show();
    }

    public static void main(String[] args) {
        launch();
    }
}