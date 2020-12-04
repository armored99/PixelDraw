package com.project.pixeldraw;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String PREFERENCE_THEME = "pref_theme";
    public static String PREFERENCE_SUBJECT_ORDER = "pref_subject_order";
    public static String PREFERENCE_PIXEL_GRID_SIZE = "pref_pixel_grid_size";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        // Access the default shared prefs
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        setPrefSummarySubjectOrder(sharedPrefs);
        setPrefSummaryPixelGridSize(sharedPrefs);
    }

    // Set the summary to the currently selected subject order
    private void setPrefSummarySubjectOrder(SharedPreferences sharedPrefs) {
        String order = sharedPrefs.getString(PREFERENCE_SUBJECT_ORDER, "1");
        String[] subjectOrders = getResources().getStringArray(R.array.pref_subject_order);
        Preference subjectOrderPref = findPreference(PREFERENCE_SUBJECT_ORDER);
        subjectOrderPref.setSummary(subjectOrders[Integer.parseInt(order)]);
    }

    // Set the summary to the Pixel Grid Size
    private void setPrefSummaryPixelGridSize(SharedPreferences sharedPrefs) {
        String pixelGridSize = sharedPrefs.getString(PREFERENCE_PIXEL_GRID_SIZE, "0");
        Preference gridPref = findPreference(PREFERENCE_PIXEL_GRID_SIZE);
        int pgs = Integer.parseInt(pixelGridSize);
        if (pgs <= 0 || pgs > 1000) {
            //questionPref.setSummary(getResources().getString(R.string.pref_none));
        }
        else {
            gridPref.setSummary(pixelGridSize);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREFERENCE_THEME)) {
            // Recreate the activity so the theme takes effect
            getActivity().recreate();
        }
        else if (key.equals(PREFERENCE_SUBJECT_ORDER)) {
            setPrefSummarySubjectOrder(sharedPreferences);
        }
        else if (key.equals(PREFERENCE_PIXEL_GRID_SIZE)) {
            setPrefSummaryPixelGridSize(sharedPreferences);
        }
    }
}