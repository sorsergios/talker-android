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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import ar.uba.fi.talker.adapter.GridScenesAdapter;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.ImageDAO;
import ar.uba.fi.talker.dao.ImageTalkerDataSource;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.fragment.ScenesGridFragment;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.ImageUtils;
import ar.uba.fi.talker.utils.ScenarioView;

import com.viewpagerindicator.PageIndicator;

public class ImageSettingsActivity extends FragmentActivity implements DeleteScenarioDialogListener {

    private ImageTalkerDataSource datasource;
	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private GridView gridView = null;
	private PagerScenesAdapter pagerAdapter;
	private int keyId;
	private static int RESULT_LOAD_IMAGE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		keyId= b.getInt("keyId");
		setContentView(R.layout.layout_images);

		ImageButton createCategoryBttn = (ImageButton) this.findViewById(R.id.new_scene_gallery);
		createCategoryBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ScenesGridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		imagesPagerSetting();
	}

	private void imagesPagerSetting() {
		viewPager = (ViewPager) this.findViewById(R.id.pager);
		pageIndicator = (PageIndicator) this.findViewById(R.id.pagerIndicator);
		ArrayList<ScenarioView> thumbnails = new ArrayList<ScenarioView>();

		datasource = new ImageTalkerDataSource(this);
	    datasource.open();
		List<ImageDAO> allImages = datasource.getImagesForCategory(keyId);
		ScenarioView thumbnail = null;
		for (ImageDAO imageDAO : allImages) {
			thumbnail = new ScenarioView();
			thumbnail.setId(imageDAO.getId());
			thumbnail.setName(imageDAO.getName());
			thumbnail.setPath(imageDAO.getPath());
			thumbnails.add(thumbnail);
		}
		List<ScenesGridFragment> gridFragments = GridUtils.setScenesGridFragments(this, thumbnails);

		pagerAdapter = new PagerScenesAdapter(getSupportFragmentManager(), gridFragments);
		viewPager.setAdapter(pagerAdapter);
		pageIndicator.setViewPager(viewPager);
	}

	@Override
	public void onDialogPositiveClickDeleteScenarioDialogListener(
			ScenarioView scenarioView) {
		
		boolean deleted = true;
		if (scenarioView.getPath().contains("/")) {
			File file = new File(scenarioView.getPath());
			deleted = file.delete();
		}
		if (deleted){
			datasource.open();
			datasource.deleteImage(scenarioView.getId());
			datasource.close();
		} else {
			Toast.makeText(this, "Ocurrio un error con la imagen.",	Toast.LENGTH_SHORT).show();
			Log.e("NewScene", "Unexpected error deleting imagen.");
		}
		imagesPagerSetting();
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/* Está configurado para de empezar la conversación directamente y guardar el escenario nuevo en la base */
		byte[] bytes = null;
		ImageDAO imageDAO = null;
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			Uri imageUri = data.getData();
			String imageName = imageUri.getLastPathSegment(); 
	        Bitmap bitmap = null;
			try {/*Entra al if cuando se elige una foto de google +*/
				if (imageUri != null && imageUri.getHost().contains("com.google.android.apps.photos.content")){
					InputStream is = getContentResolver().openInputStream(imageUri);
					bitmap = BitmapFactory.decodeStream(is);
					imageName = imageName.substring(35);
				} else {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
				}
				bytes = ImageUtils.transformImage(bitmap);
				Context ctx = this.getApplicationContext();
				ImageUtils.saveFileInternalStorage(imageName, bitmap, ctx);
				File file = new File(ctx.getFilesDir(), imageName);
				if (datasource == null){
					datasource = new ImageTalkerDataSource(this.getApplicationContext());
				}
				datasource.open();
				imageDAO = datasource.createImage(file.getPath(), imageName, keyId);
				datasource.close();
				GridScenesAdapter gsa = (GridScenesAdapter) gridView.getAdapter();
				ScenarioView scenarioView = new ScenarioView();
				scenarioView.setId(imageDAO.getId());
				scenarioView.setName(imageDAO.getName());
				scenarioView.setPath(imageDAO.getPath());
				GridItems gridItem = new GridItems(imageDAO.getId(), scenarioView);
				gsa.addItem(gridItem);
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

}
