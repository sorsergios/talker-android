package ar.uba.fi.talker.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.text.Editable;
import android.view.MotionEvent;
import ar.uba.fi.talker.component.command.ActivityCommand;

public abstract class Component {
	
	protected Dimension dimension;
	private static int TOLERANCE = 10;
	
	public Component() {
		dimension = new Dimension();
	}

	public boolean isInDimensions(Point erasePoint) {
		return erasePoint != null 
				&& dimension.x1-Component.TOLERANCE <= erasePoint.x 
				&& dimension.y1-Component.TOLERANCE <= erasePoint.y 
				&& dimension.x2+Component.TOLERANCE >= erasePoint.x 
				&& dimension.y2+Component.TOLERANCE >= erasePoint.y;
	}

	public void drawDimension(Canvas canvas, Paint paint) {
		Path path = new Path();
		path.moveTo(dimension.x1, dimension.y1);
		path.lineTo(dimension.x2, dimension.y1);
		path.lineTo(dimension.x2, dimension.y2);
		path.lineTo(dimension.x1, dimension.y2);
		path.lineTo(dimension.x1, dimension.y1);
		canvas.drawPath(path, paint);
	}

	public abstract void draw(Canvas canvas, Paint paint);

	public abstract boolean touchEvent(MotionEvent event, ActivityCommand command);

	class Dimension {
		public int x1 = -1, y1 = -1, x2, y2;
		
		public void evalPoint(Point point) {
			if (x1 == -1 || point.x < x1) {
				x1 = point.x - Component.TOLERANCE;
			}
			if (point.x > x2) {
				x2 = point.x + Component.TOLERANCE;
			}
			
			if (y1 == -1 || point.y < y1) {
				y1 = point.y - Component.TOLERANCE;
			}
			if (point.y > y2) {
				y2 = point.y + Component.TOLERANCE;
			}
		}
	}

	public void setValue(Editable text) {
		// anything to do;
	}

}

