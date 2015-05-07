package ar.uba.fi.talker.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SharedPreferencesUtils {
	public static void savePreferences(Activity activity, String key1,
            int value1) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        Editor editor = sp.edit();

        editor.putInt(key1, value1);

        editor.commit();
    }

    public static int readPreferences(Activity activity, String key,
            int defaultValue) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(activity.getApplicationContext());
        return sp.getInt(key, defaultValue);
    }
}
