package ar.fi.uba.androidtalker.adapter;

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
import ar.fi.uba.androidtalker.R;
import ar.fi.uba.androidtalker.dao.ImagesDao;

public class ImageNewSceneAdapter extends BaseAdapter {

	    private Context mContext;
	    private static long itemSelectedId;
	    private static int pos;
	    
	    public ImageNewSceneAdapter(Context c) {
	        mContext = c;
	    }

	    @Override
	    public int getCount() {
	        return ImagesDao.getScenarioSize();
	    }
	    @Override
	    public Object getItem(int position) {
	    	View grid = new View(mContext);
	    	return (ImageView)grid.findViewById(ImagesDao.getScenarioImageByPos(position));
	    }
	    @Override
	    public long getItemId(int position) {
	    	return ImagesDao.getScenarioImageByPos(position);
	    }

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	      // TODO Auto-generated method stub
	      View grid;
	      LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      if (convertView == null) {
	    	  grid = new View(mContext);
	          grid = inflater.inflate(R.layout.row_grid, parent, false);
	          TextView textView = (TextView) grid.findViewById(R.id.text);
	          ImageView imageView = (ImageView)grid.findViewById(R.id.image);
	          textView.setText(mContext.getResources().getString(ImagesDao.getScenarioNameByPos(position)));
	          imageView.setImageResource(ImagesDao.getScenarioImageByPos(position));
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
					pos = position;
				}
			});
	      } else {
	    	  grid = (View) convertView;
	      }
	      return grid;
	    }
	
		public static long getItemSelectedId() {
			return itemSelectedId;
		}
		public static long getPosition() {
			return pos;
		}
}
