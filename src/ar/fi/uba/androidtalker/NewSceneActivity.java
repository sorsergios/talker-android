package ar.fi.uba.androidtalker;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewSceneActivity extends ActionBarActivity {

	private GridView gridView;
	private GridViewAdapter customGridAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scenes);

		gridView = (GridView) findViewById(R.id.gridView);
		customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
		gridView.setAdapter(customGridAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(NewSceneActivity.this, position + "#Selected",
						Toast.LENGTH_SHORT).show();
			}

		});
		Button exitBttn = (Button) findViewById(R.id.new_scene_exit);
		Button innerBttn = (Button) findViewById(R.id.new_scene_inner);
		Button startScenarioBttn = (Button) findViewById(R.id.new_scene_start);
		
		exitBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
			}
		});
		
		innerBttn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						InnerActivity.class);
				startActivity(i);
			}
		});
		
		startScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						CanvasActivity.class);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scenes, menu);
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

	
	private ArrayList<ImageItem> getData() {
		final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();
		// retrieve String drawable array
		TypedArray imgs = getResources().obtainTypedArray(R.array.image_scenario_ids);
		for (int i = 0; i < imgs.length(); i++) {
			Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
					imgs.getResourceId(i, -1));
			imageItems.add(new ImageItem(bitmap, "Image#" + i));
		}
		return imageItems;
	}
}
