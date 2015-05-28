package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Image extends DragComponent {

	private static final int HEIGHT = 200; // TODO Make it a parameter.
	private final Paint regPaint;
	private final Paint textPaint;
	private String label;
	private Bitmap mImage;
	
	public Image(Context arg0) {
		super(arg0);
		regPaint = PaintManager.getPaint(PaintType.REGULAR);
		textPaint = PaintManager.getPaint(PaintType.TEXT);
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
			canvas.drawText(label, left, top, textPaint);
		}
		
		if (EraserStroke.enabled) {
			left = left + mImage.getWidth() - (eraseBitmap.getWidth() / 2);
			top = top - (eraseBitmap.getHeight() / 2);
			canvas.drawBitmap(eraseBitmap, left, top, regPaint);
		}
	}
	
	@Override
	public boolean isPointInnerBounds(Point outerPoint) {
		return (mImage != null
				&& point.x < outerPoint.x 
				&& point.y - eraseBitmap.getHeight() < outerPoint.y
				&& point.x + mImage.getWidth() + eraseBitmap.getWidth() > outerPoint.x
				&& point.y + mImage.getHeight() > outerPoint.y
		);
	}

	public boolean isPointInnerEraseBounds(Point outerPoint) {
		return (EraserStroke.enabled
				&& point.x + mImage.getWidth() - (eraseBitmap.getWidth() / 2) < outerPoint.x 
				&& point.y - eraseBitmap.getHeight() / 2 < outerPoint.y
				&& point.x + mImage.getWidth() + (eraseBitmap.getWidth() / 2) > outerPoint.x
				&& point.y + eraseBitmap.getHeight() / 2 > outerPoint.y
		);
	}
	
	public void setContent(Bitmap image, String label) {
		int bWidth = image.getWidth();
        int bHeight = image.getHeight();

        float parentRatio = (float) bHeight / bWidth;

        int nHeight = HEIGHT; 
        int nWidth = (int) (HEIGHT / parentRatio);
        
        mImage =  Bitmap.createScaledBitmap(image, nWidth, nHeight, true);
        this.label = label;
        
	}

}
