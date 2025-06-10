package com.example.guess_that_beast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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
    @FXML private ImageView livesImg;
    @FXML private ProgressBar progressIndificator;

    //Fail answer
    @FXML private GridPane feedbackGrigPlane;
    @FXML private Label feedbackLabel;
    @FXML private Label correctAnswerLabel;
    @FXML private Button nextButton;
    @FXML private Button exitButton;
    @FXML private Button goToMain;
    private int mistakes = 0;

    private List<Meme> allMemes;
    private List<Meme> currentLevelMemes = new ArrayList<>();
    private int numberOfCurrentLevelMemsList;
    private List<Meme> incorrectMemes = new ArrayList<>();
    private int currentMemeIndex = 0;
    private Meme currentMeme;
    private boolean wasLastAnswerCorrect;

    private GameStateManager gameStateManager = new GameStateManager();
    private LivesManager livesManager = new LivesManager(gameStateManager);
    private ScoreManager scoreManager = new ScoreManager(gameStateManager);
    private UnlockingLevelsManager unlockingLevelsManager = new UnlockingLevelsManager(gameStateManager);

    private long levelStartTime;
    private int currentLives;

    private int currentLevel;

    public void initializeWithMemes(List<Meme> memes, int startMemIndex, int endMemIndex, int currentLevel) {
        this.allMemes = new ArrayList<>(memes.subList(startMemIndex, endMemIndex));
        feedbackGrigPlane.setVisible(false); // Виключаємо вікно фітбеку
        exitButton.setVisible(false);   // Виключаємо кнопку виходу з рівня

        // Ініціалізація першого питання
        if (!allMemes.isEmpty()) {
            loadLevelQuestions();
            loadNextQuestion(); // Тепер цей метод установлює currentMeme
        } else {
            System.err.println("No memes loaded!");
        }

        levelStartTime = System.currentTimeMillis();
        updateLivesDisplay(); // оновлення тексту на екрані

        this.currentLevel = currentLevel;
    }

    private void loadLevelQuestions() {
        int questionsCount = Math.min(5, allMemes.size());
        currentLevelMemes = new ArrayList<>(allMemes.subList(0, questionsCount));
        allMemes.removeAll(currentLevelMemes);
        numberOfCurrentLevelMemsList = currentLevelMemes.size();
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
        roundOffImageCorners(30, (int)imageView.getFitWidth());

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
            correctAnswerLabel.setText("");
            nextButton.setText("Continue");

            progress();

        } else {
            mistakes++;
            feedbackLabel.setText("Incorrect answer!");
            correctAnswerLabel.setText("The correct answer was: " + correctAnswer);
            nextButton.setText("Continue");

            livesManager.loseLife(); // зменшення життів
            updateLivesDisplay();   // оновлення Життів на екрані та запис
        }

        if (livesManager.getCurrentLives() > 0) {
            feedbackGrigPlane.setVisible(true);
            exitButton.setVisible(false);
        }else if (livesManager.getCurrentLives() == 0) {
            coverageOfEndOfLife();
        }
    }

    private void confirmationOfMenuReturning () {
        feedbackGrigPlane.setVisible(true);
        feedbackLabel.setText("Do you really want to go back to the menu?");
        correctAnswerLabel.setText("You will lose your points ");

        nextButton.setText("Continue");
        exitButton.setVisible(true); // Включаємо кнопку виходу з рівня
    }

    private void coverageOfEndOfLife () {
        feedbackGrigPlane.setVisible(true);
        feedbackLabel.setText("Unfortunately, you have lost all your lives");
        correctAnswerLabel.setText("");

        goToMain.setDisable(true);
        nextButton.setVisible(false);
        exitButton.setVisible(true); // Включаємо кнопку виходу з рівня
    }

    private void hideFeedback() {
        feedbackGrigPlane.setVisible(false);
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

            // Обчислюємо час пройденя рівню
            long duration = System.currentTimeMillis() - levelStartTime;
            int seconds = (int)duration / 1000;
            System.out.println("Рівень пройдено за " + seconds + " секунд");

            // Обчислюємо правильні відповіді
            float correctAnswers;
            if((5 - mistakes) <= 0){correctAnswers = 0;
            }else{correctAnswers = 100 - ((mistakes * 100) / 5);}

            // Обчислюємо бали за рівень
            int points = scoreManager.calculatePoints(mistakes, numberOfCurrentLevelMemsList, seconds);

            System.out.println("Всього завдань: " + currentLevelMemes.size());

            ResultsController controller = loader.getController();
            controller.setResults(correctAnswers, seconds, points, unlockingLevelsManager, currentLevel); // 5 - загальна кількість завдань

            //Підключаємо стилі
            root.getStylesheets().add(getClass().getResource("/Style/Results_style.css").toExternalForm());


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
        root.getStylesheets().add(getClass().getResource("/Style/Main_menu_style.css").toExternalForm());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Встановлення позиції та розміру зі збереженого стану
        WindowStateManager stateManager = Main.getWindowStateManager();
        stage.setX(stateManager.getX());
        stage.setY(stateManager.getY());
        stage.setWidth(stateManager.getWidth());
        stage.setHeight(stateManager.getHeight());

        stage.setScene(new Scene(root));
        stage.show();
    }

    private void updateLivesDisplay(){
        currentLives = livesManager.getCurrentLives();

        Image image;
        if (currentLives == 0) {
            try {
                image = new Image(getClass().getResource("/img/Interface icons/FewLive.png").toExternalForm());
                livesImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }else{
            try {
                image = new Image(getClass().getResource("/img/Interface icons/Live.png").toExternalForm());
                livesImg.setImage(image);
            } catch (NullPointerException | IllegalArgumentException e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }

        lives.setText("Lives: " + currentLives);
        System.out.println("Lives: " + currentLives);
    }


    private void roundOffImageCorners (int radius, int imgSize){
        Rectangle rectangle = new Rectangle(0, 0, imgSize, imgSize); // координаты x, y, ширина, высота
        rectangle.setArcWidth(radius);
        rectangle.setArcHeight(radius);
        imageView.setClip(rectangle);
    }

    private void progress(){
        double progresSet = (100 / currentLevelMemes.size());

        System.out.println("Розмір масиву з мемами: " + currentLevelMemes.size());

        double currentProgress = progressIndificator.getProgress();
        System.out.println("Прогрес за один рівень становить: " + progresSet);

        if (currentProgress <= 0){
            progressIndificator.setProgress(progresSet / 100);
        }else {
            progressIndificator.setProgress(currentProgress + progresSet / 100);
        }
    }
}
