package com.project.pixeldraw;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.ArrayList;

public class PixelGrid extends View {

    private PixelGame mGame;

    private final int Pixel_RADIUS = 40;

    public enum PixelSelectionStatus {First, Additional, Last}

    public interface PixelsGridListener {
        void onPixelSelected(Pixel Pixel, PixelSelectionStatus status);
        void onAnimationFinished();
    }

    private PixelsGridListener mGridListener;

    private int[] mPixelColors;
    private int mCellWidth;
    private int mCellHeight;
    private Paint mPixelPaint;
    private Paint mPathPaint;

    private AnimatorSet mAnimatorSet;

    // private AnimatorSet mAnimatorSet;

    public PixelGrid(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Used to access the game state
        mGame = PixelGame.getInstance();

        // Get color resources
        mPixelColors = getResources().getIntArray(R.array.pixelColors);

        // For drawing Pixels
        mPixelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // For drawing the path between selected Pixels
        mPathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStrokeWidth(10);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mAnimatorSet = new AnimatorSet();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        int boardWidth = (width - getPaddingLeft() - getPaddingRight());
        int boardHeight = (height - getPaddingTop() - getPaddingBottom());
        mCellWidth = boardWidth / PixelGame.GRID_SIZE;
        mCellHeight = boardHeight / PixelGame.GRID_SIZE;
        resetPixels();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw Pixels
        for (int row = 0; row < PixelGame.GRID_SIZE; row++) {
            for (int col = 0; col < PixelGame.GRID_SIZE; col++) {
                Pixel Pixel = mGame.getPixel(row, col);
                mPixelPaint.setColor(mPixelColors[Pixel.color]);
                canvas.drawRect(col * mCellHeight, row * mCellWidth, col * mCellHeight + mCellHeight, row * mCellWidth + mCellWidth, mPixelPaint);
            }
        }
        if (!mAnimatorSet.isRunning()) {
            // Draw connector
            ArrayList<Pixel> selectedPixels = mGame.getSelectedPixels();
            if (!selectedPixels.isEmpty()) {
                Path path = new Path();
                Pixel Pixel = selectedPixels.get(0);
                path.moveTo(Pixel.centerX, Pixel.centerY);

                for (int i = 1; i < selectedPixels.size(); i++) {
                    Pixel = selectedPixels.get(i);
                    path.lineTo(Pixel.centerX, Pixel.centerY);
                }

                mPathPaint.setColor(mPixelColors[7]);
                canvas.drawPath(path, mPathPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // Only execute when a listener exists
        if (mGridListener == null || mAnimatorSet.isRunning()) return true;

        // Determine which Pixel is pressed
        int x = (int) event.getX();
        int y = (int) event.getY();
        int col = x / mCellWidth;
        int row = y / mCellHeight;
        Pixel selectedPixel = mGame.getPixel(row, col);

        // Return previously selected Pixel if touch moves outside the grid
        if (selectedPixel == null) {
            selectedPixel = mGame.getLastSelectedPixel();
        }

        // Notify activity that a Pixel is selected
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mGridListener.onPixelSelected(selectedPixel, PixelSelectionStatus.First);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mGridListener.onPixelSelected(selectedPixel, PixelSelectionStatus.Additional);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mGridListener.onPixelSelected(selectedPixel, PixelSelectionStatus.Last);
        }

        return true;
    }

    public void setGridListener(PixelsGridListener gridListener) {
        mGridListener = gridListener;
    }

    private void resetPixels() {
        for (int row = 0; row < PixelGame.GRID_SIZE; row++) {
            for (int col = 0; col < PixelGame.GRID_SIZE; col++) {
                Pixel Pixel = mGame.getPixel(row, col);
                 Pixel.radius = Pixel_RADIUS;
                Pixel.centerX = col * mCellWidth + (mCellWidth / 2);
                Pixel.centerY = row * mCellHeight + (mCellHeight / 2);
            }
        }
        mGame = PixelGame.getInstance();
    }

    public void animatePixels() {

        // For storing many animations
        ArrayList<Animator> animations = new ArrayList<>();

        // Get an animation to make selected Pixels disappear
        animations.add(getDisappearingAnimator());

        // Play animations (just one right now) together, then reset radius to full size
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(animations);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                resetPixels();
                mGridListener.onAnimationFinished();
            }
        });
        mAnimatorSet.start();
    }

    private ValueAnimator getDisappearingAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setDuration(100);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                for (Pixel Pixel : mGame.getSelectedPixels()) {
                    Pixel.radius = Pixel_RADIUS * (float) animation.getAnimatedValue();
                }
                invalidate();
            }
        });
        return animator;
    }
}