package ar.uba.fi.talker.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dao.CategoryDAO;

public class InsertImageCategoryAdapter extends BaseAdapter {
	private final Context context;
	private final List<CategoryDAO> categories;
	private final LayoutInflater mInflater;

	public InsertImageCategoryAdapter(Context context, List<CategoryDAO> categories) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.categories = categories;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View gridViewItem;

		if (convertView == null) {
			
			gridViewItem = new View(context);
			// get layout from mobile.xml
			gridViewItem = mInflater.inflate(R.layout.insert_image_grid_item, null);

			String categoryName = categories.get(position).getName();
			
			// set value into textview
			TextView textView = (TextView) gridViewItem.findViewById(R.id.grid_item_label);
			textView.setText(categoryName);

			// set image based on selected text
			ImageView imageView = (ImageView) gridViewItem.findViewById(R.id.grid_item_image);

			//FIXME acá viene la lógica de elegir imagen representativa de la categoría o construir un mosaico del interior de la categoría
			imageView.setImageResource(R.drawable.ic_launcher);
			
		} else {
			gridViewItem = convertView;
		}

		return gridViewItem;
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return categories.get(position).getId();
	}

}