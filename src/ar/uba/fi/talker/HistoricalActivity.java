package ar.uba.fi.talker;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import ar.uba.fi.talker.adapter.PagerAdapter;
import ar.uba.fi.talker.dao.ConversationDAO;
import ar.uba.fi.talker.dao.ConversationTalkerDataSource;
import ar.uba.fi.talker.fragment.GridFragment;
import ar.uba.fi.talker.utils.ConversationView;
import ar.uba.fi.talker.utils.GridUtils;

import com.viewpagerindicator.PageIndicator;

public class HistoricalActivity extends ActionBarActivity implements
		ActionBar.TabListener {

    private ConversationTalkerDataSource datasource;
	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		pageIndicator = (PageIndicator) findViewById(R.id.pagerIndicator);
		ArrayList<ConversationView> conversViews = new ArrayList<ConversationView>();

		ConversationView conversView = null;
		datasource = new ConversationTalkerDataSource(this);
	    datasource.open();
		List<ConversationDAO> allImages = datasource.getAllConversations();
		for (int i = 0; i < allImages.size(); i++) {
			ConversationDAO conversationDAO = (ConversationDAO) allImages.get(i);
			conversView = new ConversationView(conversationDAO.getId(), conversationDAO.getPath(), conversationDAO.getName(), conversationDAO.getPathSnapshot());
			conversViews.add(conversView);
		}
		
		List<GridFragment> gridFragments = GridUtils.setGridFragments(this, conversViews);

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
