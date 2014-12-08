package ar.uba.fi.talker.component;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Scenario extends View {

	private Paint paint;

	private List<Component> components;
	
	private Component activeComponent;

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

		activeComponent = new PencilStroke(paint); 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		activeComponent.draw(canvas);
	}
	
	@Override
	public boolean performClick() {
		super.performClick();
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				this.performClick();
				break;
		}
		invalidate();
		activeComponent.touchEvent(event);			
		return true; 
	}


	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public Component getActiveComponent() {
		return activeComponent;
	}

	public void setActiveComponent(Component activeComponent) {
		this.activeComponent = activeComponent;
	}


}
