package ar.uba.fi.talker.dao;

/**
 * @author earias
 *
 */
public class ScenarioDAO {

	private int id;
	private String path;
	private String name;

	public ScenarioDAO() {
	}

	public ScenarioDAO(int keyId, String path, String name) {
		this.id = keyId;
		this.path = path;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
