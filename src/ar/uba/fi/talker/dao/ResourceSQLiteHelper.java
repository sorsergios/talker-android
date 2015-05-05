package ar.uba.fi.talker.dao;

import java.io.File;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.util.Log;
import ar.uba.fi.talker.R;

public class ResourceSQLiteHelper extends SQLiteOpenHelper {

	//SQL sentence to create Images table
    
	private final Context context;
	private static final String DATABASE_NAME = "talker.db";
	private static final int DATABASE_VERSION = 1;
	
	//SQLite does not have a separate Boolean storage class. Instead, Boolean values are stored as integers 0 (false) and 1 (true).
	public static int FALSE = 0;
	public static int TRUE = 1;
	
	private static int CATEGORY_ID = 1;
	
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
	public static final String CATEGORY_COLUMN_IS_CONTACT = "is_contact_category";
	
	//IMAGE TABLE
	public static final String IMAGE_TABLE = "image";
	public static final String IMAGE_COLUMN_ID = "id";
	public static final String IMAGE_COLUMN_IDCATEGORY = "id_category";
	public static final String IMAGE_COLUMN_IDCODE = "id_code";
	public static final String IMAGE_COLUMN_PATH = "path";
	public static final String IMAGE_COLUMN_NAME = "name";
	public static final String IMAGE_COLUMN_IS_CONTACT = "is_contact";
	
	//CONVERSATION TABLE
	public static final String CONVERSATION_TABLE = "conversation";
	public static final String CONVERSATION_COLUMN_ID = "id";
	public static final String CONVERSATION_COLUMN_PATH = "path";
	public static final String CONVERSATION_COLUMN_NAME = "name";
	public static final String CONVERSATION_COLUMN_SNAPSHOT = "path_snapshot";
	
	//SETTINGS TABLE
	public static final String SETTING_TABLE = "setting";
	public static final String SETTING_COLUMN_KEY = "key";
	public static final String SETTING_COLUMN_VALUE = "value";
	
	//CONTACT TABLE
	public static final String CONTACT_TABLE = "contact";
	public static final String CONTACT_COLUMN_ID = "id";
	public static final String CONTACT_COLUMN_IMAGE_ID = "id_image";
	public static final String CONTACT_COLUMN_ADDRESS = "address";
	public static final String CONTACT_COLUMN_PHONE = "phone";
	
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

