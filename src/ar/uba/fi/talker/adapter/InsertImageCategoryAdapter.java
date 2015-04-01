package ar.uba.fi.talker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.talker.R;

public class InsertImageCategoryAdapter extends BaseAdapter {
	private Context context;
	private final String[] mobileValues;
	private LayoutInflater mInflater;

	public InsertImageCategoryAdapter(Context context, String[] mobileValues) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.mobileValues = mobileValues;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View gridViewItem;

		if (convertView == null) {
			
			gridViewItem = new View(context);
			// get layout from mobile.xml
			gridViewItem = mInflater.inflate(R.layout.insert_image_grid_item, null);

			// set value into textview
			TextView textView = (TextView) gridViewItem.findViewById(R.id.grid_item_label);
			textView.setText(mobileValues[position]);

			// set image based on selected text
			ImageView imageView = (ImageView) gridViewItem.findViewById(R.id.grid_item_image);

			String mobile = mobileValues[position];

			if (mobile.equals("Windows")) {
				imageView.setImageResource(R.drawable.ic_launcher);
			} else if (mobile.equals("iOS")) {
				imageView.setImageResource(R.drawable.ic_launcher);
			} else if (mobile.equals("Blackberry")) {
				imageView.setImageResource(R.drawable.ic_launcher);
			} else {
				imageView.setImageResource(R.drawable.ic_launcher);
			}

		} else {
			gridViewItem = (View) convertView;
		}

		return gridViewItem;
	}

	@Override
	public int getCount() {
		return mobileValues.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}