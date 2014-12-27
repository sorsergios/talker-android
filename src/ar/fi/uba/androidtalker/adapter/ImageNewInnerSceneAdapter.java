package ar.fi.uba.androidtalker.adapter;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

public class ImageNewInnerSceneAdapter extends BaseAdapter {

	    private Context mContext;
	    static long itemSelectedId;
	    
	    public ImageNewInnerSceneAdapter(Context c, long imageViewId) {
	        mContext = c;
	        itemSelectedId = imageViewId;
	    }

	    @Override
	    public int getCount() {
	        return ImagesDao.getInnerScenarioSize();
	    }

	    @Override
	    public Object getItem(int position) {
	    	View grid = new View(mContext);
	        return (ImageView)grid.findViewById(ImagesDao.getInnerScenarioImageByPos(itemSelectedId, position));
	    }

	    @Override
	    public long getItemId(int position) {
	        return ImagesDao.getInnerScenarioImageByPos(itemSelectedId, position);
	    }

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	      // TODO Auto-generated method stub
	      View grid;
	      Log.i("Prueba", "Estoy pidiendo View");
	      LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      if (convertView == null) {
	    	  grid = new View(mContext);
	          grid = inflater.inflate(R.layout.row_grid, null);
	          TextView textView = (TextView) grid.findViewById(R.id.text);
	          ImageView imageView = (ImageView)grid.findViewById(R.id.image);
	          textView.setText(mContext.getResources().getString(ImagesDao.getInnerScenarioNameByPos(itemSelectedId, position)));
	          imageView.setImageResource(ImagesDao.getInnerScenarioImageByPos(itemSelectedId, position));
	          imageView.setOnClickListener(new OnClickListener() {

			  @Override
			  public void onClick(View v) {

					Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
					Button startScenarioBttn = (Button) ((ActionBarActivity) mContext).findViewById(R.id.start_conversation);
					startScenarioBttn.setEnabled(true);
					v.setSelected(true);
					itemSelectedId = getItemId(position);
				}
			});
	      } else {
	    	  grid = (View) convertView;
	      }
	      return grid;
	    }

		public static Object getItemSelectedId() {
			return itemSelectedId;
		}
}
