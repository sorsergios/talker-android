package ar.uba.fi.talker.component;

/*
 * List of components for View @link{Scenario}
 */
public enum ComponentType {
	PENCIL("ar.uba.fi.talker.component.PencilStroke"),
	ERASER(null),
	TEXT("ar.uba.fi.talker.component.PencilStroke"),
	CONTACT("ar.uba.fi.talker.component.PencilStroke"),
	IMAGE("ar.uba.fi.talker.component.PencilStroke");

	private String className;
	
	private ComponentType(String className) {
		this.className = className;
	}
	public String className() {
		return className;
	}
}
