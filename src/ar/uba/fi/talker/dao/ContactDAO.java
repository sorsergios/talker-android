package ar.uba.fi.talker.dao;

public class ContactDAO {

	private int id;
	private long imageId;
	private String phone;
	private String address;

	public ContactDAO() {
	}

	public ContactDAO(int id, long imageId, String phone, String address) {
		super();
		this.id = id;
		this.imageId = imageId;
		this.phone = phone;
		this.address = address;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
