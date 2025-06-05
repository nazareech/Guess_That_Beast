package com.example.guess_that_beast;

public class WindowStateManager {
    private double x = 0; // Збережене положення по X
    private double y = 0; // Збережене положення по Y
    private double width = 800; // Ширина за замовчуванням
    private double height = 600; // Висота за замовчуванням

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
