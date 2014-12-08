package ar.uba.fi.talker.component;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;

public class PencilStroke extends Component {
	private Paint paint;
	private Path path;
		
	private List<PencilPoint> points;
	
	public PencilStroke(Paint paint) {
		this.paint = paint;
		path = new Path();
		points = new ArrayList<PencilPoint>();
	}
	
	public void draw(Canvas canvas) {
		path.reset();
		for (PencilPoint point : points) {
			if (point.initial) {
				path.moveTo(point.x, point.y);
				canvas.drawPoint(point.x, point.y, paint);
			} else {
				path.lineTo(point.x, point.y);
			}
			if (point.end) {
				canvas.drawPoint(point.x, point.y, paint);
			}
		}
		canvas.drawPath(path, paint);
	}
	
	public boolean touchEvent(MotionEvent event) {
		float eventX = event.getAxisValue(MotionEvent.AXIS_X);
		float eventY = event.getAxisValue(MotionEvent.AXIS_Y);
		PencilPoint point = new PencilPoint();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			point.initial = true;
		case MotionEvent.ACTION_MOVE:
			point.x = (int) eventX;
			point.y = (int) eventY;
			break;
		case MotionEvent.ACTION_UP:
			point.end = true;
			point.x = (int) eventX;
			point.y = (int) eventY;
		}
		points.add(point);
		return true;
	}
	
	
	public List<PencilPoint> getPoints() {
		return points;
	}

	public void setPoints(List<PencilPoint> points) {
		this.points = points;
	}
	
	private class PencilPoint extends Point {
		public boolean initial = false;
		public boolean end = false;
	}
	
}
