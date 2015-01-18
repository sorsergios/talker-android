package ar.uba.fi.talker.component;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class PencilStroke extends Component {
		
	private List<PencilPoint> points;
	private Path path;
	private Paint paint;
	
	public PencilStroke(Context context) {
		super(context);
		points = new ArrayList<PencilPoint>();
		paint = PaintManager.getPaint(PaintType.REGULAR);
		path = new Path();
	}

	@Override
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
