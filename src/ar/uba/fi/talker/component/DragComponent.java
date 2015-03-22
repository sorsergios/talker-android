package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;

public abstract class DragComponent extends Component {

	private static final int INIT = 200; // TODO Make it a parameter.
	
	protected Point point;
	protected Point deltaPoint;
	protected Point downPoint;
	
	public DragComponent(Context context) {
		super(context);
		
		point = new Point(INIT, INIT);
		deltaPoint = new Point(0, 0);
		downPoint = new Point(0, 0);
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
}
