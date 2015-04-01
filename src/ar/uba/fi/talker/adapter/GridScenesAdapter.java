package ar.uba.fi.talker.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.fragment.OutdoorScenarioDialogFragment;
import ar.uba.fi.talker.utils.GridItems;

public class GridScenesAdapter extends BaseAdapter {

	Context context;
	
	public class ViewHolder {
		public ImageView imageView;
		public TextView textTitle;
	}

	private List<GridItems> items;
	private LayoutInflater mInflater;
	private OutdoorScenarioDialogFragment parentFragment;
    private static Long itemSelectedId;
    private static int pos;
    private static int RESULT_LOAD_IMAGE = 1;

	public GridScenesAdapter(Context context, List<GridItems> gridItems, OutdoorScenarioDialogFragment parent) {

		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		items = gridItems;
		parentFragment = parent;
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
			return items.get(position).getCategory().getId();
		}
		return 0;
	}

	public void setItemsList(List<GridItems> locations) {
		this.items = locations;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = convertView;

		ViewHolder viewHolder = new ViewHolder();
		if (view == null) {

			view = mInflater.inflate(R.layout.row_grid, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.imageView = (ImageView) view.findViewById(R.id.image);
			viewHolder.textTitle = (TextView) view.findViewById(R.id.text);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
	
		viewHolder.imageView.setOnClickListener(new OnClickListener() {
		  

		@Override
		  public void onClick(View v) {
				Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
				
				if (position == 5) {
					Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
					parentFragment.startActivityForResult(i, RESULT_LOAD_IMAGE);						
				}
				else{
					ImageButton startScenarioBttn = (ImageButton) ((ActionBarActivity) context).findViewById(R.id.new_scene_start);
					startScenarioBttn.setEnabled(true);
					ImageButton editNameScenarioBttn = (ImageButton) ((ActionBarActivity) context).findViewById(R.id.new_scene_edit_scenario_name);
					editNameScenarioBttn.setEnabled(true);
					v.setSelected(true);
					itemSelectedId = getItemId(position);
					pos = position;
				}					
			}
		});

		GridItems gridItems = items.get(position);
		setCatImage(gridItems.getCategory().getId(), viewHolder, gridItems.getCategory().getName());
		return view;
	}
	public static Long getItemSelectedId() {
		return itemSelectedId;
	}
	public static long getPosition() {
		return pos;
	}
	private void setCatImage(Integer catImage, ViewHolder viewHolder, String catTitle) {
		viewHolder.imageView.setImageResource(catImage);
		viewHolder.textTitle.setText(catTitle);
	}
	public void setParentFragment(OutdoorScenarioDialogFragment outdoorScenarioDialogFragment) {
		this.parentFragment = outdoorScenarioDialogFragment;
	}

	public View setItem(View gridItem,String text){
		TextView textView = (TextView) gridItem.findViewById(R.id.text);
	    textView.setText(text);        
		return gridItem;
	}
}
