package ar.uba.fi.talker.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ImageTalkerDataSource {
	
	private SQLiteDatabase database;
	private ImagesSQLiteHelper dbHelper;
	private String[] allColumns = { ImagesSQLiteHelper.COLUMN_ID,
			ImagesSQLiteHelper.COLUMN_IDCODE, ImagesSQLiteHelper.COLUMN_PATH,
			ImagesSQLiteHelper.COLUMN_NAME };

	public ImageTalkerDataSource(Context context) {
		dbHelper = new ImagesSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ScenarioDAO createScenario(String path, String name) {
		ContentValues values = new ContentValues();
		values.put(ImagesSQLiteHelper.COLUMN_PATH, path);
		values.put(ImagesSQLiteHelper.COLUMN_NAME, name);
		long insertId = database.insert(ImagesSQLiteHelper.TABLE_SCENARIO, null, values);
		Cursor cursor = database.query(ImagesSQLiteHelper.TABLE_SCENARIO, allColumns,
				ImagesSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ScenarioDAO newScenario = cursorToImages(cursor);
		cursor.close();
		return newScenario;
	}

	public void deleteScenario(Long keyID) {
		database.delete(ImagesSQLiteHelper.TABLE_SCENARIO,
				ImagesSQLiteHelper.COLUMN_ID + " = " + keyID, null);
	}

	public List<ScenarioDAO> getAllImages() {
		List<ScenarioDAO> images = new ArrayList<ScenarioDAO>();

		Cursor cursor = database.query(ImagesSQLiteHelper.TABLE_SCENARIO,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ScenarioDAO scenario = cursorToImages(cursor);
			images.add(scenario);
			cursor.moveToNext();
		}
		cursor.close();
		return images;
	}

	private ScenarioDAO cursorToImages(Cursor cursor) {
		ScenarioDAO scenario = new ScenarioDAO();
		scenario.setId(cursor.getInt(0));
		scenario.setIdCode(cursor.getInt(1));
		scenario.setPath(cursor.getString(2));
		scenario.setName(cursor.getString(3));
		return scenario;
	}
	
	public ScenarioDAO getScenarioByID(int keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ImagesSQLiteHelper.TABLE_SCENARIO + " WHERE "
				+ ImagesSQLiteHelper.COLUMN_ID + " = " + keyId, null);
		cursor.moveToFirst();
		ScenarioDAO scenario = cursorToImages(cursor);
		cursor.close();
		return scenario;
	}
	
	public void updateScenario(Long keyID, String name) {
		ContentValues values = new ContentValues();
		values.put(ImagesSQLiteHelper.COLUMN_NAME,name);
		database.update(ImagesSQLiteHelper.TABLE_SCENARIO, values,
				ImagesSQLiteHelper.COLUMN_ID + " = " + keyID, null);
	}
	
}
