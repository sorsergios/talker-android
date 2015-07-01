package ar.uba.fi.talker.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import ar.uba.fi.talker.ImageSettingsActivity;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dataSource.TalkerDataSource;
import ar.uba.fi.talker.utils.GridItems;

public class SceneActionFragment extends DialogFragment implements OnClickListener {

	private GridItems gridItem;
	private View view;
	private BaseAdapter adapter;
	private OnClickListener onClickStartAction = null;
	protected TalkerDataSource dao;

	public void init(GridItems gridItems, View view, BaseAdapter adapter, TalkerDataSource dao) {
		this.gridItem = gridItems;
		this.view = view;
		this.adapter = adapter;
		this.dao = dao;
	}

	public void setOnClickStartAction(OnClickListener onClickStartAction) {
		this.onClickStartAction = onClickStartAction;
	}

	@Override
	public void onDestroy() {
		this.view.setBackgroundColor(Color.WHITE);
		super.onDestroy();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		Dialog builder = new Dialog(getActivity());
		builder.setTitle("USTED ELIGIÓ: " + gridItem.getElementGridView().getName());
		builder.setContentView(R.layout.scenario_actions);

		View backBttn = builder.findViewById(R.id.sceneActionBack);
		backBttn.setOnClickListener(this);
		View editNameScenarioBttn = builder.findViewById(R.id.new_scene_edit_scenario_name);
		editNameScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ChangeNameDialogFragment fragment = new ChangeNameDialogFragment();
				fragment.init(gridItem.getElementGridView(), adapter, dao);
				fragment.onAttach(getActivity());
				fragment.show(getActivity().getSupportFragmentManager(), "insert_text");
				SceneActionFragment.this.dismiss();
			}
		});

		View startScenarioBttn = builder.findViewById(R.id.new_scene_start);
		if (ImageSettingsActivity.class.toString().equals(getActivity().getClass().toString())) {
			ImageSettingsActivity activity = (ImageSettingsActivity)getActivity();
			if (!activity.isContact()){
				startScenarioBttn.setVisibility(View.GONE);
			} else {
				startScenarioBttn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						ContactInfoFragment newFragment = new ContactInfoFragment();
						
						Bundle args = new Bundle();
						args.putInt("imageId", (int)gridItem.getElementGridView().getId());
						newFragment.setArguments(args);
						newFragment.onAttach(getActivity());
						newFragment.show(getActivity().getSupportFragmentManager(), "show_contact_info");
				        SceneActionFragment.this.dismiss();
				        
					}
				});	
			}
		} else {
			startScenarioBttn.setOnClickListener(onClickStartAction);
		}
		View deleteScenarioBttn = builder.findViewById(R.id.new_scene_delete_scenario_name);
		deleteScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DeleteScenarioConfirmationDialogFragment(gridItem.getElementGridView());
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(), "delete_scenario");
				SceneActionFragment.this.dismiss();
			}
		});

		return builder;
	}

	@Override
	public void onClick(View v) {
		this.dismiss();
	}

}
