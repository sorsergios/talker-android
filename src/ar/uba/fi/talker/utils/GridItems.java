package ar.uba.fi.talker.utils;

public class GridItems {

	private int id;
	private ElementGridView elementGridView;

	public GridItems(int id, ElementGridView elementGridView) {
		this.id = id;
		this.elementGridView = elementGridView;
	}
	
	public ElementGridView getElementGridView() {
		return elementGridView;
	}
	
	public int getId() {
		return id;
	}
}
