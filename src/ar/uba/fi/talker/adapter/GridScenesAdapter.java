package ar.uba.fi.talker.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.fragment.OutdoorScenarioFragment;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.ScenarioView;

public class GridScenesAdapter extends BaseAdapter {

	Context context;
	
	public class ViewHolder {
		public ImageView imageView;
		public TextView textTitle;
	}

	private List<GridItems> items;
	private LayoutInflater mInflater;
    private static Long itemSelectedId;
    private static int pos;

	public GridScenesAdapter(Context context, List<GridItems> gridItems, OutdoorScenarioFragment parent) {

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		final View view = convertView != null ? convertView : mInflater.inflate(R.layout.row_grid, parent, false);

		LinearLayout viewHolder = (LinearLayout) view.findViewById(R.id.grid_element);

		viewHolder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageButton startScenarioBttn = (ImageButton) ((ActionBarActivity) context)
						.findViewById(R.id.new_scene_start);
				startScenarioBttn.setEnabled(true);
				startScenarioBttn.setVisibility(View.VISIBLE);
				ImageButton editNameScenarioBttn = (ImageButton) ((ActionBarActivity) context)
						.findViewById(R.id.new_scene_edit_scenario_name);
				editNameScenarioBttn.setEnabled(true);
				editNameScenarioBttn.setVisibility(View.VISIBLE);
				ImageButton deleteScenarioBttn = (ImageButton) ((ActionBarActivity) context)
						.findViewById(R.id.new_scene_delete_scenario_name);
				deleteScenarioBttn.setVisibility(View.VISIBLE);
				for (int i = 0; i < parent.getChildCount(); i++) {
					parent.getChildAt(i).setBackgroundColor(Color.WHITE);	
				}
				view.setBackgroundColor(Color.CYAN);
				itemSelectedId = getItemId(position);
				pos = position;
			}
		});

		GridItems gridItems = items.get(position);
		this.setCatImage(viewHolder, gridItems.getScenarioView());
		return view;
	}

	private void setCatImage(LinearLayout viewHolder, ScenarioView scenarioView) {
		ImageView imageView = (ImageView) viewHolder.findViewById(R.id.image);
		if (scenarioView.getIdCode() != 0){
			imageView.setImageResource(scenarioView.getIdCode());
		} else {
			Uri uri = Uri.parse(scenarioView.getPath());
			imageView.setImageURI(uri);
		}
		TextView textTitle = (TextView) viewHolder.findViewById(R.id.text);
		textTitle .setText(scenarioView.getName());
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
