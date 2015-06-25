package ar.uba.fi.talker.dataSource;

import java.util.List;

import android.content.Context;
import ar.uba.fi.talker.dto.TalkerDTO;

public abstract class TalkerDataSource<T extends TalkerDTO> {
	
	private ResourceSQLiteHelper dbHelper;
	
	public TalkerDataSource(Context context) {
		this.dbHelper = new ResourceSQLiteHelper(context);
	}

	protected ResourceSQLiteHelper getDbHelper() {
		return dbHelper;
	}
	
	/**
	 * Returns an entity if exists, null otherwise.
	 * 
	 * @param id
	 * @return T entity
	 */
	public abstract T get(long id);
	
	/**
	 * Returns all entities of T.
	 * 
	 * @return List<T>
	 */
	public abstract List<T> getAll();

	/**
	 * Adds new entity.
	 * 
	 * @param object
	 * @return long new id
	 */
	public abstract long add(T entity);

	/**
	 * Updates entity.
	 * 
	 * @param entity
	 */
	public abstract void update(T entity);
	
	/**
	 * Deletes entity.
	 * 
	 * @param entity to delete
	 */
	public abstract void delete(T entity);

}
