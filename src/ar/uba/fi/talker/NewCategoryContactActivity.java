package ar.uba.fi.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import ar.uba.fi.talker.adapter.GridAdapter;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.CategoryDAO;
import ar.uba.fi.talker.dao.ImageDAO;
import ar.uba.fi.talker.dataSource.CategoryTalkerDataSource;
import ar.uba.fi.talker.dataSource.ImageTalkerDataSource;
import ar.uba.fi.talker.fragment.ChangeNameConversationDialogFragment.ChangeNameDialogListener;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.fragment.ScenesGridFragment;
import ar.uba.fi.talker.fragment.TextDialogFragment;
import ar.uba.fi.talker.fragment.TextDialogFragment.TextDialogListener;
import ar.uba.fi.talker.utils.GridConversationItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.GridElementDAO;

import com.viewpagerindicator.PageIndicator;

public class NewCategoryContactActivity extends FragmentActivity implements ChangeNameDialogListener, DeleteScenarioDialogListener, TextDialogListener {

	 	private CategoryTalkerDataSource categoryDatasource;
		private ImageTalkerDataSource imageDatasource;
		//private ContactTalkerDataSource contactDatasource;
		private GridView gridView = null;
		public PageIndicator pageIndicator;
		private ViewPager viewPager;
		private PagerScenesAdapter pagerAdapter;
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
			ArrayList<GridElementDAO> categViews = new ArrayList<GridElementDAO>();
			if (imageDatasource == null ) {
				imageDatasource = new ImageTalkerDataSource(this.getApplicationContext());
			}
			GridElementDAO categView = null;
			if (categoryDatasource == null ) {
				categoryDatasource = new CategoryTalkerDataSource(this);
			}
			List<CategoryDAO> allImages = categoryDatasource.getAll();
			for (int i = 0; i < allImages.size(); i++) {
				CategoryDAO categoryDAO = (CategoryDAO) allImages.get(i);
				categView = new GridElementDAO();
				categView.setId(categoryDAO.getId());
				//da
				categView.setPath(getResources().getString(R.drawable.history_panel));
				categView.setName(categoryDAO.getName());
				categViews.add(categView);
			}
			List<ScenesGridFragment> gridFragments = GridUtils.setScenesGridFragments(this, categViews);

			pagerAdapter = new PagerScenesAdapter(getSupportFragmentManager(), gridFragments);
			viewPager.setAdapter(pagerAdapter);
			pageIndicator.setViewPager(viewPager);
		}
			
		@Override
		public void onDialogPositiveClickChangeNameDialogListener(DialogFragment dialog) {
			Dialog dialogView = dialog.getDialog();
			EditText inputText = (EditText) dialogView.findViewById(R.id.insert_text_input);
			GridAdapter gsa = (GridAdapter) gridView.getAdapter();
			String newCategoryName = inputText.getText().toString();
			((GridConversationItems)gsa.getItem(position)).getConversationDAO().setName(newCategoryName);
			gsa.notifyDataSetInvalidated();
			CategoryDAO category = new CategoryDAO();
			category.setId(GridAdapter.getItemSelectedId());
			category.setName(newCategoryName);
			categoryDatasource.update(category);
		}

		@Override
		public void onDialogPositiveClickDeleteScenarioDialogListener(
				GridElementDAO categoryView) {
			boolean deleted = true;
			List<ImageDAO> innnerImages = imageDatasource.getImagesForCategory(categoryView.getId());
			for (ImageDAO imageDAO : innnerImages) {
				if (imageDAO.getPath().contains("/")) {
					File file = new File(imageDAO.getPath());
					deleted = file.delete();
				}
			}
			if (deleted){
				CategoryDAO categoryDAO = new CategoryDAO();
				categoryDAO.setId(categoryView.getId());
				categoryDatasource.delete(categoryDAO);
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
			categoryDatasource.createCategory(inputText.getText().toString(), 1);
			categoriesPagerSetting();
		}
}
