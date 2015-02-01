package ar.uba.fi.talker.component;

import android.content.Context;
import ar.uba.fi.talker.paint.PaintManager;
import ar.uba.fi.talker.paint.PaintType;

public class EraserStroke extends PencilStroke {
	
	public EraserStroke(Context context) {
		super(context);
		this.setPaint(PaintManager.getPaint(PaintType.ERASE));
	}

}
