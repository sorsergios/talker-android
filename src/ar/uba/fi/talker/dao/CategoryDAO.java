package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.dto.TalkerDTO;

/**
 * @author astamato
 * 
 */
public class CategoryDAO extends TalkerDTO {

	private String name;
	private ImageDAO image;
	private int contactoCategory;

	public CategoryDAO() {
	}

	public CategoryDAO(long keyId, String name) {
		this.setId(keyId);
		this.name = name;
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
	
	public void setContactoCategory(int isContactCategory) {
		this.contactoCategory = isContactCategory;
	}

	public int isContactCategory() {
		return contactoCategory;
	}

}
