package ar.uba.fi.talker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import ar.uba.fi.talker.adapter.GridScenesAdapter;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.CategoryDAO;
import ar.uba.fi.talker.dao.CategoryTalkerDataSource;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.fragment.ScenesGridFragment;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.ImageUtils;
import ar.uba.fi.talker.utils.ScenarioView;

import com.viewpagerindicator.PageIndicator;

public class NewContactActivity extends ActionBarActivity implements DeleteScenarioDialogListener {

	// Use this instance of the interface to deliver action events
	private static int RESULT_LOAD_IMAGE = 1;
	
	private GridView gridView = null;
	private PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerScenesAdapter pagerAdapter;
	private CategoryTalkerDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final NewContactActivity self = this;
		setContentView(R.layout.layout_ext_scenes);

		scenesPagerSetting();
		ImageButton galleryScenarioBttn = (ImageButton) this.findViewById(R.id.new_scene_gallery);
		galleryScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ScenesGridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				self.startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
	}

	private void scenesPagerSetting() {
		viewPager = (ViewPager) this.findViewById(R.id.pager);
		pageIndicator = (PageIndicator) this.findViewById(R.id.pagerIndicator);
		ArrayList<ScenarioView> scenarios = new ArrayList<ScenarioView>();

		if (datasource == null ) {
			datasource = new CategoryTalkerDataSource(this.getApplicationContext());
		}
	    datasource.open();
		List<CategoryDAO> allImages = datasource.getContactCategories();
	    datasource.close();
	    ScenarioView scenario = null;
		for (CategoryDAO scenarioDAO : allImages) {
			scenario = new ScenarioView();
			scenario.setId(scenarioDAO.getId());
			scenario.setName(scenarioDAO.getName());
			scenario.setPath(getResources().getString(R.drawable.casa));
			scenarios.add(scenario);
		}
		List<ScenesGridFragment> gridFragments = GridUtils.setScenesGridFragments(this, scenarios);

		pagerAdapter = new PagerScenesAdapter(this.getSupportFragmentManager(), gridFragments);
		viewPager.setAdapter(pagerAdapter);
		pageIndicator.setViewPager(viewPager);
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


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/* Está configurado para de empezar la conversación directamente y guardar el escenario nuevo en la base */
		byte[] bytes = null;
		CategoryDAO scenario = null;
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			Uri imageUri = data.getData();
			String scenarioName = imageUri.getLastPathSegment(); 
	        Bitmap bitmap = null;
			try {/*Entra al if cuando se elige una foto de google +*/
				if (imageUri != null && imageUri.getHost().contains("com.google.android.apps.photos.content")){
					InputStream is = getContentResolver().openInputStream(imageUri);
					bitmap = BitmapFactory.decodeStream(is);
					scenarioName = scenarioName.substring(35);
				} else {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
				}
				bytes = ImageUtils.transformImage(bitmap);
				Context ctx = this.getApplicationContext();
				ImageUtils.saveFileInternalStorage(scenarioName, bitmap, ctx);
				File file = new File(ctx.getFilesDir(), scenarioName);
				datasource.open();
				//*FIXME: setear bien el id de categoria*/
				scenario = datasource.createCategory(1, scenarioName);
				datasource.close();
				GridScenesAdapter gsa = (GridScenesAdapter) gridView.getAdapter();
				ScenarioView scenarioView = new ScenarioView();
				scenarioView.setId(scenario.getId());
				scenarioView.setName(scenario.getName());
				scenarioView.setPath(getResources().getString(R.drawable.casa));
				GridItems gridItem = new GridItems(scenario.getId(), scenarioView);
				gsa.addItem(gridItem);
				scenesPagerSetting();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Bundle extras = new Bundle();
			extras.putByteArray("BMP",bytes);
			Intent intent = new Intent(this.getApplicationContext(), CanvasActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
		}		
	}

	@Override
	public void onDialogPositiveClickDeleteScenarioDialogListener(ScenarioView scenarioView) {
		boolean deleted = true;
		if (scenarioView.getPath().contains("/")) {
			File file = new File(scenarioView.getPath());
			deleted = file.delete();
		}
		if (deleted){
			datasource.open();
			datasource.deleteCategory(scenarioView.getId());
			datasource.close();
		} else {
			Toast.makeText(this, "Ocurrio un error con la imagen.",	Toast.LENGTH_SHORT).show();
			Log.e("NewScene", "Unexpected error deleting imagen.");
		}
		scenesPagerSetting();
	}

}
