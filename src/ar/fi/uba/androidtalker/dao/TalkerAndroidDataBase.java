package ar.fi.uba.androidtalker.dao;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import ar.fi.uba.androidtalker.R;

public class TalkerAndroidDataBase extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_talker_main);

		// Open the database 'DBImagesTalker' in write mode
		ImagesSQLiteHelper usdbh = new ImagesSQLiteHelper(this,
				"DBImagesTalker", null, 1);

		SQLiteDatabase db = usdbh.getWritableDatabase();

		// If the database was successfully opened 
		if (db != null) {
			// Example 5 images insert
			for (int i = 1; i <= 5; i++) {
				// We generate data
				int idCode = i;
				String name = "Name" + i;

				// We entered the data in the ImagesTalker table
				db.execSQL("INSERT INTO ImagesTalker (idCode, name) " + "VALUES ("
						+ idCode + ", '" + name + "')");
			}

			// Close the database
			db.close();
		}
	}

}
