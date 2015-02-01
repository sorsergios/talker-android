/**
 * 
 */
package ar.uba.fi.talker.paint;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Paint;

/**
 * @author sergio
 *
 */
public final class PaintManager {

	private Map<PaintType, Paint> paints;
	
	private static PaintManager instance;
	
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
			Paint paint = PaintFactory.createPaint(type);
			getInstance().paints.put(type, paint);
		}
		
		return getInstance().paints.get(type);
	}
}
