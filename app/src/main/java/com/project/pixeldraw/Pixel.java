package com.project.pixeldraw;

import java.util.Random;

public class Pixel {
    public int color;
    public int row;
    public int col;
    public float centerX;
    public float centerY;
    public float radius;
    public boolean selected;

    private Random randomGen;

    public Pixel(int row, int col) {
        randomGen = new Random();
        setColor();
        selected = false;
        radius = 1;
        this.row = row;
        this.col = col;
    }

    public void setRandomColor() {
        color = 5;
        //color = randomGen.nextInt(DotsGame.NUM_COLORS);
    }
    public void setColor() {
        color = 6;
    }

    public boolean isAdjacent(Pixel pixel) {
        int colDiff = Math.abs(col - pixel.col);
        int rowDiff = Math.abs(row -pixel.row);
        return colDiff + rowDiff == 1;
    }
}