/**
 * 
 */
package ar.uba.fi.talker.paint;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * @author sergio
 *
 */
public final class PaintFactory {
	
	public static Paint createPaint(PaintType type) {
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		switch (type) {
			case ERASE:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setStrokeWidth(30); // size
				
				break;
			case TEXT:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setTextSize(100);
				paint.setStrokeWidth(10);
				paint.setColor(Color.BLACK);
				
				break;
			case REGULAR:
			default:
				paint.setStyle(Paint.Style.STROKE);
				paint.setFlags(Paint.LINEAR_TEXT_FLAG);
				paint.setStrokeWidth(20); // size
				paint.setColor(Color.BLUE);
				break;
		}

		return paint;
	}
}
