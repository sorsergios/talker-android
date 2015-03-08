package ar.uba.fi.talker.dao;

public class ScenarioDAO {

	int idCode;
	String text;

	public ScenarioDAO() {
	}

	public ScenarioDAO(int keyId, String name) {
		this.idCode = keyId;
		this.text = name;
	}

	public int getID() {
		return this.idCode;
	}

	public void setID(int keyId) {
		this.idCode = keyId;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
