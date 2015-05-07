package ar.uba.fi.talker.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import ar.uba.fi.talker.CanvasActivity;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.component.Setting;
import ar.uba.fi.talker.utils.SharedPreferencesUtils;

public class SettingTalkerDataSource {
	
	private SQLiteDatabase database;
	private final ResourceSQLiteHelper dbHelper;
	private final String[] allColumns = { ResourceSQLiteHelper.SETTING_COLUMN_KEY,
			ResourceSQLiteHelper.SETTING_COLUMN_VALUE};

	public SettingTalkerDataSource(Context context) {
		dbHelper = new ResourceSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public String getSettingValueByKey(int keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.SETTING_TABLE + " WHERE "
				+ ResourceSQLiteHelper.SETTING_COLUMN_KEY + " = " + keyId, null);
		String value = null;
		if (cursor != null && cursor.moveToFirst()) {
		     value = cursor.getString(cursor.getColumnIndex(ResourceSQLiteHelper.SETTING_COLUMN_VALUE));
		}
		cursor.close();
		return value;
	}

	//same concept as SHARED PREFERENCES
	public Setting getSettings(CanvasActivity canvasActivity) {
		
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(canvasActivity);

		Setting setting = new Setting();
		sharedPref.getAll();
		int textColor = SharedPreferencesUtils.readPreferences(canvasActivity, canvasActivity.getResources().getString(R.string.settings_text_color_key),  -16777216);
				//sharedPref.getInt(getSettingValueByKey(R.string.settings_text_color_key), -16777216);
		setting.setTextColor(textColor);
		String textSize = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_text_size_key),"100");
		setting.setTextSize(Integer.parseInt(textSize));
		String textWidth = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_text_width_key), "10");
		setting.setTextWidth(Integer.parseInt(textWidth));
		String pencilColor = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_pencil_color_key),"-16776961");
		setting.setPencilColor(Integer.parseInt(pencilColor));
		String pencilSize = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_pencil_size_key),"20");
		setting.setPencilSize(Integer.parseInt(pencilSize));
		String eraserSize = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_eraser_size_key), "30");
		setting.setEraserSize(Integer.parseInt(eraserSize));
		Boolean isEnabledLabelImage = sharedPref.getBoolean(canvasActivity.getResources().getString(R.string.settings_image_tag_key),Boolean.FALSE) ;
		setting.setIsEnabledLabelImage(isEnabledLabelImage);
		Boolean isEnabledLabelContact = sharedPref.getBoolean(canvasActivity.getResources().getString(R.string.settings_contact_tag_key), Boolean.FALSE);
		setting.setIsEnabledLabelContact(isEnabledLabelContact);
		
		return setting;
	}
	
}

