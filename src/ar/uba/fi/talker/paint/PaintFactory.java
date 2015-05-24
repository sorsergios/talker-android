/**
 * 
 */
package ar.uba.fi.talker.paint;

import android.graphics.Color;
import android.graphics.Paint;
import ar.uba.fi.talker.component.Setting;

/**
 * @author ssoria
 *
 */
public final class PaintFactory {
	
	public static Paint createPaint(PaintType type) {
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		switch (type) {
			case ERASE:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				break;
			case TEXT:
				paint.setStyle(Paint.Style.FILL_AND_STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setStrokeJoin(Paint.Join.MITER);
				paint.setFakeBoldText(true);
				paint.setShadowLayer(10, 0, 0, Color.DKGRAY);
				
				break;
			case REGULAR:
			default:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				break;
		}

		return paint;
	}

	public static void definePaint(Paint paint, PaintType type, Setting settings) {
		switch (type) {
			case ERASE:
				paint.setStrokeWidth(settings.getEraserSize());
				break;
			case TEXT:
				paint.setTextSize(settings.getTextSize());
				paint.setColor(Color.parseColor(settings.getTextColor()));
				break;
			case REGULAR:
			default:
				paint.setStrokeWidth(settings.getPencilSize());
				paint.setColor(Color.parseColor(settings.getPencilColor()));
				break;
		}
	}
}
