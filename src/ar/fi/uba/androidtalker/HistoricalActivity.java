package ar.fi.uba.androidtalker;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import ar.fi.uba.androidtalker.dao.ImagesDao;
import ar.fi.uba.androidtalker.fragment.GridFragment;
import ar.fi.uba.talker.utils.Category;
import ar.fi.uba.talker.utils.GridItems;

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
		
		Iterator<Category> it = a.iterator();

		List<GridFragment> gridFragments = new ArrayList<GridFragment>();

		int i = 0;
		while (it.hasNext()) {
			ArrayList<GridItems> itmLst = new ArrayList<GridItems>();

			GridItems itm = new GridItems(0, it.next());
			itmLst.add(itm);
			i = i + 1;

			if (it.hasNext()) {
				GridItems itm1 = new GridItems(1, it.next());
				itmLst.add(itm1);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm2 = new GridItems(2, it.next());
				itmLst.add(itm2);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm3 = new GridItems(3, it.next());
				itmLst.add(itm3);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm4 = new GridItems(4, it.next());
				itmLst.add(itm4);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm5 = new GridItems(5, it.next());
				itmLst.add(itm5);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm6 = new GridItems(6, it.next());
				itmLst.add(itm6);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm7 = new GridItems(7, it.next());
				itmLst.add(itm7);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm8 = new GridItems(8, it.next());
				itmLst.add(itm8);
				i = i + 1;
			}

			GridItems[] gp = {};
			GridItems[] gridPage = itmLst.toArray(gp);
			gridFragments.add(new GridFragment(gridPage, HistoricalActivity.this));
		}

		pagerAdapter = new PagerAdapter(getSupportFragmentManager(), gridFragments);
		viewPager.setAdapter(pagerAdapter);
		pageIndicator.setViewPager(viewPager);

	}

	private class PagerAdapter extends FragmentStatePagerAdapter {
		private List<GridFragment> fragments;

		public PagerAdapter(FragmentManager fm, List<GridFragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public GridFragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
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
