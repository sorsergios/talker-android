package ar.uba.fi.talker.component;

import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class DateCalendar extends DragComponent {

	private static final int HEIGHT = 200; // TODO Make it a parameter.
	private Paint paint;
	private Bitmap mImage;
	
	public DateCalendar(Context arg0) {
		super(arg0);
		paint = PaintManager.getPaint(PaintType.REGULAR);
		this.setDrawingCacheEnabled(true);
	}

	@Override
	protected Parcelable onSaveInstanceState() {

		Bundle bundle = new Bundle();
	    bundle.putParcelable("instanceState", super.onSaveInstanceState());
	    bundle.putParcelable("image", this.mImage);
	    return bundle;
		
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		 if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
		    mImage = bundle.getParcelable("image");
			
			state = bundle.getParcelable("instanceState");
		}
		super.onRestoreInstanceState(state);
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

	public void setContent(Calendar calendar, int idImage) {
		String dateText = String.format("%1$tA %1$td %1$tB %1$tY", calendar);
		String dayParams[] = dateText.split(" "); 
		String dayOfWeek = dayParams[0];
		String numberDay = dayParams[1];
		String month = dayParams[2];
		String year = dayParams[3];
   		
		mImage = drawTextToBitmap(idImage, dayOfWeek, numberDay, month, year);
	}

	public Bitmap drawTextToBitmap(int resourceId, String dayOfWeek, String numberDay, String month, String year) {
		try {
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), resourceId);
			bitmap =  Bitmap.createScaledBitmap(bitmap, 120, 105, true);
			android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
			// set default bitmap config if none
			if (bitmapConfig == null) {
				bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
			}
			// resource bitmaps are imutable, so we need to convert it to mutable one
			bitmap = bitmap.copy(bitmapConfig, true);

			Canvas canvas = new Canvas(bitmap);

			Paint paintMonth = new Paint();
			paintMonth.setColor(Color.BLACK);
			paintMonth.setTextSize(20);
			int widthMonth = 60 - ((month.length()*9)/2);  
			canvas.drawText(month, widthMonth, 20, paintMonth);
						
			
			Paint paintNumberDay = new Paint();
			paintNumberDay.setColor(Color.RED);
			paintNumberDay.setTextSize(30);
			int widthNumberDay = 60 - ((numberDay.length()*14)/2);
			canvas.drawText(numberDay, widthNumberDay, 50, paintNumberDay);
			
			Paint paintDayOfWeek = new Paint();
			paintDayOfWeek.setColor(Color.BLACK);
			paintDayOfWeek.setTextSize(20);
			int widthDayOfWeek = 60 - ((dayOfWeek.length()*9)/2);
			canvas.drawText(dayOfWeek, widthDayOfWeek, 75, paintDayOfWeek);
			
			Paint paintYear = new Paint();
			paintYear.setColor(Color.BLACK);
			paintYear.setTextSize(20);
			int widthYear = 60 - ((year.length()*9)/2);
			canvas.drawText(year, widthYear, 100, paintYear);
			
			return bitmap;
		} catch (Exception e) {
			return null;
		}

	}
}
