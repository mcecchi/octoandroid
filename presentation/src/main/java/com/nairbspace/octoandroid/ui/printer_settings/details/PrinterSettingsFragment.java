package com.nairbspace.octoandroid.ui.printer_settings.details;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.nairbspace.octoandroid.R;

public class PrinterSettingsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    public static PrinterSettingsFragment newInstance() {
        return new PrinterSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.printer_preferences);

        // For all preferences, attach an OnPreferenceChangeListener so the UI summary can be
        // updated when the preference changes.
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_name_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_host_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_api_key_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_scheme_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_port_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_websocket_path_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_webcam_path_query_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.prefs_printer_upload_location_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(this);

        // Trigger the listener immediately with the preference's
        // current value.
        onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String stringValue = newValue.toString();

        if (preference instanceof ListPreference) {
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list (since they have separate labels/values).
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            // For other preferences, set the summary to the value's simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    }
}
