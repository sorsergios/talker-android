package ar.uba.fi.talker.listener;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dataSource.TalkerDataSource;
import ar.uba.fi.talker.fragment.SceneActionFragment;
import ar.uba.fi.talker.utils.GridItems;

public class OnClickListenerGridElement implements OnClickListener {

	private final Context context;
	private final GridItems gridItem;
	private final BaseAdapter baseAdapter;
	private TalkerDataSource dao;
		
	public OnClickListenerGridElement(final Context context,
			GridItems gridItem,
			BaseAdapter baseAdapter,
			TalkerDataSource dao){
		this.context=context;
		this.gridItem = gridItem;
		this.baseAdapter = baseAdapter;
		this.dao = dao;
	}
	
	@Override
	public void onClick(View view) {
		view.setBackgroundColor(context.getResources().getColor(R.color.selectionViolet));
		
		ActionBarActivity activity = (ActionBarActivity) context;
		SceneActionFragment fragment = new SceneActionFragment();
		fragment.init(gridItem, view, baseAdapter, dao);
				
		OnClickListener onClickListener = new OnClickStartActionDefault(activity, gridItem, fragment);
		fragment.setOnClickStartAction(onClickListener);
		fragment.onAttach(activity);
		fragment.show(activity.getSupportFragmentManager(), "action-scene");
		
	}

	public GridItems getGridItem() {
		return gridItem;
	}

}
