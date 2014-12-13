package ar.fi.uba.androidtalker;

import java.io.ByteArrayOutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class InnerActivity extends ActionBarActivity {

	
	int idSelected;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_inner_scenes);
		

	    final GridView gridViewInner = (GridView) findViewById(R.id.gridViewInner);
	    gridViewInner.setAdapter(new ImageNewInnerSceneAdapter(this));
	    
		Button startBttn = (Button) findViewById(R.id.start_conversation);
		Button exitBttn = (Button) findViewById(R.id.button3);
		
		startBttn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long imageViewId = (Long) ImageNewInnerSceneAdapter.getItemSelectedId();
				byte[] bytes = transformImage(imageViewId); 

				Bundle extras = new Bundle();
				extras.putByteArray("BMP",bytes);
				Intent intent = new Intent(getApplicationContext(), CanvasActivity.class);
				intent.putExtras(extras);
				startActivity(intent);
			}

			private byte[] transformImage(long imageViewId) {
				Bitmap image = BitmapFactory.decodeResource(getResources(),(int) imageViewId);
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				int height = size.y;
		        Bitmap resized = Bitmap.createScaledBitmap(image, width , height, true);
		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
		        byte[] bytes = stream.toByteArray();
				return bytes;
			}
		});
		
		exitBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.scenes, menu);
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
