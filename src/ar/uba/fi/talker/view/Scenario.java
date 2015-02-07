package ar.uba.fi.talker.view;

import java.util.Collection;
import java.util.LinkedHashSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import ar.uba.fi.talker.component.Component;
import ar.uba.fi.talker.component.ComponentFactory;
import ar.uba.fi.talker.component.ComponentType;
import ar.uba.fi.talker.component.command.ActivityCommand;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Scenario extends FrameLayout {

	private Collection<Component> components;

	private ComponentType activeComponentType;

	private Component activeComponent;
	
	private Bitmap mImage = null;

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
	public boolean performClick() {
		super.performClick();
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.performClick();

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			activeComponent = ComponentFactory.createComponent(
					activeComponentType, getContext());
			
			android.view.ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
			
			layoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
			
			this.addView(activeComponent, layoutParams);
			if (command != null) {
				command.execute();
			}
//			this.bringToFront();
			components.add(activeComponent);
		}
		activeComponent.onTouchEvent(event);
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mImage != null) {
			int width = this.getWidth();
			int height = this.getHeight();
			Bitmap resized = Bitmap.createScaledBitmap(mImage, width , height, true);
			BitmapShader fillBMPshader = new BitmapShader(
					resized, 
					Shader.TileMode.REPEAT, 
					Shader.TileMode.REPEAT
					);  
			
			Paint fillPaint = PaintManager.getPaint(PaintType.ERASE);
			fillPaint.setShader(fillBMPshader);
			mImage = null;
		}
		
		super.onDraw(canvas);
	}
	
	public void setBackgroundImage(Bitmap image) {
		mImage = image;
		
        BitmapDrawable background = new BitmapDrawable(getResources(), image);
		this.setBackgroundDrawable(background);
	}

	public Collection<Component> getComponents() {
		return components;
	}

	public void setComponents(Collection<Component> components) {
		this.components = components;
	}

	public void clear() {
		this.getComponents().clear();
		this.removeAllViews();
	}

	public void setActiveComponentType(ComponentType type) {
		this.setActiveComponentType(type, null);
	}
	
	public void setActiveComponentType(ComponentType type, ActivityCommand command) {
		this.activeComponentType = type;
		this.command = command;
	}

	public void setText(Editable text) {
		activeComponent.setValue(text);
	}

}
