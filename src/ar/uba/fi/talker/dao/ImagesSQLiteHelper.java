package ar.uba.fi.talker.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ImagesSQLiteHelper extends SQLiteOpenHelper {

	//SQL sentence to create Images table
    
	private Context context;
	private static final String DATABASE_NAME = "Talker.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_IMAGES = "ImagesTalker";
	public static final String COLUMN_IDCODE = "idCode";
	public static final String COLUMN_TEXT = "text";

	public ImagesSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	public boolean checkDataBase() {
		File database = context.getDatabasePath(DATABASE_NAME);

		if (!database.exists()) {
			return false;
		}
		return true;
	}

	boolean isTableExists(SQLiteDatabase db, String tableName)
	{
	    if (tableName == null || db == null || !db.isOpen())
	    {
	        return false;
	    }
	    Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
	    if (!cursor.moveToFirst())
	    {
	        return false;
	    }
	    int count = cursor.getInt(0);
	    cursor.close();
	    return count > 0;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// Execute SQL sentence to create table
		if (checkDataBase()) {
			if (!isTableExists(db, TABLE_IMAGES)) {
				db.execSQL("CREATE TABLE " + TABLE_IMAGES + " ( "
						+ COLUMN_IDCODE + " INTEGER, " + COLUMN_TEXT + " TEXT)");
				for (int i = 0; i < ImagesDao.getScenarioSize(); i++) {
					// Generate and insert default data
					int idCode = ImagesDao.getScenarioImageByPos(i);
					String name = context.getResources().getString(ImagesDao.getScenarioNameByPos(i));

					db.execSQL("INSERT INTO " + TABLE_IMAGES + " ( "
							+ COLUMN_IDCODE + " , " + COLUMN_TEXT + " ) "
							+ " VALUES (" + idCode + ", '" + name + "')");
				}
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ImagesSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
		onCreate(db);
	}

}
