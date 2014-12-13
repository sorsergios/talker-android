package ar.uba.fi.talker.component;

import android.util.Log;

public class ComponentFactory {

	public static Component createComponent(ComponentType type) {

		final String TAG = "ComponentFactory";

		try {
			return (Component) Class.forName(type.className()).newInstance();
		} catch (ClassNotFoundException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.e(TAG, e.getMessage(), e);
			e.printStackTrace();
		}
		return null;
	}
}
