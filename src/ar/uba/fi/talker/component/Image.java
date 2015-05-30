package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Image extends DragComponent {

	private static final int HEIGHT = 200; // TODO Make it a parameter.
	private final Paint regPaint;
	private final Paint labelPaint;
	private String label;
	private Bitmap mImage;

	public Image(Context arg0) {
		super(arg0);
		regPaint = PaintManager.getPaint(PaintType.REGULAR);
		labelPaint = PaintManager.getPaint(PaintType.LABEL);
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		bundle.putParcelable("image", this.mImage);
		bundle.putString("label", this.label);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mImage = bundle.getParcelable("image");
			label = bundle.getString("label");

			state = bundle.getParcelable("instanceState");
		}
		super.onRestoreInstanceState(state);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void onDraw(Canvas canvas) {
		int left = point.x + deltaPoint.x;
		int top = point.y + deltaPoint.y;
		if (mImage != null) {
			canvas.drawBitmap(mImage, left, top, regPaint);
		}
		if (label != null) {
			//no importa qué height le pase, se adapta aunque no le alcance, o aunque le pongas de más
			drawBubble(canvas, left, top + mImage.getHeight(), mImage.getWidth(), 200, label, labelPaint);
		}

		if (EraserStroke.enabled) {
			left = left + mImage.getWidth() - (eraseBitmap.getWidth() / 2);
			top = top - (eraseBitmap.getHeight() / 2);
			canvas.drawBitmap(eraseBitmap, left, top, regPaint);
		}
	}

	@Override
	public boolean isPointInnerBounds(Point outerPoint) {
		return (mImage != null && point.x < outerPoint.x && point.y - eraseBitmap.getHeight() < outerPoint.y
				&& point.x + mImage.getWidth() + eraseBitmap.getWidth() > outerPoint.x && point.y + mImage.getHeight() > outerPoint.y);
	}

	@Override
	public boolean isPointInnerEraseBounds(Point outerPoint) {
		return (EraserStroke.enabled && point.x + mImage.getWidth() - (eraseBitmap.getWidth() / 2) < outerPoint.x
				&& point.y - eraseBitmap.getHeight() / 2 < outerPoint.y
				&& point.x + mImage.getWidth() + (eraseBitmap.getWidth() / 2) > outerPoint.x && point.y + eraseBitmap.getHeight() / 2 > outerPoint.y);
	}

	public void setContent(Bitmap image, String label) {
		int bWidth = image.getWidth();
		int bHeight = image.getHeight();

		float parentRatio = (float) bHeight / bWidth;

		int nHeight = HEIGHT;
		int nWidth = (int) (HEIGHT / parentRatio);

		mImage = Bitmap.createScaledBitmap(image, nWidth, nHeight, true);
		this.label = label;

	}

	/** Draw a text bubble */
	private void drawBubble(final Canvas c, final int x, final int y, final int width, final int height, final String text,
			final Paint paint) {
		final TextInRectangle textRect = new TextInRectangle(paint);

		final int h = textRect.prepare(text, width - 8, height - 8);

		// draw bubble
		final Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setStyle(Paint.Style.FILL);
		p.setAntiAlias(true);

		c.drawRoundRect(new RectF(x, y, x + width, y + h + 8), 4, 4, p);
		textRect.draw(c, x + 4, y + 4);
	}

}
