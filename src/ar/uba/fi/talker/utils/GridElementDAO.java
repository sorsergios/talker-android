package ar.uba.fi.talker.utils;

import ar.uba.fi.talker.dao.ScenarioDAO;

/**
 * @author emontes
 * 
 * 
 *         Creates the view from a Conversation attributes.
 * 
 * 
 */
public class GridElementDAO {

	private long id;
	private String path;
	private String name;
	private boolean isScenarioElement;

	public GridElementDAO(ScenarioDAO scenarioDAO) {
		this.id = scenarioDAO.getId();
		this.path = scenarioDAO.getPath();
		this.name = scenarioDAO.getName();
		isScenarioElement = true;
	}

	public GridElementDAO() {
	}

	public GridElementDAO(int id, String path, String name, String pathSnapshot) {
		this.id = id;
		this.path = pathSnapshot;
		this.name = name;
		isScenarioElement = false;
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

	public boolean isScenarioElement() {
		return isScenarioElement;
	}

	public void setScenarioElement(boolean isScenarioElement) {
		this.isScenarioElement = isScenarioElement;
	}
}
