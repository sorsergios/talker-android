package ar.uba.fi.talker.listener;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.fragment.SceneActionFragment;
import ar.uba.fi.talker.utils.GridItems;

public class OnClickListenerGridElementSettings implements OnClickListener {

	private final Context context;
	protected GridItems gridItem = null;
	protected BaseAdapter baseAdapter=null;
		
	public OnClickListenerGridElementSettings(final Context context,GridItems gridItem,BaseAdapter baseAdapter){
		this.context=context;
		this.gridItem=gridItem;
		this.baseAdapter = baseAdapter;
	}
	
	@Override
	public void onClick(View view) {
	view.setBackgroundColor(context.getResources().getColor(R.color.selectionViolet));
		
		FragmentActivity activity = (FragmentActivity) context;		
		SceneActionFragment fragment = new SceneActionFragment(gridItem, view, baseAdapter);
		OnClickListener onClickListener = new OnClickStartActionSettings(activity, gridItem, fragment);
		fragment.setOnClickStartAction(onClickListener);
		fragment.onAttach(activity);
		fragment.show(activity.getSupportFragmentManager(), "action-scene");
		
	}

	public GridItems getGridItem() {
		return gridItem;
	}

	public void setGridItem(GridItems gridItem) {
		this.gridItem = gridItem;
	}

}
