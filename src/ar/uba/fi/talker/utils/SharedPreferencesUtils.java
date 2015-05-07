package ar.uba.fi.talker.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {
	
	public static void savePreference(Activity activity, String key, int value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
		Editor editor = sp.edit();

		editor.putInt(key, value);

		editor.commit();
	}

	public static int readPreference(Activity activity, String key, int value) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
		return sp.getInt(key, value);
	}
	
}
