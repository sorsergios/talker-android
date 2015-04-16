/**
 * 
 */
package ar.uba.fi.talker.paint;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Paint;
import ar.uba.fi.talker.component.Setting;

/**
 * @author ssoria
 *
 */
public final class PaintManager {

	private final Map<PaintType, Paint> paints;
	
	private static PaintManager instance;
	
	private static Setting settings;
	
	private PaintManager() {
		paints = new HashMap<PaintType, Paint>();
	}	
	
	protected static PaintManager getInstance() {
		if (instance == null) {
			instance = new PaintManager();
		}
		
		return instance;
	}
	
	public static Paint getPaint(PaintType type) {
		if (!getInstance().paints.containsKey(type)) {
			Paint paint = PaintFactory.createPaint(type, settings);
			getInstance().paints.put(type, paint);
		}
		
		return getInstance().paints.get(type);
	}

	public static Setting getSettings() {
		return settings;
	}

	public static void setSettings(Setting settings) {
		PaintManager.settings = settings;
	}
	
}
