/**
 * 
 */
package ar.uba.fi.talker.utils;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

/**
 * @author sergio
 *
 */
public abstract class BackgroundUtil {

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static void setBackground(View view, Drawable background) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			view.setBackgroundDrawable(background);
		} else {
		    view.setBackground(background);
		}
	}
}
