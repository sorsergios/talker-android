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
	private final ResourceSQLiteHelper dbHelper;
	private final String[] allColumns = { ResourceSQLiteHelper.IMAGE_COLUMN_ID,
			ResourceSQLiteHelper.IMAGE_COLUMN_IDCODE, ResourceSQLiteHelper.IMAGE_COLUMN_PATH,
			ResourceSQLiteHelper.IMAGE_COLUMN_NAME, ResourceSQLiteHelper.IMAGE_COLUMN_IDCATEGORY };

	public ImageTalkerDataSource(Context context) {
		dbHelper = new ResourceSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ImageDAO createImage(String path, String name) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.IMAGE_COLUMN_PATH, path);
		values.put(ResourceSQLiteHelper.IMAGE_COLUMN_NAME, name);
		long insertId = database.insert(ResourceSQLiteHelper.IMAGE_TABLE, null, values);
		Cursor cursor = database.query(ResourceSQLiteHelper.IMAGE_TABLE, allColumns,
				ResourceSQLiteHelper.IMAGE_COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ImageDAO newImage = cursorToImages(cursor);
		cursor.close();
		return newImage;
	}

	public void deleteScenario(Long keyID) {
		database.delete(ResourceSQLiteHelper.IMAGE_TABLE,
				ResourceSQLiteHelper.IMAGE_COLUMN_ID + " = " + keyID, null);
	}

	public List<ImageDAO> getAllImages() {
		List<ImageDAO> images = new ArrayList<ImageDAO>();

		Cursor cursor = database.query(ResourceSQLiteHelper.IMAGE_TABLE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ImageDAO image = cursorToImages(cursor);
			images.add(image);
			cursor.moveToNext();
		}
		cursor.close();
		return images;
	}

	private ImageDAO cursorToImages(Cursor cursor) {
		ImageDAO scenario = new ImageDAO();
		scenario.setId(cursor.getInt(0));
		scenario.setIdCode(cursor.getInt(1));
		scenario.setPath(cursor.getString(2));
		scenario.setName(cursor.getString(3));
		scenario.setIdCategory(cursor.getInt(4));
		return scenario;
	}
	
	public ImageDAO getScenarioByID(int keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.IMAGE_TABLE + " WHERE "
				+ ResourceSQLiteHelper.IMAGE_COLUMN_ID + " = " + keyId, null);
		cursor.moveToFirst();
		ImageDAO scenario = cursorToImages(cursor);
		cursor.close();
		return scenario;
	}
	
	public void updateScenario(Long keyID, String name) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.IMAGE_COLUMN_NAME,name);
		database.update(ResourceSQLiteHelper.IMAGE_TABLE, values,
				ResourceSQLiteHelper.IMAGE_COLUMN_ID + " = " + keyID, null);
	}
	
}
