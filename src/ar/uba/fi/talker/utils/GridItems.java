package ar.uba.fi.talker.utils;

public class GridItems {

	private long id;
	private GridElementDAO elementGridView;

	public GridItems(long id, GridElementDAO elementGridView) {
		this.id = id;
		this.elementGridView = elementGridView;
	}
	
	public GridElementDAO getElementGridView() {
		return elementGridView;
	}
	
	public long getId() {
		return id;
	}
}
