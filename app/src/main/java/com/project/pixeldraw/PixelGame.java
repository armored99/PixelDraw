package com.project.pixeldraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PixelGame {

    public static int NUM_COLORS = 5;
    public static int GRID_SIZE = 10;

    public enum AddPixelStatus { Added, Rejected, Removed };

    private static PixelGame mPixelsGame;

    private int mStrokes;
    private int mScore;
    private Pixel[][] mPixels;
    private ArrayList<Pixel> mSelectedPixels;

    private PixelGame() {

        mScore = 0;

        // Create Pixels for the 2d array
        mPixels = new Pixel[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mPixels[row][col] = new Pixel(row, col);
            }
        }

        mSelectedPixels = new ArrayList();
    }

    public static PixelGame getInstance() {
        if (mPixelsGame == null) {
            mPixelsGame = new PixelGame();
        }
        return mPixelsGame;
    }

    public int getMovesLeft() {
        return mStrokes;
    }

    public int getScore() {
        return mScore;
    }

    public Pixel getPixel(int row, int col) {
        if (row >= GRID_SIZE || row < 0 || col >= GRID_SIZE || col < 0) {
            return null;
        } else {
            return mPixels[row][col];
        }
    }

    // Return list of selected Pixels
    public ArrayList<Pixel> getSelectedPixels() {
        return mSelectedPixels;
    }

    // Return the last selected Pixel
    public Pixel getLastSelectedPixel() {
        if (mSelectedPixels.size() > 0) {
            return mSelectedPixels.get(mSelectedPixels.size() - 1);
        } else {
            return null;
        }
    }

    // Return the lowest selected Pixel in each column
    public ArrayList<Pixel> getLowestSelectedPixels() {

        ArrayList<Pixel> Pixels = new ArrayList<>();
        for (int col = 0; col < GRID_SIZE; col++) {
            for (int row = GRID_SIZE - 1; row >= 0; row--) {
                if (mPixels[row][col].selected) {
                    Pixels.add(mPixels[row][col]);
                    break;
                }
            }
        }

        return Pixels;
    }

    // Clear the list of selected Pixels
    public void clearSelectedPixels() {

        // Reset board so none selected
        for (Pixel Pixel : mSelectedPixels) {
            Pixel.selected = false;
        }

        mSelectedPixels.clear();
    }

    // Attempt to add the Pixel to the list of selected Pixels
    public AddPixelStatus addSelectedPixel(Pixel Pixel) {
        AddPixelStatus status = AddPixelStatus.Rejected;

        // Check if first Pixel selected
        if (mSelectedPixels.size() == 0) {
            mSelectedPixels.add(Pixel);
            Pixel.selected = true;
            status = AddPixelStatus.Added;
        }
        else {
            if (!Pixel.selected) {
                // Make sure new is same color and adjacent to last selected Pixel
                Pixel lastPixel = getLastSelectedPixel();
                if (lastPixel.color == Pixel.color && lastPixel.isAdjacent(Pixel)) {
                    mSelectedPixels.add(Pixel);
                    Pixel.selected = true;
                    status = AddPixelStatus.Added;
                }
            }
            else if (mSelectedPixels.size() > 1) {
                // Pixel is already selected, so remove last Pixel if backtracking
                Pixel secondLast = mSelectedPixels.get(mSelectedPixels.size() - 2);
                if (secondLast.equals(Pixel)) {
                    Pixel removedPixel = mSelectedPixels.remove(mSelectedPixels.size() - 1);
                    removedPixel.selected = false;
                    status = AddPixelStatus.Removed;
                }
            }
        }

        return status;
    }

    // Sort by rows ascending
    private void sortSelectedPixels() {
        Collections.sort(mSelectedPixels, new Comparator<Pixel>() {
            public int compare(Pixel Pixel1, Pixel Pixel2) {
                return Pixel1.row - Pixel2.row;
            }
        });
    }

    // Call after completing a Pixel path to relocate the Pixels and update the score and moves
    public void finishMove() {
        if (mSelectedPixels.size() > 0) {
            // Sort by row so Pixels are processed top-down
            sortSelectedPixels();

            // Move all Pixels above each selected Pixel down by changing color
            for (Pixel Pixel : mSelectedPixels) {
                for (int row = Pixel.row; row > 0; row--) {
                    //Pixel PixelCurrent = mPixels[row][Pixel.col];
                    //Pixel PixelAbove = mPixels[row - 1][Pixel.col];
                    //PixelCurrent.color = PixelAbove.color;
                }
                Pixel PixelCurrent = mPixels[Pixel.row][Pixel.col];
                PixelCurrent.setRandomColor();
                // Add new Pixel at top
                //Pixel topPixel = mPixels[0][Pixel.col];
                //topPixel.setRandomColor();
            }

            mScore += mSelectedPixels.size();
            mStrokes++;

            clearSelectedPixels();
        }
    }

    // Start a new game
    public void newGame() {
        mScore = 0;
        mStrokes = 0;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mPixels[row][col].setColor();
            }
        }
    }

    /* Determine if the game is over
    public boolean isGameOver() {
        return mStrokes == 0;
    }*/
}