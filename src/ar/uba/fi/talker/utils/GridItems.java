package ar.uba.fi.talker.utils;

public class GridItems {

	private int id;
	private ElementGridView scenarioView;

	public GridItems(int id, ElementGridView scenarioView) {
		this.id = id;
		this.scenarioView = scenarioView;
	}

	public ElementGridView getScenarioView() {
		return scenarioView;
	}

	public int getId() {
		return id;
	}
}
