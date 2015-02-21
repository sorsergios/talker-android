package ar.fi.uba.androidtalker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import ar.fi.uba.androidtalker.adapter.PagerAdapter;
import ar.fi.uba.androidtalker.dao.ImagesDao;
import ar.fi.uba.androidtalker.fragment.GridFragment;
import ar.fi.uba.talker.utils.Category;
import ar.fi.uba.talker.utils.GridUtils;

import com.viewpagerindicator.PageIndicator;

public class HistoricalActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageIndicator = (PageIndicator) findViewById(R.id.pagerIndicator);
		ArrayList<Category> a = new ArrayList<Category>();

		Category m = null;
		for (int i = 0; i < ImagesDao.getScenarioSize(); i++) {
			m = new Category();
			m.setName(this.getResources().getString(ImagesDao.getScenarioNameByPos(i)));
			m.setId(ImagesDao.getScenarioImageByPos(i));
			a.add(m);
		}
		
		List<GridFragment> gridFragments = GridUtils.setGridFragments(this, a);

		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), gridFragments);
		viewPager.setAdapter(pagerAdapter);
		pageIndicator.setViewPager(viewPager);
	}


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
}
