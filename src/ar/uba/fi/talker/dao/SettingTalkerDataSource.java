package ar.uba.fi.talker.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
				+ ResourceSQLiteHelper.SETTING_COLUMN_VALUE + " WHERE "
				+ ResourceSQLiteHelper.SETTING_COLUMN_KEY + " = " + keyId, null);
		cursor.moveToFirst();
		String value = cursor.getString(1);
		cursor.close();
		return value;
	}

	public Setting getSettings() {
		Setting setting = new Setting();
		//FIXME centralizacion de configuracion
		setting.setPencilColor(Color.BLUE);
		setting.setPencilSize(20);
		return setting;
	}
	
}

