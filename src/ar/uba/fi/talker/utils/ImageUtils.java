package ar.uba.fi.talker.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;

public final class ImageUtils {
	
	private static final int HEIGHT = 200; 
	
	public static byte[] transformImage(Resources res, long imageViewId) {
		Bitmap image = BitmapFactory.decodeResource(res, (int) imageViewId);
		BitmapDrawable drawable = new BitmapDrawable(res, image);
		drawable.setAlpha(100);
		image = drawable.getBitmap();
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
	
	public static String convertBitmapToString(Bitmap image) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream .toByteArray();
		String encodedBitmap = Base64.encodeToString(byteArray, Base64.DEFAULT);
		return encodedBitmap;
	}

	public static Bitmap convertStringToBitmap(String encodedBitmap) {
		 byte[] decodedByte = Base64.decode(encodedBitmap, 0);
		 return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
	
	public static void saveFileInternalStorage(String name, Bitmap b, Context context) {

		FileOutputStream out;
		try {
			out = context.openFileOutput(name, Context.MODE_PRIVATE);
			b = setContent(b);
			b.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Bitmap setContent(Bitmap image) {
		int bWidth = image.getWidth();
        int bHeight = image.getHeight();

        float parentRatio = (float) bHeight / bWidth;

        int nHeight = HEIGHT; 
        int nWidth = (int) (HEIGHT / parentRatio);
        
        return Bitmap.createScaledBitmap(image, nWidth, nHeight, true);
	}

	
	public static Bitmap getImageBitmap(Context context, String name) {
		try {
			File f = new File(name);
			Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
