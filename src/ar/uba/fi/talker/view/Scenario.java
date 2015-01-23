package ar.uba.fi.talker.view;

import java.util.Collection;
import java.util.LinkedHashSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import ar.uba.fi.talker.component.Component;
import ar.uba.fi.talker.component.ComponentFactory;
import ar.uba.fi.talker.component.ComponentType;
import ar.uba.fi.talker.component.command.ActivityCommand;

public class Scenario extends View {

	private Paint paint, erasePaint;

	private Collection<Component> components;
	
	private boolean eraseMode;
	
	private boolean eraseAllMode;

	private ComponentType activeComponentType;

	private Component activeComponent;

	private Point erasePoint;
	
	private Bitmap backgroudImage;
	
	private ActivityCommand command;

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
		paint.setFlags(Paint.LINEAR_TEXT_FLAG);
		paint.setTextSize(200);
		paint.setStrokeWidth(20); // size
		paint.setColor(Color.BLUE); // color

		erasePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		erasePaint.setStyle(Paint.Style.STROKE);
		erasePaint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 0));
		erasePaint.setStrokeWidth(5); // size
		erasePaint.setColor(Color.RED);
		components = new LinkedHashSet<Component>();
		this.setActiveComponentType(ComponentType.PENCIL); 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Component removedComponent = null;
		if (backgroudImage != null){
			canvas.drawBitmap(backgroudImage, 0, 0, paint);
		}
		for (Component component : components) {
			if (eraseMode) {
				component.drawDimension(canvas, erasePaint);
			}
			if (!component.isInDimensions(erasePoint)) {
				component.draw(canvas, paint);
			} else {
				removedComponent = component;
			}
		}
		components.remove(removedComponent);
		erasePoint = null;
	}
	
	@Override
	public boolean performClick() {
		super.performClick();
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.performClick();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (eraseMode) {
				erasePoint = new Point();
				erasePoint.x = (int) event.getAxisValue(MotionEvent.AXIS_X);
				erasePoint.y = (int) event.getAxisValue(MotionEvent.AXIS_Y);
			} else if (!eraseAllMode) {
				activeComponent = ComponentFactory.createComponent(activeComponentType);
				components.add(activeComponent);
			}
		}
		if (!(eraseMode || eraseAllMode)) {
			activeComponent.touchEvent(event, command);
		}
		invalidate();
		return true; 
	}

	private void setCommonModes(ComponentType type) {
		eraseMode = ComponentType.ERASER.equals(type);
		eraseAllMode = ComponentType.ERASE_ALL.equals(type);
	}
	
	public void setActiveComponentType(ComponentType type) {
		setCommonModes(type);
		setActiveComponentType(type, null);
	}

	public void setActiveComponentType(ComponentType type, ActivityCommand command) {
		setCommonModes(type);
		this.activeComponentType = type;
		this.command = command;
	}

	public ComponentType getActiveComponentType() {
		return activeComponentType;
	}

	public void setBackgroundImage(Bitmap previewThumbnail) {
		backgroudImage = previewThumbnail;
	}

	public Collection<Component> getComponents() {
		return components;
	}

	public void setComponents(Collection<Component> components) {
		this.components = components;
	}
	
	public void clear(){
		this.getComponents().clear();
	}

	public void setText(Editable text) {
		activeComponent.setValue(text);
	}

}
