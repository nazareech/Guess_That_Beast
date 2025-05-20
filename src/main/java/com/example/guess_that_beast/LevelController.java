package com.example.guess_that_beast;

import javafx.animation.PauseTransition;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LevelController {
    @FXML private Button option1Button;
    @FXML private Button option2Button;
    @FXML private Button option3Button;
    @FXML private Button option4Button;
    @FXML private ImageView imageView;

    //Fail answer
    @FXML private VBox feedbackVBox;
    @FXML private Label feedbackLabel;
    @FXML private Label correctAnswerLabel;
    @FXML private Button nextButton;
    private int mistakes = 0;

    private List<Meme> allMemes;
    private List<Meme> currentLevelMemes = new ArrayList<>();
    private List<Meme> incorrectMemes = new ArrayList<>();
    private int currentMemeIndex = 0;
    private Meme currentMeme;
    private boolean wasLastAnswerCorrect;

    public void initializeWithMemes(List<Meme> memes) {
        this.allMemes = new ArrayList<>(memes);
        feedbackVBox.setVisible(false);

        // Ініціалізація першого питання
        if (!allMemes.isEmpty()) {
            loadLevelQuestions();
            loadNextQuestion(); // Тепер цей метод встановлює currentMeme
        } else {
            System.err.println("No memes loaded!");
        }
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
    private void handleNextButton() {
        if (!wasLastAnswerCorrect) {
            incorrectMemes.add(currentMeme);
        }
        currentMemeIndex++;
        loadNextQuestion();
    }

    private void showFeedback(boolean isCorrect, String correctAnswer) {
        feedbackVBox.setVisible(true);
        if (isCorrect) {
            feedbackLabel.setText("Great!");
            feedbackLabel.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            correctAnswerLabel.setText("");
        } else {
            feedbackLabel.setText("Your answer is incorrect!");
            feedbackLabel.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            correctAnswerLabel.setText("The correct answer is: " + correctAnswer);
            correctAnswerLabel.setStyle("-fx-text-fill: green;");

            mistakes++;
        }
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

            float correctAnswers;
            if((5 - mistakes) <= 0){
                correctAnswers = 0;
            }else {
                correctAnswers = 100 - ((mistakes * 100) / 5);
            }
            ResultsController controller = loader.getController();
            controller.setResults(correctAnswers, 100); // 5 - загальна кількість завдань

            Stage stage = (Stage) imageView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/guess_that_beast/view-main-scene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
