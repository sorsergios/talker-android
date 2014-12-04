package ar.uba.fi.talker.component;

import android.graphics.Canvas;
import android.view.MotionEvent;

public abstract class Component {
	
	
	public abstract void draw(Canvas canvas);
	
	public abstract boolean touchEvent(MotionEvent event);

}

