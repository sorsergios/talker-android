package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.Editable;
import android.view.MotionEvent;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Text extends Component {

	private String value;
	private Point point;
	private Point deltaPoint;
	private Point downPoint;
	private Paint paint;

	public Text(Context context) {
		super(context);
		point = new Point();
		deltaPoint = new Point();
		downPoint = new Point();
		// TODO Calcular donde aparece el texto.
		point.x = 200; 
		point.y = 200;
		deltaPoint.x = 0;
		deltaPoint.y = 0;
		paint = PaintManager.getPaint(PaintType.TEXT);
		value = "";
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText(value, point.x+deltaPoint.x, point.y+deltaPoint.y, paint);
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
			downPoint.x = (int) eventX;
			downPoint.y = (int) eventY;
			break;
		case MotionEvent.ACTION_MOVE:
			deltaPoint.x = (int) eventX - downPoint.x;
			deltaPoint.y = (int) eventY - downPoint.y;
			break;
		case MotionEvent.ACTION_UP:
			point.x += deltaPoint.x;
			point.y += deltaPoint.y;
			deltaPoint.x = 0;
			deltaPoint.y = 0;
			this.toggleActive();
			break;
		}
		
		this.invalidate();
		return this.performClick();
	}

	public void setValue(Editable text) {
		this.value = text.toString();
		paint.getTextBounds(this.value, 0, this.value.length(), this.getBounds());
		this.invalidate();
	}

	@Override
	public boolean isPointInnerBounds(Point outerPoint) {
		Rect bounds = this.getBounds();
		
		return (point.x < outerPoint.x 
				&& point.y > outerPoint.y
				&& point.x + bounds.right > outerPoint.x
				&& point.y + bounds.top < outerPoint.y
		);
			
	}
}