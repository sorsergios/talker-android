package ar.uba.fi.talker.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.component.Setting;

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
	public Setting getSettings() {
		Setting setting = new Setting();
		
		String textColor = getSettingValueByKey(R.string.settings_text_color_key);
		setting.setTextColor(Integer.parseInt(textColor));
		String textSize = getSettingValueByKey(R.string.settings_text_size_key);
		setting.setTextSize(Integer.parseInt(textSize));
		String textWidth = getSettingValueByKey(R.string.settings_text_width_key);
		setting.setTextWidth(Integer.parseInt(textWidth));
		String pencilColor = getSettingValueByKey(R.string.settings_pencil_color_key);
		setting.setPencilColor(Integer.parseInt(pencilColor));
		String pencilSize = getSettingValueByKey(R.string.settings_pencil_size_key);
		setting.setPencilSize(Float.parseFloat(pencilSize));
		String eraserSize = getSettingValueByKey(R.string.settings_eraser_size_key);
		setting.setEraserSize(Float.parseFloat(eraserSize));
		String isEnabledLabelImage = getSettingValueByKey(R.string.settings_image_tag_key);
		setting.setIsEnabledLabelImage("f".equals(isEnabledLabelImage) ? false : true);
		String isEnabledLabelContact = getSettingValueByKey(R.string.settings_contact_tag_key);
		setting.setIsEnabledLabelImage("f".equals(isEnabledLabelContact) ? false : true);
		
		return setting;
	}
	
}

