package ar.uba.fi.talker.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ar.uba.fi.talker.R;

public class ResourceSQLiteHelper extends SQLiteOpenHelper {

	//SQL sentence to create Images table
    
	private final Context context;
	private static final String DATABASE_NAME = "talker.db";
	private static final int DATABASE_VERSION = 1;
	
	//SCENARIO TABLE
	public static final String SCENARIO_TABLE = "scenario";
	public static final String SCENARIO_COLUMN_ID = "id";
	public static final String SCENARIO_COLUMN_IDCODE = "id_code";
	public static final String SCENARIO_COLUMN_PATH = "path";
	public static final String SCENARIO_COLUMN_NAME = "name";

	//CATEGORY TABLE
	public static final String CATEGORY_TABLE = "category";
	public static final String CATEGORY_COLUMN_ID = "id";
	public static final String CATEGORY_COLUMN_NAME = "name";
	
	//IMAGE TABLE
	public static final String IMAGE_TABLE = "image";
	public static final String IMAGE_COLUMN_ID = "id";
	public static final String IMAGE_COLUMN_IDCATEGORY = "id_category";
	public static final String IMAGE_COLUMN_IDCODE = "id_code";
	public static final String IMAGE_COLUMN_PATH = "path";
	public static final String IMAGE_COLUMN_NAME = "name";
	
	//CONTACT TABLE
	
	
	public ResourceSQLiteHelper(Context context) {
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
			if (!isTableExists(db, SCENARIO_TABLE)) {
				db.execSQL("CREATE TABLE " + SCENARIO_TABLE + " ( "
						+ SCENARIO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ SCENARIO_COLUMN_IDCODE + " INTEGER, "
						+ SCENARIO_COLUMN_PATH + " TEXT, "
						+ SCENARIO_COLUMN_NAME + " TEXT)");
				for (int i = 0; i < mThumbIdsScenario.length; i++) {
					// Generate and insert default data
					int idCode = mThumbIdsScenario[i];
					String name = context.getResources().getString(mThumbTextsScenario[i]);

					db.execSQL("INSERT INTO " + SCENARIO_TABLE + " ( "
							+ SCENARIO_COLUMN_IDCODE + " , " 
							+ SCENARIO_COLUMN_PATH + " , " + SCENARIO_COLUMN_NAME + " ) "
							+ " VALUES (" + idCode + ", " + "NULL" + ", '" + name + "')");
				}
			}
			if (!isTableExists(db, CATEGORY_TABLE)) {
				if (!isTableExists(db, CATEGORY_TABLE)) {
					db.execSQL("CREATE TABLE " + CATEGORY_TABLE + " ( "
							+ CATEGORY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
							+ IMAGE_COLUMN_NAME + " TEXT)");
					
				}
			}
			if (!isTableExists(db, IMAGE_TABLE)) {
				db.execSQL("CREATE TABLE " + IMAGE_TABLE + " ( "
						+ IMAGE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ IMAGE_COLUMN_IDCODE + " INTEGER, "
						+ IMAGE_COLUMN_PATH + " TEXT, "
						+ IMAGE_COLUMN_NAME + " TEXT, "
						+ IMAGE_COLUMN_IDCATEGORY + " INTEGER)");
				
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ResourceSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + SCENARIO_TABLE);
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
