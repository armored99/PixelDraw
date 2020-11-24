package com.project.pixeldraw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private PixelGame mGame;
    private PixelGrid mPixelsGrid;
    private TextView mStrokes;
    private TextView mPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStrokes = findViewById(R.id.strokes);
        mPixels = findViewById(R.id.pixel);
        mPixelsGrid = findViewById(R.id.gameGrid);
        mPixelsGrid.setGridListener(mGridListener);

        mGame = PixelGame.getInstance();
        newGame();
    }

    private PixelGrid.PixelsGridListener mGridListener = new PixelGrid.PixelsGridListener() {

        @Override
        public void onPixelSelected(Pixel Pixel, PixelGrid.PixelSelectionStatus status) {

            // Ignore selections when game is over
            //if (mGame.isGameOver()) return;

            // Add to list of selected Pixels
            mGame.addSelectedPixel(Pixel);

            // If done selecting Pixels then replace selected Pixels and display new moves and score
            if (status == PixelGrid.PixelSelectionStatus.Last) {
                if (mGame.getSelectedPixels().size() > 0) {
                    mPixelsGrid.animatePixels();

                    // These methods must be called AFTER the animation completes
                    //mGame.finishMove();
                    //updateMovesAndScore();
                } else {
                    mGame.clearSelectedPixels();
                }
            }

            // Display changes to the game
            mPixelsGrid.invalidate();
        }
        @Override
        public void onAnimationFinished() {
            mGame.finishMove();
            mPixelsGrid.invalidate();
            updateMovesAndScore();
        }
    };

    public void newGameClick(View view) {
        // Animate down off screen
        ObjectAnimator moveBoardOff = ObjectAnimator.ofFloat(mPixelsGrid,
                "translationY", mPixelsGrid.getHeight() * 1.5f);
        moveBoardOff.setDuration(700);
        moveBoardOff.start();

        moveBoardOff.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                newGame();

                // Animate from above the screen down to default location
                ObjectAnimator moveBoardOn = ObjectAnimator.ofFloat(mPixelsGrid,
                        "translationY", mPixelsGrid.getHeight() * -1.5f, 0);
                moveBoardOn.setDuration(700);
                moveBoardOn.start();
            }
        });
    }

    private void newGame() {
        mGame.newGame();
        mPixelsGrid.invalidate();
        updateMovesAndScore();
    }

    private void updateMovesAndScore() {
        mStrokes.setText(Integer.toString(mGame.getMovesLeft()));
        mPixels.setText(Integer.toString(mGame.getScore()));
    }
}