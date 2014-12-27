package ar.fi.uba.androidtalker;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import ar.fi.uba.androidtalker.action.userlog.TextDialogFragment;
import ar.fi.uba.androidtalker.action.userlog.TextDialogFragment.TextDialogListener;
import ar.uba.fi.talker.component.ComponentType;
import ar.uba.fi.talker.component.command.ActivityCommand;
import ar.uba.fi.talker.view.Scenario;

public class CanvasActivity extends ActionBarActivity implements TextDialogListener {
		
	private static final String BACKGROUND_IMAGE = "imagebitmap";

	final String TAG = "CanvasActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_default);
		
		if(getIntent().hasExtra("BMP")) {
		    Bundle extras = getIntent().getExtras();
		    byte[] bytes = extras.getByteArray("BMP");	
		    Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		    
		    Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
		    Bitmap transformedImage = reTransformImage(image);
		    s.setBackgroundImage(transformedImage);
		}
	    final Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
	
		if(getIntent().hasExtra(BACKGROUND_IMAGE)) {
		    Bundle extras = getIntent().getExtras();
		    Bitmap image = extras.getParcelable(BACKGROUND_IMAGE);
		    s.setBackgroundImage(image);
		}
	
		ImageButton pencilOp = (ImageButton) findViewById(R.id.pencilOption);
		pencilOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				s.setActiveComponentType(ComponentType.PENCIL);
				s.invalidate();
			}
		});
		
		ImageButton eraserOp = (ImageButton) findViewById(R.id.eraserOption);
		eraserOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				s.setActiveComponentType(ComponentType.ERASER);
				s.invalidate();
			}
		});
		
		ImageButton eraseAllOp = (ImageButton) findViewById(R.id.eraseAllOption);
		eraseAllOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				s.clear();
				s.invalidate();
			}
		});

		ImageButton textOp = (ImageButton) findViewById(R.id.textOption);
		textOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityCommand command = new ActivityCommand() {
					
					@Override
					public void execute() {
						DialogFragment newFragment = new TextDialogFragment();
						newFragment.show(getSupportFragmentManager(), "insert_text");
					}
				};
				s.setActiveComponentType(ComponentType.TEXT, command);
			}
		});

	}
	
	@Override
	public void onDialogPositiveClickTextDialogListener(DialogFragment dialog) {
	    Dialog dialogView = dialog.getDialog();
	    EditText inputText = (EditText) dialogView.findViewById(R.id.ale_capa);
	    Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
	    s.setText(inputText.getText());
	    s.invalidate();
	}
	@Override
	public void onDialogNegativeClickTextDialogListener(DialogFragment dialog) {
		// TODO Auto-generated method stub
	}

	private Bitmap reTransformImage(Bitmap image) {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		Bitmap resized = Bitmap.createScaledBitmap(image, width , height, true);
		int transparency = getResources().getInteger(R.integer.alpha);
		image = makeTransparent(resized, transparency);
		return image;
	}
	
	public Bitmap makeTransparent(Bitmap src, int value) {
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap transBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(transBitmap);
		canvas.drawARGB(0, 0, 0, 0);
		// config paint
		final Paint paint = new Paint();
		paint.setAlpha(value);
		canvas.drawBitmap(src, 0, 0, paint);
		return transBitmap;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}