package ar.uba.fi.talker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import ar.uba.fi.talker.adapter.GridAdapter;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.ConversationDAO;
import ar.uba.fi.talker.dataSource.ConversationTalkerDataSource;
import ar.uba.fi.talker.fragment.ChangeNameConversationDialogFragment.ChangeNameDialogListener;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.fragment.ScenesGridFragment;
import ar.uba.fi.talker.utils.GridConversationItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.GridElementDAO;

import com.viewpagerindicator.PageIndicator;

public class HistoricalActivity extends ActionBarActivity implements ChangeNameDialogListener, DeleteScenarioDialogListener {

    private ConversationTalkerDataSource datasource;
	private GridView gridView = null;
	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerScenesAdapter pagerAdapter;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_hist_conversations);
		conversationPagerSetting();
	}

	private void conversationPagerSetting() {
		viewPager = (ViewPager) this.findViewById(R.id.pager);
		pageIndicator = (PageIndicator) this.findViewById(R.id.pagerIndicator);
		ArrayList<GridElementDAO> conversViews = new ArrayList<GridElementDAO>();

		GridElementDAO conversView = null;
		datasource = new ConversationTalkerDataSource(this);
		List<ConversationDAO> allImages = datasource.getAll();
		for (int i = 0; i < allImages.size(); i++) {
			ConversationDAO conversationDAO = (ConversationDAO) allImages.get(i);
			conversView = new GridElementDAO(conversationDAO.getId(), conversationDAO.getName(), conversationDAO.getPathSnapshot());
			conversViews.add(conversView);
		}
		List<ScenesGridFragment> gridFragments = GridUtils.setScenesGridFragments(this, conversViews);

		pagerAdapter = new PagerScenesAdapter(getSupportFragmentManager(), gridFragments);
		viewPager.setAdapter(pagerAdapter);
		pageIndicator.setViewPager(viewPager);
	}
		
	@Override
	public void onDialogPositiveClickChangeNameDialogListener(DialogFragment dialog) {
		Dialog dialogView = dialog.getDialog();
		EditText inputText = (EditText) dialogView.findViewById(R.id.insert_text_input);
		GridAdapter gsa = (GridAdapter) gridView.getAdapter();
		String newName = inputText.getText().toString();
		((GridConversationItems)gsa.getItem(position)).getConversationDAO().setName(newName);
		gsa.notifyDataSetInvalidated();
		
		ConversationDAO conversation = new ConversationDAO(GridAdapter.getItemSelectedId(), null, newName, null); 
		datasource.update(conversation);
	}

	@Override
	public void onDialogPositiveClickDeleteScenarioDialogListener(
			GridElementDAO scenarioView) {
		
		boolean deleted = true;
		if (scenarioView.getPath().contains("/")) {
			File file = new File(scenarioView.getPath());
			deleted = file.delete();
		}
		if (deleted){
			ConversationDAO conversation = new ConversationDAO();
			conversation.setId(scenarioView.getId()); 
			datasource.delete(conversation);
		} else {
			Toast.makeText(this, "Ocurrio un error con la imagen.",	Toast.LENGTH_SHORT).show();
			Log.e("NewScene", "Unexpected error deleting imagen.");
		}
		conversationPagerSetting();
	}
}
