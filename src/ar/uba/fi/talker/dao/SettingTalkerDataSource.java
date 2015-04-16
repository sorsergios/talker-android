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

	public Setting getSettings() {
		Setting setting = new Setting();
		int[] keys = { R.string.settings_text_color_key, 
				R.string.settings_pencil_color_key, 
				R.string.settings_pencil_size_key,
				R.string.settings_eraser_size_key, 
				R.string.settings_image_tag_key, 
				R.string.settings_contact_tag_key };

		String pencilColor = getSettingValueByKey(R.string.settings_pencil_color_key);
		setting.setPencilColor(Integer.parseInt(pencilColor));
		
		String pencilSize = getSettingValueByKey(R.string.settings_pencil_size_key);
		setting.setPencilSize( Float.parseFloat(pencilSize));
		
		//FIXME faltan otras configs
		
		return setting;
	}
	
}

