package ar.uba.fi.talker.dao;

public class ScenarioDAO {

	private int id;
	private int idCode;
	private String path;
	private String name;

	public ScenarioDAO() {
	}

	public ScenarioDAO(int keyId, int idCode, String path, String name) {
		this.id = keyId;
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
