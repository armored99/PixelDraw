package com.project.pixeldraw;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity{
private boolean mDarkTheme;
private SharedPreferences mSharedPrefs;
//private EditText PixelSize;
@Override
protected void onCreate(Bundle savedInstanceState) {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        mDarkTheme = mSharedPrefs.getBoolean(SettingsFragment.PREFERENCE_THEME, false);
        if (mDarkTheme) {
            setTheme(R.style.DarkTheme);
        }
        super.onCreate(savedInstanceState);
        //stop popup keyboard from censoring
        //PixelSize = (EditText) rootView.findViewById(R.id.PGS);
        //PixelSize.setTransformationMethod(null);

        // Display the fragment as the main content
        getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new SettingsFragment())
        .commit();
        }

}