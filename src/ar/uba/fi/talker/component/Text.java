package ar.uba.fi.talker.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class Text extends Component {

	private String value;
	private Point point;

	public Text() {
		point = new Point();
		value = "hola Ale";
	}
	
	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		paint.setFlags(Paint.LINEAR_TEXT_FLAG);
		paint.setStrokeWidth(1); // size
		canvas.drawText(value, point.x, point.y, paint);

		dimension.evalPoint(point);
		Point secondPoint = new Point(point);
		secondPoint.x += value.length()*4;
		secondPoint.y += 4;
		dimension.evalPoint(secondPoint);
		
	}

	@Override
	public boolean touchEvent(MotionEvent event) {
		float eventX = event.getAxisValue(MotionEvent.AXIS_X);
		float eventY = event.getAxisValue(MotionEvent.AXIS_Y);
		
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			point.x = (int) eventX;
			point.y = (int) eventY;
			
		}
		
		return true;
	}
	
}
