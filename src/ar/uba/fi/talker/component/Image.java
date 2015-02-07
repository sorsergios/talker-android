package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Image extends Component {

	private Paint paint;
	private Point point;
	
	public Image(Context arg0) {
		super(arg0);
		paint = PaintManager.getPaint(PaintType.REGULAR);
		point = new Point(0, 0);
	}

	private String label;
	private ImageView mImage;

	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mImage != null) {
			Bitmap bitmap = ((BitmapDrawable) mImage.getDrawable()).getBitmap();
			canvas.drawBitmap(bitmap, point.x, point.y, paint);
		}
	}

	@Override
	public boolean performClick() {
		return super.performClick() || true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("IMAGE", " evento " + event.getAction());
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

	public void setContent(ImageView image) {
		mImage = image;
	}
}
