package com.example.guess_that_beast;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    private static final WindowStateManager windowStateManager = new WindowStateManager();

    @Override
    public void start(Stage root) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("view-main-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        //Підключаємо стилі
        scene.getStylesheets().add(getClass().getResource("/Style/Main_menu_style.css").toExternalForm());
        root.setTitle("Brain rot Animals!");

        try {
            URL imageUrl = Main.class.getResource("/img/Interface icons/Icon.png");
            if (imageUrl != null) {
                Image icon = new Image(imageUrl.toExternalForm());
                root.getIcons().add(icon);
            } else {
                System.err.println("Icon resource not found: " + imageUrl);
            }
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + e.getMessage());
        }


        root.setWidth(1200);
        root.setHeight(900);

        // Встановлення мінімального розміру
        root.setMinWidth(1100); // Мінімальна ширина
        root.setMinHeight(800); // Мінімальна висота


        // Збереження стану при зміні розміру/позиції
        root.xProperty().addListener((obs, oldVal, newVal) -> windowStateManager.setX(newVal.doubleValue()));
        root.yProperty().addListener((obs, oldVal, newVal) -> windowStateManager.setY(newVal.doubleValue()));
        root.widthProperty().addListener((obs, oldVal, newVal) -> windowStateManager.setWidth(newVal.doubleValue()));
        root.heightProperty().addListener((obs, oldVal, newVal) -> windowStateManager.setHeight(newVal.doubleValue()));

        root.setScene(scene);
        root.show();

    }

    public static WindowStateManager getWindowStateManager() {
        return windowStateManager;
    }

    public static void main(String[] args) {
        launch();
    }

}



/*
*   Додати логіку ПОІНТІВ
*за які можна буде поновити життя
*
*   Додати магазин
*
*   Неможливість підійти до рівня коли перший не розблоковано
*
*   Неможливо обрати рівень коли нема сердець
 */