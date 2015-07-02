package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.dto.TalkerDTO;

public class ContactDAO extends TalkerDTO {

	private long imageId;
	private String phone;
	private String address;

	public ContactDAO() {
		super();
	}

	public ContactDAO(int id, long imageId, String phone, String address) {
		super();
		this.setId(id);
		this.imageId = imageId;
		this.phone = phone;
		this.address = address;
	}

	public long getImageId() {
		return imageId;
	}

	public void setImageId(long imageId) {
		this.imageId = imageId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
