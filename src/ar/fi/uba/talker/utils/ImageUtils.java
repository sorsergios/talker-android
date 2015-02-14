package ar.fi.uba.talker.utils;

import java.io.ByteArrayOutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public final class ImageUtils {

	public static byte[] transformImage(Resources res, long imageViewId) {
		Bitmap image = BitmapFactory.decodeResource(res, (int) imageViewId);
		int newHeight = (int) (image.getHeight() * (512.0 / image.getWidth()));
		Bitmap scaled = Bitmap.createScaledBitmap(image, 512, newHeight, true);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		scaled.compress(Bitmap.CompressFormat.PNG, 70, stream);
		byte[] bytes = stream.toByteArray();
		return bytes;
	}
	
	public static byte[] transformImage(Bitmap image) {
		int newHeight = (int) (image.getHeight() * (512.0 / image.getWidth()));
		Bitmap scaled = Bitmap.createScaledBitmap(image, 512, newHeight, true);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		scaled.compress(Bitmap.CompressFormat.PNG, 70, stream);
		byte[] bytes = stream.toByteArray();
		return bytes;
	}
	
	public static int safeLongToInt(long l) {
	    int i = (int)l;
	    if ((long)i != l) {
	        throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
	    }
	    return i;
	}
}
