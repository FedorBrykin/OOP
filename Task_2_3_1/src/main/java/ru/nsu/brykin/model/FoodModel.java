package ru.nsu.brykin.model;

import java.util.Random;

public class FoodModel {

    public int x, y;

    public FoodModel(int maxX, int maxY) {
        spawn(maxX, maxY);
    }

    public void spawn(int maxX, int maxY) {
        Random random = new Random();
        x = random.nextInt(maxX);
        y = random.nextInt(maxY);
    }
}