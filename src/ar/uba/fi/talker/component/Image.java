package ar.uba.fi.talker.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import ar.uba.fi.talker.component.command.ActivityCommand;

public class Image extends Component {

	private static String label;

	public static String getLabel() {
		return label;
	}

	public static void setLabel(String label) {
		Image.label = label;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean touchEvent(MotionEvent event, ActivityCommand command) {
//		float eventX = event.getAxisValue(MotionEvent.AXIS_X);
//		float eventY = event.getAxisValue(MotionEvent.AXIS_Y);
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			break;
//
//		case MotionEvent.ACTION_MOVE:
//			float x_cord = (float) event.getRawX();
//			float y_cord = (float) event.getRawY();
//
//			if (x_cord > eventX) {
//				x_cord = eventX;
//			}
//			if (y_cord > eventY) {
//				y_cord = eventY;
//			}
//			break;
//		default:
//			break;
//		}
		return true;
	}

}
