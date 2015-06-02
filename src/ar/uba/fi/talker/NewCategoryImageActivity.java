package ar.uba.fi.talker;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import ar.uba.fi.talker.adapter.GridAdapter;
import ar.uba.fi.talker.adapter.GridScenesAdapter;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.CategoryDAO;
import ar.uba.fi.talker.dao.CategoryTalkerDataSource;
import ar.uba.fi.talker.dao.ImageDAO;
import ar.uba.fi.talker.dao.ImageTalkerDataSource;
import ar.uba.fi.talker.fragment.ChangeNameConversationDialogFragment.ChangeNameDialogListener;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.fragment.ScenesGridFragment;
import ar.uba.fi.talker.fragment.TextDialogFragment;
import ar.uba.fi.talker.fragment.TextDialogFragment.TextDialogListener;
import ar.uba.fi.talker.utils.GridElementDAO;
import ar.uba.fi.talker.utils.GridConversationItems;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.ImageUtils;
import ar.uba.fi.talker.utils.ResultConstant;

import com.viewpagerindicator.PageIndicator;

public class NewCategoryImageActivity extends FragmentActivity implements DeleteScenarioDialogListener, TextDialogListener, ChangeNameDialogListener{

		private GridView gridView = null;
		private PageIndicator pageIndicator;
		private ViewPager viewPager;
		private PagerScenesAdapter pagerAdapter;
		private CategoryTalkerDataSource categoryDatasource;
		private ImageTalkerDataSource imageDatasource;
		private int position;
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.layout_categories);

			categoriesPagerSetting();
			ImageButton createCategoryBttn = (ImageButton) this.findViewById(R.id.add_image);
			createCategoryBttn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					DialogFragment newFragment = new TextDialogFragment();
					newFragment.show(getSupportFragmentManager(), "insert_text");
					categoriesPagerSetting();
				}
			});
		}

		private void categoriesPagerSetting() {
			viewPager = (ViewPager) this.findViewById(R.id.pager);
			pageIndicator = (PageIndicator) this.findViewById(R.id.pagerIndicator);
			ArrayList<GridElementDAO> thumbnails = new ArrayList<GridElementDAO>();

			if (categoryDatasource == null ) {
				categoryDatasource = new CategoryTalkerDataSource(this.getApplicationContext());
			}
			if (imageDatasource == null ) {
				imageDatasource = new ImageTalkerDataSource(this.getApplicationContext());
			}
		    categoryDatasource.open();
			List<CategoryDAO> allImages = categoryDatasource.getImageCategories();
		    categoryDatasource.close();
		    GridElementDAO thumbnail = null;
			for (CategoryDAO categoryDAO : allImages) {
				thumbnail = new GridElementDAO();
				thumbnail.setId(categoryDAO.getId());
				thumbnail.setName(categoryDAO.getName());
				thumbnail.setPath(getResources().getString(R.drawable.history_panel));
				thumbnails.add(thumbnail);
			}
			List<ScenesGridFragment> gridFragments = GridUtils.setScenesGridFragments(this, thumbnails);

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
			CategoryDAO scenario = null;
			if (requestCode == ResultConstant.RESULT_LOAD_IMAGE && null != data) {
				Uri imageUri = data.getData();
				String categoryName = imageUri.getLastPathSegment(); 
		        Bitmap bitmap = null;
				try {/*Entra al if cuando se elige una foto de google +*/
					if (imageUri != null && imageUri.getHost().contains("com.google.android.apps.photos.content")){
						InputStream is = getContentResolver().openInputStream(imageUri);
						bitmap = BitmapFactory.decodeStream(is);
						categoryName = categoryName.substring(35);
					} else {
						bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
					}
					Context ctx = this.getApplicationContext();
					ImageUtils.saveFileInternalStorage(categoryName, bitmap, ctx);
					categoryDatasource.open();
					scenario = categoryDatasource.createCategory(categoryName, 0);
					categoryDatasource.close();
					GridScenesAdapter gsa = (GridScenesAdapter) gridView.getAdapter();
					GridElementDAO scenarioView = new GridElementDAO();
					scenarioView.setId(scenario.getId());
					scenarioView.setName(scenario.getName());
					scenarioView.setPath(getResources().getString(R.drawable.casa));
					GridItems gridItem = new GridItems(scenario.getId(), scenarioView);
					gsa.addItem(gridItem);
					categoriesPagerSetting();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}		
		}

		@Override
		public void onDialogPositiveClickDeleteScenarioDialogListener(GridElementDAO categoryView) {
			boolean deleted = true;
			imageDatasource.open();
			List<ImageDAO> innnerImages = imageDatasource.getImagesForCategory(categoryView.getId());
			for (ImageDAO imageDAO : innnerImages) {
				if (imageDAO.getPath().contains("/")) {
					File file = new File(imageDAO.getPath());
					deleted = file.delete();
				}
			}
			imageDatasource.close();
			if (deleted){
				categoryDatasource.open();
				categoryDatasource.deleteCategory(categoryView.getId());
				categoryDatasource.close();
			} else {
				Toast.makeText(this, "Ocurrio un error con la imagen.",	Toast.LENGTH_SHORT).show();
				Log.e("NewScene", "Unexpected error deleting imagen.");
			}
			categoriesPagerSetting();
		}
		
		@Override
		public void onDialogPositiveClickTextDialogListener(DialogFragment dialog) {
			Dialog dialogView = dialog.getDialog();
			EditText inputText = (EditText) dialogView
					.findViewById(R.id.insert_text_input);
			if (categoryDatasource == null ) {
				categoryDatasource = new CategoryTalkerDataSource(this);
			}
		    categoryDatasource.open();
			categoryDatasource.createCategory(inputText.getText().toString(), 0);
			categoriesPagerSetting();
		}
		
		@Override
		public void onDialogPositiveClickChangeNameDialogListener(DialogFragment dialog) {
			Dialog dialogView = dialog.getDialog();
			EditText inputText = (EditText) dialogView.findViewById(R.id.insert_text_input);
			GridAdapter gsa = (GridAdapter) gridView.getAdapter();
			String newCategoryName = inputText.getText().toString();
			((GridConversationItems)gsa.getItem(position)).getConversationDAO().setName(newCategoryName);
			gsa.notifyDataSetInvalidated();
			categoryDatasource.updateCategory(GridAdapter.getItemSelectedId(), newCategoryName);
		}

}
