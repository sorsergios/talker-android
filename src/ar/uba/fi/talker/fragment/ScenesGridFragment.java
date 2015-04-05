package ar.uba.fi.talker.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.adapter.GridScenesAdapter;
import ar.uba.fi.talker.utils.GridItems;

public class ScenesGridFragment extends Fragment {

	private GridView mGridView;
	private GridScenesAdapter mGridAdapter;
	List<GridItems> gridItems;
	private Activity activity;

	public ScenesGridFragment() {
		this.gridItems = new ArrayList<GridItems>();
	}

	public ScenesGridFragment(List<GridItems> gridItems, Activity activity) {
		this.gridItems = gridItems;
		this.activity = activity;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.grid_inside_view_pager, container, false);
		mGridView = (GridView) view.findViewById(R.id.gridView);
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int maxImages = (width / 300);
		if (maxImages > 3){
			maxImages = 3;
		} else if (maxImages < 2) {
			maxImages = 2;
		}
		mGridView.setNumColumns(maxImages);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (activity != null) {

			mGridAdapter = new GridScenesAdapter(activity, gridItems);
			if (mGridView != null) {
				mGridView.setAdapter(mGridAdapter);
			}
		}
	}

	public GridView getmGridView() {
		return mGridView;
	}
}
