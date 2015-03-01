package ar.uba.fi.talker.adapter;

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
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dao.ImagesDao;
import ar.uba.fi.talker.utils.ImageUtils;

public class ImageNewInnerSceneAdapter extends BaseAdapter {

	    private Context mContext;
	    private static long itemSelectedId;
	    
	    public ImageNewInnerSceneAdapter(Context c,long position) {
	        mContext = c;
	        ImagesDao.getInstance().setPositionDao(ImageUtils.safeLongToInt(position));
	    }
	    
	    @Override
	    public int getCount() {
	        return ImagesDao.getInstance().getInnerScenarioSize();
	    }

	    @Override
	    public Object getItem(int position) {
	    	View grid = new View(mContext);
	        return (ImageView)grid.findViewById(ImagesDao.getInstance().getInnerScenarioImageByPos(position));
	    }

	    @Override
	    public long getItemId(int position) {
	        return ImagesDao.getInstance().getInnerScenarioImageByPos(position);
	    }

	    @Override
	    public View getView(final int position, View convertView, ViewGroup parent) {
	      View grid;
	      LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	      if (convertView == null) {
	          grid = inflater.inflate(R.layout.row_grid, null);
	          TextView textView = (TextView) grid.findViewById(R.id.text);
	          ImageView imageView = (ImageView)grid.findViewById(R.id.image);
	          textView.setText(mContext.getResources().getString(ImagesDao.getInstance().getInnerScenarioNameByPos(position)));
	          imageView.setImageResource(ImagesDao.getInstance().getInnerScenarioImageByPos(position));
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

		public static long getItemSelectedId() {
			return itemSelectedId;
		}
}
