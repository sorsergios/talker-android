package ar.uba.fi.talker.component;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class Text extends Component{

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public void draw(Canvas canvas, Paint paint) {
		Path path = new Path();
		
		canvas.drawPath(path, paint);
	}

	@Override
	public boolean touchEvent(MotionEvent event) {
		
		
		return true;
	}

}
