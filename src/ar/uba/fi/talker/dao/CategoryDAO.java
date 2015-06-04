package ar.uba.fi.talker.dao;

/**
 * @author astamato
 * 
 */
public class CategoryDAO {

	private int id;
	private String name;
	private ImageDAO image;

	public CategoryDAO() {
	}

	public CategoryDAO(int keyId, String name) {
		this.id = keyId;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImage(ImageDAO image) {
		this.image = image;
	}
	
	public ImageDAO getImage() {
		return image;
	}

}
