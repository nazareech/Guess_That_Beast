package com.example.guess_that_beast;

import java.util.List;

public class Meme {
    private String name;
    private String imageUrl;
    private List<String> options;
    private String correctAnswer;


    public Meme() {}

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
