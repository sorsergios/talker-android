package ar.uba.fi.talker.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.Editable;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class Text extends DragComponent {

	private Paint paint;
	private String value;

	public Text(Context context) {
		super(context);
		paint = PaintManager.getPaint(PaintType.TEXT);
		value = "";
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawText(value, point.x+deltaPoint.x, point.y+deltaPoint.y, paint);
	}
	
	public void setValue(Editable text) {
		this.value = text.toString();
		paint.getTextBounds(this.value, 0, this.value.length(), this.getBounds());
		this.invalidate();
	}

	@Override
	public boolean isPointInnerBounds(Point outerPoint) {
		Rect bounds = this.getBounds();
		
		return (point.x < outerPoint.x 
				&& point.y > outerPoint.y
				&& point.x + bounds.right > outerPoint.x
				&& point.y + bounds.top < outerPoint.y
		);
			
	}
}