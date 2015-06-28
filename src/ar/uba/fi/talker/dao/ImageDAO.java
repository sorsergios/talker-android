package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.dto.TalkerDTO;

/**
 * @author astamato
 * 
 */
public class ImageDAO extends TalkerDTO {

	private long idCategory;
	private String path;
	private String name;

	public ImageDAO() {
	}

	public ImageDAO(int id, long idCategory, String path, String name) {
		super();
		this.setId(id);
		this.idCategory = idCategory;
		this.path = path;
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(long idCategory) {
		this.idCategory = idCategory;
	}

}
