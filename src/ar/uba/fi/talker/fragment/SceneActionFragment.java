package ar.uba.fi.talker.fragment;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.listener.OnClickStartActionDefault;
import ar.uba.fi.talker.utils.GridItems;

public class SceneActionFragment extends DialogFragment implements OnClickListener {

	private final GridItems gridItem;
	private final View view;
	private final BaseAdapter adapter;
	private OnClickListener onClickStartAction = null;

	public SceneActionFragment(GridItems gridItems, View view, BaseAdapter adapter) {
		this.gridItem = gridItems;
		this.view = view;
		this.adapter = adapter;
		this.onClickStartAction = new OnClickStartActionDefault(this.getActivity(), gridItem,this);
	}
	
	public void setOnClickStartAction(OnClickListener onClickStartAction){
		this.onClickStartAction = onClickStartAction;
	}
	
	
	@Override
	public void onStart() {
		super.onStart();
		Window window = getDialog().getWindow();
		window.setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	@Override
	public void dismiss() {
		this.view.setBackgroundColor(Color.WHITE);
		super.dismiss();
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View actions = View.inflate(getActivity(), R.layout.scenario_actions, null);

		View backBttn = actions.findViewById(R.id.sceneActionBack);
		backBttn.setOnClickListener(this);
		View editNameScenarioBttn = actions.findViewById(R.id.new_scene_edit_scenario_name);
		editNameScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new ChangeNameDialogFragment(gridItem.getScenarioView(), adapter);
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(), "insert_text");
				SceneActionFragment.this.dismiss();
			}
		});

		View startScenarioBttn = actions.findViewById(R.id.new_scene_start);
		
				
		startScenarioBttn.setOnClickListener(onClickStartAction);
		
		View deleteScenarioBttn = actions.findViewById(R.id.new_scene_delete_scenario_name);
		deleteScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DeleteScenarioConfirmationDialogFragment(gridItem.getScenarioView());
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(), "delete_scenario");
				SceneActionFragment.this.dismiss();
			}
		});
		
		View galleryScenarioBttn = actions.findViewById(R.id.new_scene_gallery);
		galleryScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new InsertImageDialogFragment();
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(), "insert_text");
				SceneActionFragment.this.dismiss();
			}
		});
		galleryScenarioBttn.setVisibility(View.GONE);
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(actions);
		return builder.create();
	}
	
	@Override
	public void onClick(View v) {
		this.dismiss();
	}
	
}
