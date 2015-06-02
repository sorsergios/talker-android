package ar.uba.fi.talker.utils;

public class GridItems {

	private int id;
	private GridElementDAO elementGridView;

	public GridItems(int id, GridElementDAO elementGridView) {
		this.id = id;
		this.elementGridView = elementGridView;
	}
	
	public GridElementDAO getElementGridView() {
		return elementGridView;
	}
	
	public int getId() {
		return id;
	}
}
