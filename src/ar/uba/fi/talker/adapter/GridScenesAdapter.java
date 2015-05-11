package ar.uba.fi.talker.adapter;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.fragment.SceneActionFragment;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.ScenarioView;

public class GridScenesAdapter extends BaseAdapter {

	private class ViewHolder {
		public ImageView imageView;
		public TextView textTitle;
	}
	
	private final Context context;
	private List<GridItems> items;
    private static Long itemSelectedId;
    private static int pos;

	public GridScenesAdapter(Context context, List<GridItems> gridItems) {
		this.context = context;
		items = gridItems;
	}

	public List<GridItems> getItems() {
		return items;
	}

	@Override
	public int getCount() {
		if (items != null) {
			return items.size();
		}
		return 0;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if (items != null && position >= 0 && position < getCount()) {
			return items.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		if (items != null && position >= 0 && position < getCount()) {
			return items.get(position).getScenarioView().getId();
		}
		return 0;
	}

	public void setItemsList(List<GridItems> locations) {
		this.items = locations;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		ViewHolder mViewHolder = new ViewHolder();
		final GridItems gridItem = items.get(position);
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.row_grid, parent, false);
			
			mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
			mViewHolder.textTitle = (TextView) convertView.findViewById(R.id.text);

		    convertView.setTag(mViewHolder);
		    convertView.setOnClickListener(new OnClickListener() {
		    	@Override
		    	public void onClick(View view) {
		    		view.setBackgroundColor(context.getResources().getColor(R.color.selectionViolet));
		    		
		    		ActionBarActivity activity = (ActionBarActivity) context;
		    		SceneActionFragment fragment = new SceneActionFragment(gridItem, view, GridScenesAdapter.this);
		    		fragment.onAttach(activity);
		    		fragment.show(activity.getSupportFragmentManager(), "action-scene");
		    	}
		    });
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		
		this.setViewItemContent(mViewHolder, gridItem.getScenarioView());
	
		return convertView;
	}

	private void setViewItemContent(ViewHolder viewHolder, ScenarioView scenarioView) {
		if (scenarioView.getPath().contains("/")){
			Uri uri = Uri.parse(scenarioView.getPath());
			viewHolder.imageView.setImageURI(uri);
		} else {
			int idCode = Integer.valueOf(scenarioView.getPath());
			viewHolder.imageView.setImageResource(idCode);
		}
		viewHolder.textTitle.setText(scenarioView.getName());
	}

	public static Long getItemSelectedId() {
		return itemSelectedId;
	}
	
	public static long getPosition() {
		return pos;
	}
	
	public void setItem(GridItems gridItem,String text, int location){
		gridItem.getScenarioView().setName(text);
	}

	public void removeItem(int location) {
		items.remove(location);
	}

	public void addItem(GridItems gridItem) {
		items.add(gridItem);
	}
}
