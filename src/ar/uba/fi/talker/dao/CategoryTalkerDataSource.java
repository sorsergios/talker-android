package ar.uba.fi.talker.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CategoryTalkerDataSource {
	
	private SQLiteDatabase database;
	private final ResourceSQLiteHelper dbHelper;
	private final String[] allColumns = { ResourceSQLiteHelper.CATEGORY_COLUMN_ID,
			ResourceSQLiteHelper.CATEGORY_COLUMN_NAME, ResourceSQLiteHelper.CATEGORY_COLUMN_IS_CONTACT };

	public CategoryTalkerDataSource(Context context) {
		dbHelper = new ResourceSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public CategoryDAO createCategory(String name, int isContactCategory) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.CATEGORY_COLUMN_NAME, name);
		values.put(ResourceSQLiteHelper.CATEGORY_COLUMN_IS_CONTACT, isContactCategory);		
		long insertId = database.insert(ResourceSQLiteHelper.CATEGORY_TABLE, null, values);
		Cursor cursor = database.query(ResourceSQLiteHelper.CATEGORY_TABLE, allColumns,
				ResourceSQLiteHelper.CATEGORY_COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		CategoryDAO newScenario = cursorToCategory(cursor);
		cursor.close();
		return newScenario;
	}

	public List<CategoryDAO> getAllCategories() {
		List<CategoryDAO> categories = new ArrayList<CategoryDAO>();

		Cursor cursor = database.query(ResourceSQLiteHelper.CATEGORY_TABLE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CategoryDAO category = cursorToCategory(cursor);
			categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();
		return categories;
	}

	private CategoryDAO cursorToCategory(Cursor cursor) {
		CategoryDAO category = new CategoryDAO();
		category.setId(cursor.getInt(0));
		category.setName(cursor.getString(1));
		return category;
	}
	
	
	public CategoryDAO getCategoryByID(long keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.CATEGORY_TABLE + " WHERE "
				+ ResourceSQLiteHelper.CATEGORY_COLUMN_ID + " = " + keyId, null);
		cursor.moveToFirst();
		CategoryDAO category = cursorToCategory(cursor);
		cursor.close();
		return category;
	}
	
	public List<CategoryDAO> getContactCategories() {
		List<CategoryDAO> categories = new ArrayList<CategoryDAO>();

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.CATEGORY_TABLE + " WHERE "
				+ ResourceSQLiteHelper.CATEGORY_COLUMN_IS_CONTACT + " = " + ResourceSQLiteHelper.TRUE, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CategoryDAO category = cursorToCategory(cursor);
			ImageDAO image = getFirstImagesForCategory(category.getId());
			category.setImage(image);
			categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();
		return categories;
	}
	
	public List<CategoryDAO> getImageCategories() {
		List<CategoryDAO> categories = new ArrayList<CategoryDAO>();

		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.CATEGORY_TABLE + " WHERE "
				+ ResourceSQLiteHelper.CATEGORY_COLUMN_IS_CONTACT + " = " + ResourceSQLiteHelper.FALSE, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CategoryDAO category = cursorToCategory(cursor);
			ImageDAO image = getFirstImagesForCategory(category.getId());
			category.setImage(image);
			categories.add(category);
			cursor.moveToNext();
		}
		cursor.close();
		return categories;
	}

	public void deleteCategory(long keyID) {
		database.delete(ResourceSQLiteHelper.CATEGORY_TABLE,
			ResourceSQLiteHelper.CATEGORY_COLUMN_ID + " = " + keyID, null);
	}
	
	public void updateCategory(Long keyID, String name) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.CATEGORY_COLUMN_NAME,name);
		database.update(ResourceSQLiteHelper.CATEGORY_TABLE, values,
				ResourceSQLiteHelper.CATEGORY_COLUMN_ID + " = " + keyID, null);
	}

	public ImageDAO getFirstImagesForCategory(long keyId) {
		
		Cursor cursor = database.query(ResourceSQLiteHelper.IMAGE_TABLE, new String[]{"*"},
				ResourceSQLiteHelper.IMAGE_COLUMN_IDCATEGORY + " = " + keyId, null, null, null, 
				ResourceSQLiteHelper.IMAGE_COLUMN_ID, "1");
		cursor.moveToFirst();
		
		ImageDAO image = null;
		if (!cursor.isAfterLast()) {
			image = cursorToImages(cursor);
		}
		cursor.close();
		return image;
	}
	
	private ImageDAO cursorToImages(Cursor cursor) {
		ImageDAO image = new ImageDAO();
		image.setId(cursor.getInt(0));
		image.setPath(cursor.getString(1));
		image.setName(cursor.getString(2));
		image.setIdCategory(cursor.getInt(3));
		return image;
	}
}
