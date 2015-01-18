package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Editable;
import android.view.MotionEvent;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Text extends Component {

	private String value;
	private Point point;
	private Paint paint;

	public Text(Context context) {
		super(context);
		point = new Point();
		paint = PaintManager.getPaint(PaintType.TEXT);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(value, point.x, point.y, paint);

		dimension.evalPoint(point);
		Point secondPoint = new Point(point);
		secondPoint.x += value.length() * 4;
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
			value = "";
		}

		return true;
	}

	@Override
	public void setValue(Editable text) {
		this.value = text.toString();
	}

}