package ar.uba.fi.talker.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ScenarioTalkerDataSource {
	
	private SQLiteDatabase database;
	private ResourceSQLiteHelper dbHelper;
	private String[] allColumns = { ResourceSQLiteHelper.SCENARIO_COLUMN_ID,
			ResourceSQLiteHelper.SCENARIO_COLUMN_PATH,
			ResourceSQLiteHelper.SCENARIO_COLUMN_NAME };

	public ScenarioTalkerDataSource(Context context) {
		dbHelper = new ResourceSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public int getLastScenarioID() {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.SCENARIO_TABLE + " ORDER BY "
				+ ResourceSQLiteHelper.SCENARIO_COLUMN_ID + " DESC LIMIT 1", null);
		cursor.moveToFirst();
		ScenarioDAO scenario = cursorToImages(cursor);
		cursor.close();
		return scenario.getId();
	}
	
	public ScenarioDAO createScenario(String path, String name) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.SCENARIO_COLUMN_PATH, path);
		values.put(ResourceSQLiteHelper.SCENARIO_COLUMN_NAME, name);
		long insertId = database.insert(ResourceSQLiteHelper.SCENARIO_TABLE, null, values);
		Cursor cursor = database.query(ResourceSQLiteHelper.SCENARIO_TABLE, allColumns,
				ResourceSQLiteHelper.SCENARIO_COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ScenarioDAO newScenario = cursorToImages(cursor);
		cursor.close();
		return newScenario;
	}

	public void deleteScenario(Long keyID) {
		database.delete(ResourceSQLiteHelper.SCENARIO_TABLE,
				ResourceSQLiteHelper.SCENARIO_COLUMN_ID + " = " + keyID, null);
	}

	public List<ScenarioDAO> getAllImages() {
		List<ScenarioDAO> images = new ArrayList<ScenarioDAO>();

		Cursor cursor = database.query(ResourceSQLiteHelper.SCENARIO_TABLE,
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
		scenario.setPath(cursor.getString(1));
		scenario.setName(cursor.getString(2));
		return scenario;
	}
	
	public ScenarioDAO getScenarioByID(int keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.SCENARIO_TABLE + " WHERE "
				+ ResourceSQLiteHelper.SCENARIO_COLUMN_ID + " = " + keyId, null);
		cursor.moveToFirst();
		ScenarioDAO scenario = cursorToImages(cursor);
		cursor.close();
		return scenario;
	}
	
	public void updateScenario(Long keyID, String name) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.SCENARIO_COLUMN_NAME,name);
		database.update(ResourceSQLiteHelper.SCENARIO_TABLE, values,
				ResourceSQLiteHelper.SCENARIO_COLUMN_ID + " = " + keyID, null);
	}
	
}
