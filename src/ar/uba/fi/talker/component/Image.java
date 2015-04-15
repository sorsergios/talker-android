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
	private Paint paint;
	private String label;
	private Bitmap mImage;
	
	public Image(Context arg0) {
		super(arg0);
		paint = PaintManager.getPaint(PaintType.REGULAR);
		// TODO Calcular donde aparece el texto.
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

	public void setContent(Bitmap image) {
		int bWidth = image.getWidth();
        int bHeight = image.getHeight();

        float parentRatio = (float) bHeight / bWidth;

        int nHeight = HEIGHT; 
        int nWidth = (int) (HEIGHT / parentRatio);
        
        mImage =  Bitmap.createScaledBitmap(image, nWidth, nHeight, true);
	}

}
