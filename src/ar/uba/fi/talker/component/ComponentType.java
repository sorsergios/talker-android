package ar.uba.fi.talker.component;

/*
 * List of components for View @link{Scenario}
 */
public enum ComponentType {
	PENCIL(PencilStroke.class.getName()),
	ERASER(null),
	TEXT(Text.class.getName()),
	CONTACT(Contact.class.getName()),
	IMAGE(Image.class.getName()),
	ERASE_ALL(null);
	
	private String className;
	
	private ComponentType(String className) {
		this.className = className;
	}
	public String className() {
		return className;
	}
}
