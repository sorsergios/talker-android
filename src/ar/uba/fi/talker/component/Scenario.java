package ar.uba.fi.talker.component;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Scenario extends View {

	private Paint paint, canvasPaint;
	private Path path;
	private List<MyPoint> points;
	
	// canvas
	private Canvas drawCanvas;
	// canvas bitmap
	private Bitmap canvasBitmap;

	public Scenario(Context context) {
		super(context);
		this.init();
	}

	public Scenario(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}

	public Scenario(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
	}

	private void init() {
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(20); // size
		paint.setColor(Color.BLUE); // color
		
		path = new Path();

		points = new ArrayList<MyPoint>();
		
		canvasPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		
		Point prevPoint = null;
		path.reset();
        for (MyPoint point : points) {
        	if (point.initial) {
        		path.moveTo(point.x, point.y);
        	//	canvas.drawLine(point.x, point.y, prevPoint.x, prevPoint.y, paint);
        	} else {
        		//canvas.drawLine(prevPoint.x, prevPoint.y, point.x, point.y, paint);
        		path.lineTo(point.x, point.y);
        	}
        	//canvas.drawPoint(point.x, point.y, paint);
    		
        	prevPoint = point;
        }
        canvas.drawPath(path, paint);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float eventX = event.getAxisValue(MotionEvent.AXIS_X);
		float eventY = event.getAxisValue(MotionEvent.AXIS_Y);

		MyPoint point = new MyPoint();
        switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				point.initial = true;
			case MotionEvent.ACTION_MOVE:
				point.x = (int)eventX;
				point.y = (int)eventY;
				points.add(point);
				invalidate();
			case MotionEvent.ACTION_UP:
				break;
		}
		return true;
	}

	private class MyPoint extends Point {
		
		public boolean initial = false;
	}
}
