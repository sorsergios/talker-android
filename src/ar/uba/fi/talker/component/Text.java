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
		value = "";
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText(value, point.x, point.y, paint);

	}
	
	@Override
	public boolean performClick() {
		return super.performClick() || true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getAxisValue(MotionEvent.AXIS_X);
		float eventY = event.getAxisValue(MotionEvent.AXIS_Y);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_MOVE:
			point.x = (int) eventX;
			point.y = (int) eventY;
			break;
		}
		
		this.invalidate();
		return this.performClick();
	}

	@Override
	public void setValue(Editable text) {
		this.value = text.toString();
		this.invalidate();
	}

}