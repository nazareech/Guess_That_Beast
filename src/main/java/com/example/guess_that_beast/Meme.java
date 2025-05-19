package com.example.guess_that_beast;

import java.util.List;

public class Meme {
    private List<Meme> level;
    private String name;
    private String image;
    private List<String> options;
    private String correctAnswer;


    public Meme() {}


    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