	boolean isTableExists(SQLiteDatabase db, String tableName) {
		if (tableName == null || db == null || !db.isOpen()) {
			return false;
		}
		Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] { "table", tableName });
		if (!cursor.moveToFirst()) {
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
							+ IMAGE_COLUMN_NAME + " TEXT, "
							+ CATEGORY_COLUMN_IS_CONTACT + " INTEGER DEFAULT 0)");
				}
				db.execSQL("INSERT INTO " + CATEGORY_TABLE + " ( " + CATEGORY_COLUMN_ID + " , " + CATEGORY_COLUMN_NAME + " ) "
						+ " VALUES (" + CATEGORY_ID++ + ",\"ANIMALES\")");
				db.execSQL("INSERT INTO " + CATEGORY_TABLE + " ( " + CATEGORY_COLUMN_ID + " , " + CATEGORY_COLUMN_NAME + " ) "
						+ " VALUES (" + CATEGORY_ID++ + ",\"COMIDA\")");
				db.execSQL("INSERT INTO " + CATEGORY_TABLE + " ( " + CATEGORY_COLUMN_ID + " , " + CATEGORY_COLUMN_NAME + " ) "
						+ " VALUES (" + CATEGORY_ID++ + ",\"OBJETOS\")");
				db.execSQL("INSERT INTO " + CATEGORY_TABLE + " ( " + CATEGORY_COLUMN_ID + " , " + CATEGORY_COLUMN_NAME + " ) "
						+ " VALUES (" + CATEGORY_ID++ + ",\"PATIO\")");
				db.execSQL("INSERT INTO " + CATEGORY_TABLE + " ( " + CATEGORY_COLUMN_ID + " , " + CATEGORY_COLUMN_NAME + " , " + CATEGORY_COLUMN_IS_CONTACT + " ) "
						+ " VALUES (" + CATEGORY_ID++ + ",\"FUNDACION\", "+ TRUE +")");
			}

			if (!isTableExists(db, IMAGE_TABLE)) {
				db.execSQL("CREATE TABLE " + IMAGE_TABLE + " ( "
						+ IMAGE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ IMAGE_COLUMN_IDCODE + " INTEGER, "
						+ IMAGE_COLUMN_PATH + " TEXT, "
						+ IMAGE_COLUMN_NAME + " TEXT, "
						+ IMAGE_COLUMN_IDCATEGORY + " INTEGER)");
				
				for (int i = 0; i < mThumbIdsImagesForCateg1.length; i++) {
					// Generate and insert default data
					int idCode = mThumbIdsImagesForCateg1[i];
					String name = context.getResources().getString(mThumbTextsImagesForCateg1[i]);

					db.execSQL("INSERT INTO " + IMAGE_TABLE + " ( "
							+ IMAGE_COLUMN_IDCODE + " , " 
							+ IMAGE_COLUMN_IDCATEGORY + " , " + IMAGE_COLUMN_NAME + " ) "
							+ " VALUES (" + idCode + ", " + 1 + ", '" + name + "')");
				}
				for (int i = 0; i < mThumbIdsImagesForCateg2.length; i++) {
					// Generate and insert default data
					int idCode = mThumbIdsImagesForCateg2[i];
					String name = context.getResources().getString(mThumbTextsImagesForCateg2[i]);

					db.execSQL("INSERT INTO " + IMAGE_TABLE + " ( "
							+ IMAGE_COLUMN_IDCODE + " , " 
							+ IMAGE_COLUMN_IDCATEGORY + " , " + IMAGE_COLUMN_NAME + " ) "
							+ " VALUES (" + idCode + ", " + 2 + ", '" + name + "')");
				}
				
				for (int i = 0; i < mThumbIdsImagesForCateg3.length; i++) {
					// Generate and insert default data
					int idCode = mThumbIdsImagesForCateg3[i];
					String name = context.getResources().getString(mThumbTextsImagesForCateg3[i]);

					db.execSQL("INSERT INTO " + IMAGE_TABLE + " ( "
							+ IMAGE_COLUMN_IDCODE + " , " 
							+ IMAGE_COLUMN_IDCATEGORY + " , " + IMAGE_COLUMN_NAME + " ) "
							+ " VALUES (" + idCode + ", " + 3 + ", '" + name + "')");
				}
			}
			
			if (!isTableExists(db, CONVERSATION_TABLE)) {
				db.execSQL("CREATE TABLE " + CONVERSATION_TABLE + " ( "
						+ CONVERSATION_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ CONVERSATION_COLUMN_PATH + " TEXT, "
						+ CONVERSATION_COLUMN_NAME + " TEXT, "
						+ CONVERSATION_COLUMN_SNAPSHOT + " TEXT )");
			}

			if (!isTableExists(db, SETTING_TABLE)) {
				db.execSQL("CREATE TABLE " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " INTEGER PRIMARY KEY, "
						+ SETTING_COLUMN_VALUE + " TEXT )");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_text_color_key + ", " + Integer.toString(Color.BLACK) + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_text_size_key + ", " + "'100'" + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_text_width_key + ", " + "'10'" + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_pencil_color_key + ", " + Integer.toString(Color.BLUE) + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_pencil_size_key + ", " + "'20'" + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_eraser_size_key + ", " + "'30'" + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_image_tag_key + ", " + "'f'" + ")");
				db.execSQL("INSERT INTO " + SETTING_TABLE + " ( "
						+ SETTING_COLUMN_KEY + " , " 
						+ SETTING_COLUMN_VALUE + " ) "
						+ " VALUES (" + R.string.settings_contact_tag_key + ", " + "'f'" + ")");
			}
			
			if (!isTableExists(db, CONTACT_TABLE)) {
				db.execSQL("CREATE TABLE " + CONTACT_TABLE + " ( "
						+ CONTACT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
						+ CONTACT_COLUMN_IMAGE_ID + " INTEGER , " //FIXME no se como poner foreign key
						+ CONTACT_COLUMN_PHONE + " TEXT, "
						+ CONTACT_COLUMN_ADDRESS + " TEXT )");
			
				for (int i = 0; i < mThumbIdsImagesForCateg5.length; i++) {
					// Generate and insert default data
					int idCode = mThumbIdsImagesForCateg5[i];
					String name = context.getResources().getString(mThumbTextsImagesForCateg5[i]);

					db.execSQL("INSERT INTO " + IMAGE_TABLE + " ( "
							+ IMAGE_COLUMN_IDCODE + " , " 
							+ IMAGE_COLUMN_IDCATEGORY + " , " + IMAGE_COLUMN_NAME + " ) "
							+ " VALUES (" + idCode + ", " + 5 + ", '" + name + "')");
				}
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(ResourceSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + SCENARIO_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + IMAGE_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CONVERSATION_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
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
	
	/*IMAGES OF IMAGE CATEGORIES*/
	private static Integer[] mThumbIdsImagesForCateg1 = {
		R.drawable.an_1,
		R.drawable.an_2,
		R.drawable.an_3
	};
	private static Integer[] mThumbTextsImagesForCateg1 = {
		R.string.an_1,
		R.string.an_2,
		R.string.an_3
	};
	private static Integer[] mThumbIdsImagesForCateg2 = {
		R.drawable.food_1,
        R.drawable.food_2,
        R.drawable.food_3
	};
	private static Integer[] mThumbTextsImagesForCateg2 = {
		R.string.food_1,
		R.string.food_2,
		R.string.food_3
	};
	private static Integer[] mThumbIdsImagesForCateg3 = {
		R.drawable.ob_1
	};
	private static Integer[] mThumbTextsImagesForCateg3 = {
		R.string.ob_1
	};
	
	/*IMAGES OF CONTACT CATEGORIES*/
	private static Integer[] mThumbIdsImagesForCateg5 = {
		R.drawable.con_1
	};
	private static Integer[] mThumbTextsImagesForCateg5 = {
		R.string.con_1
	};
	
}
