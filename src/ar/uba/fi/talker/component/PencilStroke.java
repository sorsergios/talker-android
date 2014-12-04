package ar.uba.fi.talker.component;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;

public class PencilStroke extends Component {
	private Paint paint, canvasPaint;
	private Path path;
	
	// canvas
	private Canvas drawCanvas;
	// canvas bitmap
	private Bitmap canvasBitmap;

	
	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(20); // size
		paint.setColor(Color.BLUE); // color

		path = new Path();

		canvasPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	private List<PencilPoint> points;
	
	public void Component() {
		points = new ArrayList<PencilPoint>();
	}
	
	public void draw(Canvas canvas) {

		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);

		Point prevPoint = null;
		path.reset();
		for (PencilPoint point : points) {
			if (point.initial) {
				path.moveTo(point.x, point.y);
				// canvas.drawLine(point.x, point.y, prevPoint.x, prevPoint.y,
				// paint);
			} else {
				// canvas.drawLine(prevPoint.x, prevPoint.y, point.x, point.y,
				// paint);
				path.lineTo(point.x, point.y);
			}
			// canvas.drawPoint(point.x, point.y, paint);

			prevPoint = point;
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
			points.add(point);
			//FIXME call on draw
//			invalidate();
		case MotionEvent.ACTION_UP:
			break;
		}
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
	}
	
}
