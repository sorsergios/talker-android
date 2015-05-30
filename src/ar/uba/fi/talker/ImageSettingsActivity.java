package ar.uba.fi.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.ImageDAO;
import ar.uba.fi.talker.dao.ImageTalkerDataSource;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.fragment.ScenesGridFragment;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.ScenarioView;

import com.viewpagerindicator.PageIndicator;

public class ImageSettingsActivity extends FragmentActivity implements DeleteScenarioDialogListener {

    private ImageTalkerDataSource datasource;
	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerScenesAdapter pagerAdapter;
	private int keyId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		keyId= b.getInt("keyId");
		setContentView(R.layout.layout_images);
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
}
