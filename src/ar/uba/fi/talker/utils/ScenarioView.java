package ar.uba.fi.talker.utils;

import ar.uba.fi.talker.dao.ScenarioDAO;

public class ScenarioView {
	
	private long id;
	private String path;
	private String name;
	private boolean scenario;
	
	public ScenarioView(ScenarioDAO scenarioDAO) {
		this.id = scenarioDAO.getId();
		this.path = scenarioDAO.getPath();
		this.name = scenarioDAO.getName();
		scenario = true;
	}

	public ScenarioView() {
	}

	/**
	 * Creates the view from a Conversation attributes.
	 * 
	 * @param id
	 * @param path
	 * @param name
	 * @param pathSnapshot
	 */
	public ScenarioView(int id, String path, String name, String pathSnapshot) {
		this.id = id;
		this.path = pathSnapshot;
		this.name = name;
		scenario = false;
	}

	public long getId() {
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

	public boolean isScenario() {
		return scenario;
	}
}
