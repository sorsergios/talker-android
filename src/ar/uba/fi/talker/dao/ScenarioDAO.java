package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.dto.TalkerDTO;

/**
 * @author earias
 *
 */
public class ScenarioDAO extends TalkerDTO {

	private String path;
	private String name;

	public ScenarioDAO() {
	}

	public ScenarioDAO(long keyId, String path, String name) {
		super();
		setId(keyId);
		this.path = path;
		this.name = name;
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
