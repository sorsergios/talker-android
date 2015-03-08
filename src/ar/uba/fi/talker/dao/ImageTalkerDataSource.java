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
	private String[] allColumns = { ImagesSQLiteHelper.COLUMN_IDCODE, ImagesSQLiteHelper.COLUMN_TEXT };

	public ImageTalkerDataSource(Context context) {
		dbHelper = new ImagesSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ScenarioDAO createScenario(String text) {
		ContentValues values = new ContentValues();
		values.put(ImagesSQLiteHelper.COLUMN_TEXT, text);
		long insertId = database.insert(ImagesSQLiteHelper.TABLE_IMAGES, null,
				values);
		Cursor cursor = database.query(ImagesSQLiteHelper.TABLE_IMAGES,
				allColumns,
				ImagesSQLiteHelper.COLUMN_IDCODE + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		ScenarioDAO newScenario = cursorToImages(cursor);
		cursor.close();
		return newScenario;
	}

	public void deleteScenario(Long keyID) {
		System.out.println("Images deleted with id: " + keyID);
		database.delete(ImagesSQLiteHelper.TABLE_IMAGES,
				ImagesSQLiteHelper.COLUMN_IDCODE + " = " + keyID, null);
	}

	public List<ScenarioDAO> getAllImages() {
		List<ScenarioDAO> images = new ArrayList<ScenarioDAO>();

		Cursor cursor = database.query(ImagesSQLiteHelper.TABLE_IMAGES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ScenarioDAO scenario = cursorToImages(cursor);
			images.add(scenario);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return images;
	}

	private ScenarioDAO cursorToImages(Cursor cursor) {
		ScenarioDAO scenario = new ScenarioDAO();
		scenario.setID(cursor.getInt(0));
		scenario.setText(cursor.getString(1));
		return scenario;
	}
	
	public ScenarioDAO getScenarioByID(int keyId) {
		Cursor cursor = database.query(ImagesSQLiteHelper.TABLE_IMAGES,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		ScenarioDAO scenario = cursorToImages(cursor);
		// make sure to close the cursor
		cursor.close();
		return scenario;
	}
}
