package ar.uba.fi.talker.component;

public class Contact extends Image {

	private static String name;
	private static String phone;
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		Contact.name = name;
	}
	public static String getPhone() {
		return phone;
	}
	public static void setPhone(String phone) {
		Contact.phone = phone;
	}
	
}
