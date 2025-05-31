package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelController {
    @FXML private Button option1Button;
    @FXML private Button option2Button;
    @FXML private Button option3Button;
    @FXML private Button option4Button;
    @FXML private ImageView imageView;

    @FXML private Label lives;

    //Fail answer
    @FXML private VBox feedbackVBox;
    @FXML private Label feedbackLabel;
    @FXML private Label correctAnswerLabel;
    @FXML private Button nextButton;
    @FXML private Button exitButton;
    @FXML private Button goToMain;
    private int mistakes = 0;

    private List<Meme> allMemes;
    private List<Meme> currentLevelMemes = new ArrayList<>();
    private List<Meme> incorrectMemes = new ArrayList<>();
    private int currentMemeIndex = 0;
    private Meme currentMeme;
    private boolean wasLastAnswerCorrect;

    private long levelStartTime;
    private int currentLives;

    public void initializeWithMemes(List<Meme> memes, int startMemIndex, int endMemIndex) {
        this.allMemes = new ArrayList<>(memes.subList(startMemIndex, endMemIndex));
        feedbackVBox.setVisible(false); // Виключаємо вікно фітбеку
        exitButton.setVisible(false);   // Виключаємо кнопку виходу з рівня

        // Ініціалізація першого питання
        if (!allMemes.isEmpty()) {
            loadLevelQuestions();
            loadNextQuestion(); // Тепер цей метод встановлює currentMeme
        } else {
            System.err.println("No memes loaded!");
        }

        levelStartTime = System.currentTimeMillis();
        updateLivesDisplay(); // оновлення тексту на екрані

        // Фарбуємо кнопки
        paintTheButtons();
    }

    private void loadLevelQuestions() {
        int questionsCount = Math.min(5, allMemes.size());
        currentLevelMemes = new ArrayList<>(allMemes.subList(0, questionsCount));
        allMemes.removeAll(currentLevelMemes);
        currentMemeIndex = 0;
    }

    private void loadNextQuestion() {
        if (currentMemeIndex < currentLevelMemes.size()) {
            currentMeme = currentLevelMemes.get(currentMemeIndex); // Ініціалізуємо поточний мем
            displayMeme(currentMeme);
        } else if (!incorrectMemes.isEmpty()) {
            // Перехід до повторення неправильних відповідей
            currentLevelMemes = new ArrayList<>(incorrectMemes);
            incorrectMemes.clear();
            currentMemeIndex = 0;
            currentMeme = currentLevelMemes.get(currentMemeIndex);
            displayMeme(currentMeme);
        } else {
            showLevelResults();
        }
    }

    private void displayMeme(Meme meme) {
        try {
            Image image = new Image(getClass().getResource(meme.getImage()).toExternalForm());
            imageView.setImage(image);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        // Закруглення кутів зображення
        roundOffImageCorners(30);

        // Перемішуємо варіанти відповідей
        List<String> options = new ArrayList<>(meme.getOptions());
        Collections.shuffle(options);

        option1Button.setText(options.get(0));
        option2Button.setText(options.get(1));
        option3Button.setText(options.get(2));
        option4Button.setText(options.get(3));

        // Ховаємо попередній фідбек та виключаємо кнопки
        hideFeedback();
        enableAnswerButtons(true);
    }

    @FXML
    private void handleAnswer(ActionEvent event) {
        if (currentMeme == null) {
            System.err.println("currentMeme is null!");
            return;
        }

        Button clickedButton = (Button) event.getSource();
        String selectedAnswer = clickedButton.getText();

        wasLastAnswerCorrect = selectedAnswer.equals(currentMeme.getCorrectAnswer());

        showFeedback(wasLastAnswerCorrect, currentMeme.getCorrectAnswer());
        enableAnswerButtons(false);
    }

    @FXML
    private void handleNextButton(ActionEvent event) throws IOException {

        if (!wasLastAnswerCorrect) {
            incorrectMemes.add(currentMeme);
        }
        currentMemeIndex++;
        loadNextQuestion();
    }

    private void showFeedback(boolean isCorrect, String correctAnswer) {

        if (isCorrect) {
            feedbackLabel.setText("Great!");
            feedbackLabel.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            correctAnswerLabel.setStyle("-fx-text-fill: white;");
            correctAnswerLabel.setText("");
            nextButton.setText("Next");
            nextButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        } else {
            mistakes++;

            feedbackLabel.setText("Your answer is incorrect!");
            feedbackLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            correctAnswerLabel.setText("The correct answer is: " + correctAnswer);
            correctAnswerLabel.setStyle("-fx-text-fill: green;");
            nextButton.setText("Next");
            nextButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");

            GameState.getInstance().loseLife(); // зменшення життів
            updateLivesDisplay();               // оновлення Життів на екрані
        }

        if (GameState.getInstance().getLives() > 0) {
            feedbackVBox.setVisible(true);
            exitButton.setVisible(false);
        }else if (GameState.getInstance().getLives() == 0) {
            coverageOfEndOfLife();
        }
    }

    private void confirmationOfMenuReturning () {
        feedbackVBox.setVisible(true);
        feedbackLabel.setText("Do you really want to go back to the menu?");
        feedbackLabel.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        correctAnswerLabel.setStyle("-fx-background-color: blue; -fx-text-fill: red;");
        correctAnswerLabel.setText("You will lose your points ");

        nextButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        nextButton.setText("Continue");
        exitButton.setVisible(true); // Включаємо кнопку виходу з рівня
        exitButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }

    private void coverageOfEndOfLife () {
        feedbackVBox.setVisible(true);
        feedbackLabel.setText("Unfortunately, you have lost all your lives");
        feedbackLabel.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        correctAnswerLabel.setStyle("-fx-background-color: blue; -fx-text-fill: red;");
        correctAnswerLabel.setText("You will lose your points");

        goToMain.setDisable(true);
        nextButton.setVisible(false);
        exitButton.setVisible(true); // Включаємо кнопку виходу з рівня
        exitButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
    }

    private void hideFeedback() {
        feedbackVBox.setVisible(false);
    }

    private void enableAnswerButtons(boolean enabled) {
        option1Button.setDisable(!enabled);
        option2Button.setDisable(!enabled);
        option3Button.setDisable(!enabled);
        option4Button.setDisable(!enabled);
    }

    private void showLevelResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/guess_that_beast/view-results.fxml"));
            Parent root = loader.load();

            long duration = System.currentTimeMillis() - levelStartTime;
            long seconds = duration / 1000;
            System.out.println("Рівень пройдено за " + seconds + " секунд");

            float correctAnswers;
            if((5 - mistakes) <= 0){
                correctAnswers = 0;
            }else {
                correctAnswers = 100 - ((mistakes * 100) / 5);
            }
            ResultsController controller = loader.getController();
            controller.setResults(correctAnswers, 100, seconds); // 5 - загальна кількість завдань

            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToMenuButton(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();
        String clickedButtonId = clickedButton.getId();
        if (clickedButtonId.equals("goToMain")) {
            confirmationOfMenuReturning();
        }
    }

    // Вихід до меню
    @FXML
    private void exitToMenu (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/guess_that_beast/view-main-scene.fxml"));
        Parent root = loader.load();

        //Підключаємо стилі
        root.getStylesheets().add(getClass().getResource("/Main_menu_style.css").toExternalForm());


        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void updateLivesDisplay(){
        currentLives = GameState.getInstance().getLives();
        lives.setText("Lives: " + currentLives);
        System.out.println("Lives: " + currentLives);
    }

    private void paintTheButtons(){
        option1Button.setStyle("-fx-background-color: #89A8AE; -fx-text-fill: #F5F5F3;");
        option2Button.setStyle("-fx-background-color: #89A8AE; -fx-text-fill: #F5F5F3;");
        option3Button.setStyle("-fx-background-color: #89A8AE; -fx-text-fill: #F5F5F3;");
        option4Button.setStyle("-fx-background-color: #89A8AE; -fx-text-fill: #F5F5F3;");
    }

    private void roundOffImageCorners (int radius){
        Rectangle rectangle = new Rectangle(0, 0, 300, 300); // координаты x, y, ширина, высота
        rectangle.setArcWidth(radius);
        rectangle.setArcHeight(radius);
        imageView.setClip(rectangle);
    }
}
