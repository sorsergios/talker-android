package ar.uba.fi.talker.utils;

public class ScenarioView {
	
	private int id;
	private int idCode;
	private String path;
	private String name;

	public ScenarioView(int id, int idCode, String path, String name) {
		this.id = id;
		this.idCode = idCode;
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
}