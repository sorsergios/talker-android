package ar.uba.fi.talker.dao;

/**
 * @author astamato
 * 
 */
public class ImageDAO {

	private int id;
	private int idCode;
	private int idCategory;
	private String path;
	private String name;

	public ImageDAO() {
	}

	public ImageDAO(int id, int idCode, int idCategory, String path, String name) {
		super();
		this.id = id;
		this.idCode = idCode;
		this.idCategory = idCategory;
		this.path = path;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCode() {
		return idCode;
	}

	public void setIdCode(int idCode) {
		this.idCode = idCode;
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

	public int getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}

}
