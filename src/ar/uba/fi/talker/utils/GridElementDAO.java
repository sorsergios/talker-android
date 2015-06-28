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
public class GridElementDAO extends ScenarioDAO {

	private boolean isScenarioElement;

	public GridElementDAO(ScenarioDAO scenarioDAO) {
		super(scenarioDAO.getId(), scenarioDAO.getPath(), scenarioDAO.getName());
		isScenarioElement = true;
	}

	public GridElementDAO(long id, String name, String pathSnapshot) {
		super(id, pathSnapshot, name);
		isScenarioElement = false;
	}

	public GridElementDAO() {
		super();
	}

	public boolean isScenarioElement() {
		return isScenarioElement;
	}

	public void setScenarioElement(boolean isScenarioElement) {
		this.isScenarioElement = isScenarioElement;
	}
}
