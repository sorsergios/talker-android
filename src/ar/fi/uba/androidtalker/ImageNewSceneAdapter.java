package ar.fi.uba.androidtalker;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ImageNewSceneAdapter extends BaseAdapter {

	    private Context mContext;
	    private long itemSelectedId;
	    
	    public ImageNewSceneAdapter(Context c) {
	        mContext = c;
	    }

	    public int getCount() {
	        return mThumbIds.length;
	    }

	    public Object getItem(int position) {
	    	View grid = new View(mContext);
	        return (ImageView)grid.findViewById(mThumbIds[position]);
	    }

	    public long getItemId(int position) {
	        return mThumbIds[position];
	    }

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	      // TODO Auto-generated method stub
	      View grid;
	      LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      if (convertView == null) {
	    	  grid = new View(mContext);
	          grid = inflater.inflate(R.layout.row_grid, null);
	          TextView textView = (TextView) grid.findViewById(R.id.text);
	          ImageView imageView = (ImageView)grid.findViewById(R.id.image);
	          textView.setText(mContext.getResources().getString(mTextsIds[position]));
	          imageView.setImageResource(mThumbIds[position]);
	          imageView.setOnClickListener(new OnClickListener() {

			  @Override
			  public void onClick(View v) {

					Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
					Button startScenarioBttn = (Button) ((ActionBarActivity) mContext).findViewById(R.id.new_scene_start);
					startScenarioBttn.setEnabled(true);
					Button innerScenarioBttn = (Button) ((ActionBarActivity) mContext).findViewById(R.id.new_scene_inner);
					innerScenarioBttn.setEnabled(true);
					v.setSelected(true);
					itemSelectedId = getItemId(position);
				}
			});
	      } else {
	    	  grid = (View) convertView;
	      }
	      return grid;
	    }
	    
	    // references to our images
	    private Integer[] mThumbIds = {
	            R.drawable.casa,
	            R.drawable.oficina,
	            R.drawable.colectivo, 
	            R.drawable.escuela,
	            R.drawable.nuevo
	    };
	    
	    private Integer[] mTextsIds = {
	    		R.string.casa,
	    		R.string.oficina,
	    		R.string.colectivo,
	    		R.string.escuela,
	    		R.string.nuevo
	    };

		public long getItemSelectedId() {
			return itemSelectedId;
		}
}
