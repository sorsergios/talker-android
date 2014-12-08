package ar.uba.fi.talker.component;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;

public class PencilStroke extends Component {
		
	private List<PencilPoint> points;
	
	public PencilStroke() {
		super();
		points = new ArrayList<PencilPoint>();
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		Path path = new Path();
		for (PencilPoint point : points) {
			if (point.initial) {
				path.moveTo(point.x, point.y);
				canvas.drawCircle(point.x, point.y, 3, paint);
			} else {
				path.lineTo(point.x, point.y);
			}
			if (point.end) {
				canvas.drawCircle(point.x, point.y, 3, paint);
			}
		}
		canvas.drawPath(path, paint);
	}
	
	@Override
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
		dimension.evalPoint(point);
		points.add(point);
		return true;
	}

	private class PencilPoint extends Point {
		public boolean initial = false;
		public boolean end = false;
	}
	
}
