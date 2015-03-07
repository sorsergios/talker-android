package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Image extends Component {

	private static final int HEIGHT = 200; // TODO Make it a parameter.
	private Paint paint;
	
	private Point point;
	private Point deltaPoint;
	private Point downPoint;
	
	public Image(Context arg0) {
		super(arg0);
		paint = PaintManager.getPaint(PaintType.REGULAR);
		point = new Point(0, 0);
		deltaPoint = new Point(0, 0);
		downPoint = new Point(0, 0);
		// TODO Calcular donde aparece el texto.
		point.x = 200; 
		point.y = 200;
	}

	private String label;
	private Bitmap mImage;

	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mImage != null) {
			canvas.drawBitmap(mImage, point.x+deltaPoint.x, point.y+deltaPoint.y, paint);
		}
	}
	
	@Override
	public boolean isPointInnerBounds(Point outerPoint) {
		return (mImage != null
				&& point.x < outerPoint.x 
				&& point.y < outerPoint.y
				&& point.x + mImage.getWidth() > outerPoint.x
				&& point.y + mImage.getHeight() > outerPoint.y
		);
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

	public void setContent(Bitmap image) {
		int bWidth = image.getWidth();
        int bHeight = image.getHeight();

        float parentRatio = (float) bHeight / bWidth;

        int nHeight = HEIGHT; 
        int nWidth = (int) (HEIGHT / parentRatio);

        mImage =  Bitmap.createScaledBitmap(image, nWidth, nHeight, true);
	}
}
