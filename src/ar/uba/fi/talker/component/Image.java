package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class Image extends Component {
	
	public Image(Context arg0) {
		super(arg0);
	}

	private static String label;

	public static String getLabel() {
		return label;
	}

	public static void setLabel(String label) {
		Image.label = label;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean touchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

}
