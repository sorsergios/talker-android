package ar.uba.fi.talker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactTalkerDataSource {
	
	private SQLiteDatabase database;
	private final ResourceSQLiteHelper dbHelper;
	private final String[] allColumns = {
			ResourceSQLiteHelper.CONTACT_COLUMN_ID,
			ResourceSQLiteHelper.CONTACT_COLUMN_IMAGE_ID,
			ResourceSQLiteHelper.CONTACT_COLUMN_PHONE,
			ResourceSQLiteHelper.CONTACT_COLUMN_ADDRESS };

	public ContactTalkerDataSource(Context context) {
		dbHelper = new ResourceSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ContactDAO createContact(long imageId, String phone, String address) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.CONTACT_COLUMN_IMAGE_ID, imageId);
		values.put(ResourceSQLiteHelper.CONTACT_COLUMN_PHONE, phone);
		values.put(ResourceSQLiteHelper.CONTACT_COLUMN_ADDRESS, address);
		long insertId = database.insert(ResourceSQLiteHelper.CONTACT_TABLE, null, values);
		Cursor cursor = database.query(ResourceSQLiteHelper.CONTACT_TABLE, allColumns,
				ResourceSQLiteHelper.CONTACT_COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ContactDAO newContact = cursorToContact(cursor);
		cursor.close();
		return newContact;
	}

	private ContactDAO cursorToContact(Cursor cursor) {
		ContactDAO contact = new ContactDAO();
		contact.setId(cursor.getInt(0));
		contact.setImageId(cursor.getLong(1));
		contact.setPhone(cursor.getString(2));
		contact.setAddress(cursor.getString(3));
		return contact;
	}
	
	
	public ContactDAO getContactByImageID(long keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.CONTACT_TABLE + " WHERE "
				+ ResourceSQLiteHelper.CONTACT_COLUMN_IMAGE_ID + " = " + keyId, null);
		cursor.moveToFirst();
		ContactDAO contact = cursorToContact(cursor);
		cursor.close();
		return contact;
	}

	public void deleteContactByImageID(long keyID) {
		database.delete(ResourceSQLiteHelper.CONTACT_TABLE,
			ResourceSQLiteHelper.CONTACT_COLUMN_IMAGE_ID + " = " + keyID, null);
	}
	
	public void updateContact(Long keyID, String phone, String address) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.CONTACT_COLUMN_PHONE, phone);
		values.put(ResourceSQLiteHelper.CONTACT_COLUMN_ADDRESS, address);
		database.update(ResourceSQLiteHelper.CONTACT_TABLE, values,
				ResourceSQLiteHelper.CONTACT_COLUMN_ID + " = " + keyID, null);
	}
}

