package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LevelController {
    @FXML private Button option1Button;
    @FXML private Button option2Button;
    @FXML private Button option3Button;
    @FXML private Button option4Button;
    @FXML private ImageView imageView;

    private List<Meme> allMemes;
    private List<Meme> currentLevelMemes = new ArrayList<>();
    private List<Meme> incorrectMemes = new ArrayList<>();
    private int currentMemeIndex = 0;
    private int correctAnswers = 0;

    public void initializeWithMemes(List<Meme> memes) {
        this.allMemes = new ArrayList<>(memes);
        loadNextQuestion();
    }

    private void loadNextQuestion() {
        if (currentLevelMemes.isEmpty() && incorrectMemes.isEmpty() && !allMemes.isEmpty()) {
            // Початок рівня - беремо перші 5 мемів
            int questionsCount = Math.min(5, allMemes.size());
            currentLevelMemes = new ArrayList<>(allMemes.subList(0, questionsCount));
            allMemes.removeAll(currentLevelMemes);
            currentMemeIndex = 0;
        } else if (currentMemeIndex >= currentLevelMemes.size() && !incorrectMemes.isEmpty()) {
            // Повторення неправильних відповідей
            currentLevelMemes = new ArrayList<>(incorrectMemes);
            incorrectMemes.clear();
            currentMemeIndex = 0;
        }

        if (currentMemeIndex < currentLevelMemes.size()) {
            Meme currentMeme = currentLevelMemes.get(currentMemeIndex);
            displayMeme(currentMeme);
        } else {
            // Рівень завершено
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

        List<String> options = meme.getOptions();
        if (options.size() >= 4) {
            option1Button.setText(options.get(0));
            option2Button.setText(options.get(1));
            option3Button.setText(options.get(2));
            option4Button.setText(options.get(3));
        }
    }

    @FXML
    private void handleAnswer(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String selectedAnswer = clickedButton.getText();
        Meme currentMeme = currentLevelMemes.get(currentMemeIndex);

        if (selectedAnswer.equals(currentMeme.getCorrectAnswer())) {
            correctAnswers++;
        } else {
            incorrectMemes.add(currentMeme); // Додаємо до списку для повторення
        }

        currentMemeIndex++;
        loadNextQuestion();
    }

    private void showLevelResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/guess_that_beast/view-results.fxml"));
            Parent root = loader.load();

            ResultsController controller = loader.getController();
            controller.setResults(correctAnswers, 5); // 5 - загальна кількість завдань

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
