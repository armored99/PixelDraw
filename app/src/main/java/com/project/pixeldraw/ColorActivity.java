package com.project.pixeldraw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.prior_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.previous:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
       //handles selection of a color button
    public void ChangeColorSelection (View view){
        Button btn = findViewById(view.getId());
        String text = btn.getText().toString();
        if (text == getString(R.string.red)) {
            MainActivity.UpdateColor(0);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.red), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.black)) {
            MainActivity.UpdateColor(6);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.black), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.purple)) {
            MainActivity.UpdateColor(5);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.purple), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.white)) {
            MainActivity.UpdateColor(7);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.white), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.yellow)) {
            MainActivity.UpdateColor(3);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.yellow), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.orange)) {
            MainActivity.UpdateColor(4);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.orange), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.blue)) {
            MainActivity.UpdateColor(1);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.blue), Toast.LENGTH_SHORT).show();
        }
        if (text == getString(R.string.green)) {
            MainActivity.UpdateColor(2);
            Toast.makeText(this, getString(R.string.color_changed_to)+" "+getString(R.string.green), Toast.LENGTH_SHORT).show();
        }
    }

}