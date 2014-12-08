package ar.uba.fi.talker.component;

public class ComponentFactory {

	public static Component createComponent(ComponentType type) {
		try {
			return (Component) Class.forName(type.className()).newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
