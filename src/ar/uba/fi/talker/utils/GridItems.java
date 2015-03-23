package ar.uba.fi.talker.utils;

public class GridItems {

	private int id;
	private ScenarioView scenarioView;

	public GridItems(int id, ScenarioView scenarioView) {
		this.id = id;
		this.scenarioView = scenarioView;
	}

	public ScenarioView getScenarioView() {
		return scenarioView;
	}

	public int getId() {
		return id;
	}
}
