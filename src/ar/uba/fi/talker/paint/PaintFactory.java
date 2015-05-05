/**
 * 
 */
package ar.uba.fi.talker.paint;

import android.graphics.Paint;
import ar.uba.fi.talker.component.Setting;

/**
 * @author ssoria
 *
 */
public final class PaintFactory {
	
	public static Paint createPaint(PaintType type, Setting setting) {
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		switch (type) {
			case ERASE:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setStrokeWidth(setting.getEraserSize()); // size
				break;
			case TEXT:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setTextSize(setting.getTextSize()); //size
				paint.setStrokeWidth(setting.getTextWidth()); //width
				paint.setColor(setting.getTextColor());
				
				break;
			case REGULAR:
			default:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setStrokeWidth(setting.getPencilSize()); // size
				paint.setColor(setting.getPencilColor());
				break;
		}

		return paint;
	}
}
