package com.project.pixeldraw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class ColorActivity extends AppCompatActivity {
    private boolean mDarkTheme;
    private SharedPreferences mSharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mDarkTheme = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (mDarkTheme) {
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void ChangeColorSelection (View view){
        Button btn = (Button) findViewById(view.getId());
        String text = btn.getText().toString();
        if (text == getString(R.string.red)) {
            MainActivity.UpdateColor(0);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.red), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.black)) {
            MainActivity.UpdateColor(6);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.black), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.purple)) {
            MainActivity.UpdateColor(5);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.purple), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.white)) {
            MainActivity.UpdateColor(7);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.white), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.yellow)) {
            MainActivity.UpdateColor(3);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.yellow), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.orange)) {
            MainActivity.UpdateColor(4);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.orange), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.blue)) {
            MainActivity.UpdateColor(1);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.blue), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.green)) {
            MainActivity.UpdateColor(2);
            Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.green), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, getString(R.string.color_changed_to)+getString(R.string.black), Toast.LENGTH_SHORT).show();
    }

}