package ar.uba.fi.talker.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import ar.uba.fi.talker.component.Component;
import ar.uba.fi.talker.component.ComponentFactory;
import ar.uba.fi.talker.component.ComponentType;
import ar.uba.fi.talker.component.Image;
import ar.uba.fi.talker.component.Text;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Scenario extends FrameLayout {

	private static final String CHILD_LABEL = "Child_";

	private ComponentType activeComponentType;

	private Component activeComponent;

	private List<Component> draggableComponents;

	private Bitmap mImage = null;

	private int viewIdIncremental = new Random().nextInt();

	private Bundle bundle = null;

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
		this.setActiveComponentType(ComponentType.PENCIL);
		draggableComponents = new LinkedList<Component>();
	}

	@Override
	protected Parcelable onSaveInstanceState() {
		int childCount = this.getChildCount();
		if (bundle == null) {
			bundle = new Bundle();
		}
		
		bundle.putParcelable("instanceState", super.onSaveInstanceState());
		if (childCount > 0) {
			bundle.putInt("childCount", childCount);
			
			for (int index = 0; childCount > 0; index++, childCount--) {
				Component child = (Component) this.getChildAt(0);
				this.removeView(child);
				bundle.putParcelable(CHILD_LABEL + index, child);
			}
		}
		
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			int childCount = bundle.getInt("childCount");
			for (int index = 0; index < childCount; index++) {
				Component child = bundle.getParcelable(CHILD_LABEL + index);
				this.addView(child);
			}
			state = bundle.getParcelable("instanceState");
		}
		super.onRestoreInstanceState(state);
	}

	@Override
	public boolean performClick() {
		return super.performClick() || true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			boolean foundElement = false;
			int eventX = (int) event.getAxisValue(MotionEvent.AXIS_X);
			int eventY = (int) event.getAxisValue(MotionEvent.AXIS_Y);
			Point point = new Point(eventX, eventY);

			for (Component child : draggableComponents) {
				if (child.isPointInnerBounds(point)) {
					foundElement = true;
					activeComponent = child;
					activeComponent.toggleActive();
				}
			}

			if (!foundElement) {
				activeComponent = ComponentFactory.createComponent(
						activeComponentType, getContext());

				this.addView(activeComponent,
						android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.MATCH_PARENT);

				activeComponent.setId(viewIdIncremental++);
				activeComponent.setSaveEnabled(true);
				activeComponent.setSaveFromParentEnabled(true);
			}

		}
		activeComponent.onTouchEvent(event);
		return this.performClick();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mImage != null) {
			int width = this.getWidth();
			int height = this.getHeight();
			Bitmap resized = Bitmap.createScaledBitmap(mImage, width, height,
					true);
			BitmapShader fillBMPshader = new BitmapShader(resized,
					Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

			Paint fillPaint = PaintManager.getPaint(PaintType.ERASE);
			fillPaint.setShader(fillBMPshader);
			mImage = null;
		}

		super.onDraw(canvas);
	}

	public void setBackgroundImage(Bitmap image) {
		mImage = image;

		BitmapDrawable background = new BitmapDrawable(getResources(), image);
		this.setBackground(background);
	}

	public void clear() {
		this.removeAllViews();
	}

	public void setActiveComponentType(ComponentType type) {
		this.activeComponentType = type;
	}

	public void setText(Editable text) {
		Component alterComponent = ComponentFactory.createComponent(
				ComponentType.TEXT, getContext());

		android.view.ViewGroup.LayoutParams layoutParams = this
				.getLayoutParams();
		layoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;

		this.addView(alterComponent, layoutParams);
		((Text) alterComponent).setValue(text);
		alterComponent.toggleActive();
		draggableComponents.add(alterComponent);
	}

	public void addImage(Bitmap image) {
		Component alterComponent = ComponentFactory.createComponent(
				ComponentType.IMAGE, getContext());

		android.view.ViewGroup.LayoutParams layoutParams = this
				.getLayoutParams();
		layoutParams.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		layoutParams.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;

		this.addView(alterComponent, layoutParams);

		((Image) alterComponent).setContent(image);
		alterComponent.toggleActive();
		draggableComponents.add(alterComponent);
	}

	public void restore() {
		if (bundle != null) {
			this.onRestoreInstanceState(bundle);
		}
	}

}
