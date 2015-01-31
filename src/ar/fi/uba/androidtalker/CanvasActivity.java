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
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import ar.fi.uba.androidtalker.InsertImageDialogFragment.InsertImageDialogListener;
import ar.fi.uba.androidtalker.action.userlog.TextDialogFragment;
import ar.fi.uba.androidtalker.action.userlog.TextDialogFragment.TextDialogListener;
import ar.uba.fi.talker.component.ComponentType;
import ar.uba.fi.talker.component.command.ActivityCommand;
import ar.uba.fi.talker.view.Scenario;

public class CanvasActivity extends ActionBarActivity implements
		TextDialogListener, InsertImageDialogListener {

	private static final String BACKGROUND_IMAGE = "imagebitmap";

	final String TAG = "CanvasActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_default);

		final Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);

		if (getIntent().hasExtra("BMP")) {
			Bundle extras = getIntent().getExtras();
			byte[] bytes = extras.getByteArray("BMP");
			Bitmap image = BitmapFactory
					.decodeByteArray(bytes, 0, bytes.length);

			Bitmap transformedImage = reTransformImage(image);
			s.setBackgroundImage(transformedImage);
		}

		if (getIntent().hasExtra(BACKGROUND_IMAGE)) {
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
				s.setActiveComponentType(ComponentType.ERASE_ALL);
				s.clear();
				s.invalidate();
			}
		});

		ImageButton textOp = (ImageButton) findViewById(R.id.textOption);
		textOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				s.setActiveComponentType(ComponentType.TEXT);
				ActivityCommand command = new ActivityCommand() {
					@Override
					public void execute() {
						DialogFragment newFragment = new TextDialogFragment();
						newFragment.show(getSupportFragmentManager(),
								"insert_text");
					}
				};
				s.setActiveComponentType(ComponentType.TEXT, command);
				s.invalidate();
			}
		});

		ImageButton imageOption = (ImageButton) findViewById(R.id.insertImageOption);
		imageOption.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				s.setActiveComponentType(ComponentType.IMAGE);
				DialogFragment newFragment = new InsertImageDialogFragment();
				newFragment.show(getSupportFragmentManager(), "insert_image");
				s.invalidate();
			}
		});

	}

	@Override
	public void onDialogPositiveClickTextDialogListener(DialogFragment dialog) {
		Dialog dialogView = dialog.getDialog();
		EditText inputText = (EditText) dialogView
				.findViewById(R.id.insert_text_input);
		Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
		s.setText(inputText.getText());
		s.invalidate();
	}

	private Bitmap reTransformImage(Bitmap image) {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		Bitmap resized = Bitmap.createScaledBitmap(image, width, height, true);
		int transparency = getResources().getInteger(R.integer.alpha);
		image = makeTransparent(resized, transparency);
		return image;
	}

	public Bitmap makeTransparent(Bitmap src, int value) {
		int width = src.getWidth();
		int height = src.getHeight();
		Bitmap transBitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
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

	@Override
	public void onDialogPositiveClickInsertImageDialogListener(
			DialogFragment dialog) {
		final Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
		final int windowwidth = getWindowManager().getDefaultDisplay()
				.getWidth();
		final int windowheight = getWindowManager().getDefaultDisplay()
				.getHeight();
		System.out.println("width" + windowwidth);
		System.out.println("height" + windowheight);
		RelativeLayout linearLayout = (RelativeLayout) findViewById(R.id.canvas_default);
		final ImageView ima1 = new ImageView(CanvasActivity.this);
		ima1.setImageResource(R.drawable.ic_launcher);

		ima1.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				LayoutParams layoutParams = (RelativeLayout.LayoutParams) ima1
						.getLayoutParams();

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;

				case MotionEvent.ACTION_MOVE:
					int x_cord = (int) event.getRawX();
					int y_cord = (int) event.getRawY();

					System.out.println("value of x" + x_cord);
					System.out.println("value of y" + y_cord);

					if (x_cord > windowwidth) {
						x_cord = windowwidth;
					}
					if (y_cord > windowheight) {
						y_cord = windowheight;
					}
					layoutParams.leftMargin = x_cord - 25;
					layoutParams.topMargin = y_cord - 25;

					ima1.setLayoutParams(layoutParams);
					break;
				default:
					break;
				}
				return true;
			}
		});
		linearLayout.addView(ima1);
		s.setActiveComponentType(ComponentType.IMAGE);
		s.invalidate();

	}

}