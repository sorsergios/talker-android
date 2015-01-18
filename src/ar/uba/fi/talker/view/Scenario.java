package ar.uba.fi.talker.view;

import java.util.Collection;
import java.util.LinkedHashSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Scenario extends View {

	private Collection<Component> components;
	
	private boolean eraseMode;

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
		components = new LinkedHashSet<Component>();
		this.setActiveComponentType(ComponentType.PENCIL); 
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Component removedComponent = null;
		Paint paint = PaintManager.getPaint(PaintType.REGULAR);
		if (backgroudImage != null){
			canvas.drawBitmap(backgroudImage, 0, 0, paint);
		}
		for (Component component : components) {
			if (eraseMode) {
				component.drawDimension(canvas);
			}
			if (!component.isInDimensions(erasePoint)) {
				component.draw(canvas);
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
			} else {
				activeComponent = ComponentFactory.createComponent(activeComponentType, getContext());
				components.add(activeComponent);
				if (command != null) {
					command.execute();
				}
			}
		}

		if (!eraseMode) {
			activeComponent.touchEvent(event);
		}
		invalidate();
		return true; 
	}

	public void setActiveComponentType(ComponentType type) {
		eraseMode = ComponentType.ERASER.equals(type);
		this.activeComponentType = type;
		this.command = null;
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

	public void setActiveComponentType(ComponentType type, ActivityCommand command) {
		eraseMode = ComponentType.ERASER.equals(type);
		this.activeComponentType = type;
		this.command = command;
	}

	public void setText(Editable text) {
		activeComponent.setValue(text);
	}

}
