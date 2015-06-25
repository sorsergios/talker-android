package ar.uba.fi.talker.dataSource;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import ar.uba.fi.talker.dao.ConversationDAO;

public class ConversationTalkerDataSource {

	private SQLiteDatabase database;
	private final ResourceSQLiteHelper dbHelper;
	private final String[] allColumns = { ResourceSQLiteHelper.CONVERSATION_COLUMN_ID,
			ResourceSQLiteHelper.CONVERSATION_COLUMN_PATH,
			ResourceSQLiteHelper.CONVERSATION_COLUMN_NAME, 
			ResourceSQLiteHelper.CONVERSATION_COLUMN_SNAPSHOT };

	public ConversationTalkerDataSource(Context context) {
		dbHelper = new ResourceSQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public ConversationDAO createConversation(String path, String name, String pathImage) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.CONVERSATION_COLUMN_PATH, path);
		values.put(ResourceSQLiteHelper.CONVERSATION_COLUMN_NAME, name);
		values.put(ResourceSQLiteHelper.CONVERSATION_COLUMN_SNAPSHOT, pathImage);
		long insertId = database.insert(ResourceSQLiteHelper.CONVERSATION_TABLE, null, values);
		Cursor cursor = database.query(ResourceSQLiteHelper.CONVERSATION_TABLE, allColumns,
				ResourceSQLiteHelper.CONVERSATION_COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		ConversationDAO newConversation = cursorToConversation(cursor);
		cursor.close();
		return newConversation;
	}

	public void deleteConversation(Long keyID) {
		database.delete(ResourceSQLiteHelper.CONVERSATION_TABLE,
				ResourceSQLiteHelper.CONVERSATION_COLUMN_ID + " = " + keyID, null);
	}

	public List<ConversationDAO> getAllConversations() {
		List<ConversationDAO> conversations = new ArrayList<ConversationDAO>();

		Cursor cursor = database.query(ResourceSQLiteHelper.CONVERSATION_TABLE,
				allColumns, null, null, null, null, ResourceSQLiteHelper.CONVERSATION_COLUMN_ID + " DESC");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ConversationDAO conversationDAO = cursorToConversation(cursor);
			conversations.add(conversationDAO);
			cursor.moveToNext();
		}
		cursor.close();
		return conversations;
	}

	private ConversationDAO cursorToConversation(Cursor cursor) {
		ConversationDAO conversation = new ConversationDAO();
		conversation.setId(cursor.getInt(0));
		conversation.setPath(cursor.getString(1));
		conversation.setName(cursor.getString(2));
		conversation.setPathSnapshot(cursor.getString(3));
		return conversation;
	}
	
	public ConversationDAO getConversationByID(int keyId) {
		Cursor cursor = database.rawQuery("SELECT * FROM "
				+ ResourceSQLiteHelper.CONVERSATION_TABLE + " WHERE "
				+ ResourceSQLiteHelper.CONVERSATION_COLUMN_ID + " = " + keyId, null);
		cursor.moveToFirst();
		ConversationDAO conversationDAO = cursorToConversation(cursor);
		cursor.close();
		return conversationDAO;
	}
	
	public void updateConversation(Long keyID, String name) {
		ContentValues values = new ContentValues();
		values.put(ResourceSQLiteHelper.CONVERSATION_COLUMN_NAME,name);
		database.update(ResourceSQLiteHelper.CONVERSATION_TABLE, values,
				ResourceSQLiteHelper.CONVERSATION_COLUMN_ID + " = " + keyID, null);
	}
}
