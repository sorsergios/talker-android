package ar.fi.uba.androidtalker;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import ar.uba.fi.talker.component.ComponentType;
import ar.uba.fi.talker.view.Scenario;

public class CanvasActivity extends ActionBarActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.canvas_default);
		if(getIntent().hasExtra("imagebitmap")) {
		    Bundle extras = getIntent().getExtras();
		    Bitmap image = extras.getParcelable("imagebitmap");
		    Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
		    s.setBackgroundImage(image);
		}
		
		ImageButton pencilOp = (ImageButton) findViewById(R.id.pencilOption);
				
		pencilOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
				s.setActiveComponent(ComponentType.PENCIL);
				s.invalidate();
			}
		});
		
		ImageButton eraserOp = (ImageButton) findViewById(R.id.eraserOption);
		
		eraserOp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Scenario s = (Scenario) findViewById(R.id.gestureOverlayView1);
				s.setActiveComponent(ComponentType.ERASER);
				s.invalidate();
			}
		});
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