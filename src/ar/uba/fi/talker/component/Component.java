package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public abstract class Component extends View {
	
	private boolean active = true;
	private Rect bounds;
	
	public Component(Context context) {
		super(context);
		bounds = new Rect();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		return active && super.dispatchTouchEvent(event);
	}

	protected Rect getBounds() {
		return bounds;
	}
	
	public boolean isPointInnerBounds(Point point) {
		return false;
	}
	
	@Override
	public boolean performClick() {
		return active && super.performClick();
	}
	
	public void toggleActive() {
		active = !active;
		this.bringToFront();
	}

}

