package ar.uba.fi.talker.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ImagesSQLiteHelper extends SQLiteOpenHelper {

	//SQL sentence to create Images table
    String sqlCreate = "CREATE TABLE ImagesTalker (idCode INTEGER, name TEXT)";
 
	public ImagesSQLiteHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Execute SQL sentence to create table
        db.execSQL(sqlCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO: change this, is just an example
        db.execSQL("DROP TABLE IF EXISTS ImagesTalker");
        db.execSQL(sqlCreate);
	}

}
