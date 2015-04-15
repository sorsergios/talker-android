package ar.uba.fi.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import ar.uba.fi.talker.adapter.GridAdapter;
import ar.uba.fi.talker.adapter.PagerAdapter;
import ar.uba.fi.talker.dao.ConversationDAO;
import ar.uba.fi.talker.dao.ConversationTalkerDataSource;
import ar.uba.fi.talker.fragment.ChangeNameConversationDialogFragment;
import ar.uba.fi.talker.fragment.ChangeNameConversationDialogFragment.TextDialogListener;
import ar.uba.fi.talker.fragment.DeleteConversationConfirmationDialogFragment;
import ar.uba.fi.talker.fragment.DeleteConversationConfirmationDialogFragment.DeleteConversationDialogListener;
import ar.uba.fi.talker.fragment.GridFragment;
import ar.uba.fi.talker.utils.ConversationView;
import ar.uba.fi.talker.utils.GridConversationItems;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.ImageUtils;

import com.viewpagerindicator.PageIndicator;

public class HistoricalActivity extends ActionBarActivity implements TextDialogListener, DeleteConversationDialogListener {

    private ConversationTalkerDataSource datasource;
	private GridView gridView = null;
	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final HistoricalActivity self = this;
		setContentView(R.layout.layout_hist_conversations);

		conversationPagerSetting();
		
		ImageButton startScenarioBttn = (ImageButton) this.findViewById(R.id.new_scene_start);
		ImageButton editNameConversationBttn = (ImageButton) this.findViewById(R.id.new_scene_edit_scenario_name);
		ImageButton deleteConversationBttn = (ImageButton) this.findViewById(R.id.new_scene_delete_scenario_name);
		
		startScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int imageViewId = GridAdapter.getItemSelectedId().intValue();
				ConversationDAO scenariodao = datasource.getConversationByID(imageViewId);
				byte[] bytes = null;
				
				Context ctx = self.getApplicationContext();
				Bitmap imageBitmap = ImageUtils.getImageBitmap(ctx, scenariodao.getPathSnapshot());
				bytes = ImageUtils.transformImage(imageBitmap);
				Bundle extras = new Bundle();
				extras.putByteArray("BMP",bytes);
				Intent intent = new Intent(self.getApplicationContext(), CanvasActivity.class);
				intent.putExtras(extras);
				startActivity(intent);				
			}
		});
		
		editNameConversationBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				final int numVisibleChildren = gridView.getChildCount();
				final int firstVisiblePosition = gridView.getFirstVisiblePosition();

				int positionIamLookingFor = (int) GridAdapter.getPosition();
				for (int i = 0; i < numVisibleChildren; i++) {
					if ((firstVisiblePosition + i) == positionIamLookingFor) {
						position = i;
					}
				}
				DialogFragment newFragment = new ChangeNameConversationDialogFragment();
				newFragment.onAttach(self);
				newFragment.show(self.getSupportFragmentManager(),"insert_text");
			}
		});
		
		deleteConversationBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				final int numVisibleChildren = gridView.getChildCount();
				final int firstVisiblePosition = gridView.getFirstVisiblePosition();

				int positionIamLookingFor = (int) GridAdapter.getPosition();
				for (int i = 0; i < numVisibleChildren; i++) {
					if ((firstVisiblePosition + i) == positionIamLookingFor) {
						position = i;
					}
				}
				
				DialogFragment newFragment = new DeleteConversationConfirmationDialogFragment();
				newFragment.onAttach(self);
				newFragment.show(self.getSupportFragmentManager(), "delete_scenario");
			}
		});
	
	}

	private void conversationPagerSetting() {
		viewPager = (ViewPager) this.findViewById(R.id.pager);
		pageIndicator = (PageIndicator) this.findViewById(R.id.pagerIndicator);
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
	public void onDialogPositiveClickDeleteConversationDialogListener(DialogFragment dialog) {
		int imageViewId = GridAdapter.getItemSelectedId().intValue();
		ConversationDAO conversationDAO = datasource.getConversationByID(imageViewId);
		boolean deleted = true;
		if (conversationDAO.getPath() != null) {
			File file = new File(conversationDAO.getPath());
			deleted = file.delete();
		}
		if (deleted){
			datasource.deleteConversation(GridAdapter.getItemSelectedId());
		} // TODO informar error al usuario?
		
		GridAdapter gsa = (GridAdapter) gridView.getAdapter();
		gsa.removeItem(position);
		gsa.notifyDataSetInvalidated();
		conversationPagerSetting();
	}
	
	@Override
	public void onDialogPositiveClickTextDialogListener(DialogFragment dialog) {
		Dialog dialogView = dialog.getDialog();
		EditText inputText = (EditText) dialogView.findViewById(R.id.insert_text_input);
		GridAdapter gsa = (GridAdapter) gridView.getAdapter();
		String newScenarioName = inputText.getText().toString();
		((GridConversationItems)gsa.getItem(position)).getConversationView().setName(newScenarioName);
		gsa.notifyDataSetInvalidated();
		datasource.updateConversation(GridAdapter.getItemSelectedId(), newScenarioName);
	}
}
