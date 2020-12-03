package com.project.pixeldraw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    public PixelGame mGame;
    private PixelGrid mPixelsGrid;
    private TextView mStrokes;
    private TextView mPixels;
    private boolean mDarkTheme;
    public static SharedPreferences mSharedPrefs;
    private int GRID_SIZE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        mDarkTheme = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (mDarkTheme) {
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStrokes = findViewById(R.id.strokes);
        mPixels = findViewById(R.id.pixel);
        mPixelsGrid = findViewById(R.id.gameGrid);
        mPixelsGrid.setGridListener(mGridListener);
        GRID_SIZE = Integer.parseInt(mSharedPrefs.getString(SettingsFragment.PREFERENCE_PIXEL_GRID_SIZE, "20"));
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
    @Override
    protected void onResume() {
        super.onResume();
        // If theme changed, recreate the activity so theme is applied
        boolean darkTheme = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (darkTheme != mDarkTheme) {
            recreate();
        }
        int mGRID_SIZE = Integer.parseInt(mSharedPrefs.getString(SettingsFragment.PREFERENCE_PIXEL_GRID_SIZE, "20"));
        if (mGRID_SIZE != GRID_SIZE) {
            GRID_SIZE = Integer.parseInt(mSharedPrefs.getString(SettingsFragment.PREFERENCE_PIXEL_GRID_SIZE, "20"));
            mGame.changeSize(GRID_SIZE);
            recreate();
        }

    }

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}