package ar.fi.uba.androidtalker;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
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
		
	final String TAG = "CanvasActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_default);
		
		final Scenario scenario = (Scenario) findViewById(R.id.gestureOverlayView1);
		if(getIntent().hasExtra("BMP")) {
		    Bundle extras = getIntent().getExtras();
		    byte[] bytes = extras.getByteArray("BMP");
		    Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		    
		    scenario.invalidate();	
		    scenario.setBackgroundImage(image);
		}
	
		ImageButton pencilOp = (ImageButton) findViewById(R.id.pencilOption);
		pencilOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scenario.setActiveComponentType(ComponentType.PENCIL);
			}
		});
		
		ImageButton eraserOp = (ImageButton) findViewById(R.id.eraserOption);
		eraserOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scenario.setActiveComponentType(ComponentType.ERASER);
			}
		});
		
		ImageButton eraseAllOp = (ImageButton) findViewById(R.id.eraseAllOption);
		eraseAllOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				scenario.clear();
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
				scenario.setActiveComponentType(ComponentType.TEXT, command);
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