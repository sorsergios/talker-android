package ar.uba.fi.talker.utils;

public class GridItems {

	private int id;
	private Category category;

	public GridItems(int id, Category category) {
		this.id = id;
		this.category = category;
	}

	public Category getCategory() {
		return category;
	}

	public int getId() {
		return id;
	}
}
