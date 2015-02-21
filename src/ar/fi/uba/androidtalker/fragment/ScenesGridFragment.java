package ar.fi.uba.androidtalker.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import ar.fi.uba.androidtalker.R;
import ar.fi.uba.androidtalker.adapter.GridScenesAdapter;
import ar.fi.uba.talker.utils.GridItems;

public class ScenesGridFragment extends Fragment {

	private GridView mGridView;
	private GridScenesAdapter mGridAdapter;
	GridItems[] gridItems = {};
	private Activity activity;
	private OutdoorScenarioDialogFragment parent;

	public ScenesGridFragment(GridItems[] gridItems, Activity activity, OutdoorScenarioDialogFragment parent) {
		this.gridItems = gridItems;
		this.activity = activity;
		this.parent = parent;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.grid_inside_view_pager, container, false);
		mGridView = (GridView) view.findViewById(R.id.gridView);
		return view;
	}
	
	public void setParent(OutdoorScenarioDialogFragment parent) {
		this.parent = parent;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (activity != null) {

			mGridAdapter = new GridScenesAdapter(activity, gridItems, parent);
			if (mGridView != null) {
				mGridView.setAdapter(mGridAdapter);
			}

			mGridView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					onGridItemClick((GridView) parent, view, position, id);
				}
			});
		}
	}

	public void onGridItemClick(GridView g, View v, int position, long id) {
		Toast.makeText(
				activity,
				"Position Clicked: - " + position + " & " + "Text is: - "
						+ gridItems[position].getCategory().getName(), Toast.LENGTH_LONG).show();
		Log.e("TAG", "POSITION CLICKED " + position);
	}
	
	public GridView getmGridView() {
		return mGridView;
	}
}
