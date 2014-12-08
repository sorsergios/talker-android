package ar.uba.fi.talker.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

public abstract class Component {
	
	protected Dimension dimension;
	
	public Component() {
		dimension = new Dimension();
	}

	public boolean isInDimensions(PointF erasePoint) {
		return erasePoint != null 
				&& dimension.x1 <= erasePoint.x 
				&& dimension.y1 <= erasePoint.y 
				&& dimension.x2 >= erasePoint.x 
				&& dimension.y2 >= erasePoint.y;
	}

	public void drawDimension(Canvas canvas, Paint paint) {
		canvas.drawLine(dimension.x1, dimension.y1, dimension.x2, dimension.y1, paint);
		canvas.drawLine(dimension.x2, dimension.y1, dimension.x2, dimension.y2, paint);
		canvas.drawLine(dimension.x2, dimension.y2, dimension.x1, dimension.y2, paint);
		canvas.drawLine(dimension.x1, dimension.y2, dimension.x1, dimension.y1, paint);
	}

	public abstract void draw(Canvas canvas, Paint paint);

	public abstract boolean touchEvent(MotionEvent event);

	class Dimension {
		public float x1 = -1, y1 = -1, x2, y2;
		
		public void evalPoint(Point point) {
			if (x1 == -1 || point.x < x1) {
				x1 = point.x;
			}
			if (point.x > x2) {
				x2 = point.x;
			}
			
			if (y1 == -1 || point.y < y1) {
				y1 = point.y;
			}
			if (point.y > y2) {
				y2 = point.y;
			}
		}
	}
}

