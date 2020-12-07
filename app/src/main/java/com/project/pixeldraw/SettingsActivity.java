package com.project.pixeldraw;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
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
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                // Display the fragment as the main content
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, new SettingsFragment())
                        .commit();
        }

/*        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

                // Inflate menu for the app bar
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.prior_menu, menu);
                return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

                // Determine which app bar item was chosen
                switch (item.getItemId()) {
                        case R.id.previous:

                        default:
                                return super.onOptionsItemSelected(item);
                }
        }
*/
}