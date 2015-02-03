package ar.uba.fi.talker.component;

/*
 * List of components for View @link{Scenario}
 */
public enum ComponentType {
	PENCIL(PencilStroke.class),
	ERASER(EraserStroke.class),
	TEXT(Text.class),
	CONTACT(Contact.class),
	IMAGE(Image.class);

	private Class<? extends Component> className;
	
	private ComponentType(Class<? extends Component> className) {
		this.className = className;
	}
	public Class<? extends Component> className() {
		return className;
	}
}
