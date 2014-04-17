package com.npogulanik.parquimetro.activities;

import java.util.prefs.Preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.view.View;

import com.npogulanik.parquimetro.R;
import com.npogulanik.parquimetro.Utils;
import com.npogulanik.parquimetro.fsm.IdleState;
import com.npogulanik.parquimetro.fsm.OnPreferencesState;
import com.npogulanik.parquimetro.fsm.ParquimetroContext;

public class PreferencesActivity extends PreferenceActivity implements
		OnSharedPreferenceChangeListener {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		//hago funcionar el boton para salir de las preferecias
		Preference exitButton = (Preference)getPreferenceManager().findPreference("exitlink");      
	    if(exitButton != null) {
	    	exitButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	                    @Override
	                    public boolean onPreferenceClick(Preference arg0) {
	                        finish();   
	                        return true;
	                    }
	                });     
	    }
	    
	    Preference selectLauncherButton = (Preference)getPreferenceManager().findPreference("selectLauncher");      
	    if(selectLauncherButton != null) {
	    	selectLauncherButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
	                    @Override
	                    public boolean onPreferenceClick(Preference arg0) {
	                    	Utils.ShowStatusBar();
	                    	Intent i = new Intent(Intent.ACTION_MAIN);
	        				i.addCategory(Intent.CATEGORY_HOME);
	        				i.addCategory(Intent.CATEGORY_DEFAULT);
	        				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        				startActivity(Intent.createChooser(i, "Seleccione Launcher"));
	                        return true;
	                    }
	                });     
	    }
	   
		
		PreferenceManager.setDefaultValues(PreferencesActivity.this, R.xml.preferences,
				false);
		for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
			initSummary(getPreferenceScreen().getPreference(i));
		}
		boolean isEnabled = getPreferenceScreen().getSharedPreferences().getBoolean("prefScreenSaverEnabled", true);
		getPreferenceScreen().findPreference("prefScreenSaverTime").setEnabled(isEnabled);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		// Set up a listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
		super.onPause();
		// Unregister the listener whenever a key changes
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}

	@SuppressWarnings("deprecation")
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		updatePrefSummary(findPreference(key));
		if (key.equals("prefScreenSaverEnabled")){
			boolean isEnabled = sharedPreferences.getBoolean(key, true);
			getPreferenceScreen().findPreference("prefScreenSaverTime").setEnabled(isEnabled);
		}
	}

	private void initSummary(Preference p) {
		if (p instanceof PreferenceCategory) {
			PreferenceCategory pCat = (PreferenceCategory) p;
			for (int i = 0; i < pCat.getPreferenceCount(); i++) {
				initSummary(pCat.getPreference(i));
			}
		} else {
			updatePrefSummary(p);
		}
	}

	private void updatePrefSummary(Preference p) {
		if (p instanceof ListPreference) {
			ListPreference listPref = (ListPreference) p;
			p.setSummary(listPref.getEntry());
		}
		if (p instanceof EditTextPreference) {
			EditTextPreference editTextPref = (EditTextPreference) p;
			p.setSummary(editTextPref.getText());
		}
	}
}