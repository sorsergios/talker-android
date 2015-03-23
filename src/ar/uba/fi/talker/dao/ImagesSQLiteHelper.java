package ar.uba.fi.talker.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ar.uba.fi.talker.R;

public class ImagesSQLiteHelper extends SQLiteOpenHelper {

	//SQL sentence to create Images table
    
	private Context context;
	private static final String DATABASE_NAME = "talker.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_SCENARIO = "scenario";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_IDCODE = "idCode";
	public static final String COLUMN_PATH = "path";
	public static final String COLUMN_NAME = "name";

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
			if (!isTableExists(db, TABLE_SCENARIO)) {
				db.execSQL("CREATE TABLE " + TABLE_SCENARIO + " ( "
						+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ COLUMN_IDCODE + " INTEGER, "
						+ COLUMN_PATH + " TEXT, "
						+ COLUMN_NAME + " TEXT)");
				for (int i = 0; i < mThumbIdsScenario.length; i++) {
					// Generate and insert default data
					int idCode = mThumbIdsScenario[i];
					String name = context.getResources().getString(mThumbTextsScenario[i]);

					db.execSQL("INSERT INTO " + TABLE_SCENARIO + " ( "
							+ COLUMN_IDCODE + " , " 
							+ COLUMN_PATH + " , " + COLUMN_NAME + " ) "
							+ " VALUES (" + idCode + ", " + "NULL" + ", '" + name + "')");
				}
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ImagesSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCENARIO);
		onCreate(db);
	}
	
	/*IMAGES OF SCENARIO*/
	private static Integer[] mThumbIdsScenario = {
			R.drawable.blanco,    
			R.drawable.casa,
	        R.drawable.oficina,
	        R.drawable.colectivo, 
	        R.drawable.escuela,
	        R.drawable.living,
	        R.drawable.cocina,
	        R.drawable.habitacion, 
	        R.drawable.banio,
	        R.drawable.aulaescuela,
	        R.drawable.banioescuela,
	        R.drawable.patioescuela
	};
	
	private static Integer[] mThumbTextsScenario = {
			R.string.blanco,
			R.string.casa,
			R.string.oficina,
			R.string.colectivo,
			R.string.escuela,
			R.string.living,
			R.string.cocina,
			R.string.habitacion,
			R.string.banio,
			R.string.aula,
			R.string.banio,
			R.string.patio
	};
}
