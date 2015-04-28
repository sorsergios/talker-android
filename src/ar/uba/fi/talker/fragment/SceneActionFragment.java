package ar.uba.fi.talker.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import ar.uba.fi.talker.CanvasActivity;
import ar.uba.fi.talker.R;

public class SceneActionFragment extends DialogFragment implements OnClickListener {

	private int position;

	public SceneActionFragment(int position) {
		this.position = position;
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
				DialogFragment newFragment = new ChangeNameDialogFragment(position);
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(),"insert_text");
				SceneActionFragment.this.dismiss();
			}
		});

		View startScenarioBttn = actions.findViewById(R.id.new_scene_start);
		startScenarioBttn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Bundle extras = new Bundle();
				extras.putInt("position", position);
				Intent intent = new Intent(getActivity().getApplicationContext(), CanvasActivity.class);
				intent.putExtras(extras);
				startActivity(intent);
				SceneActionFragment.this.dismiss();
			}
		});
		
		View deleteScenarioBttn = actions.findViewById(R.id.new_scene_delete_scenario_name);
		deleteScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				DialogFragment newFragment = new DeleteScenarioConfirmationDialogFragment(position);
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(), "delete_scenario");
				SceneActionFragment.this.dismiss();
			}
		});

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
