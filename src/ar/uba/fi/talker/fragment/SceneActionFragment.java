package ar.uba.fi.talker.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import ar.uba.fi.talker.ImageSettingsActivity;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.utils.GridItems;

public class SceneActionFragment extends DialogFragment implements OnClickListener {

	private final GridItems gridItem;
	private final View view;
	private final BaseAdapter adapter;
	private OnClickListener onClickStartAction = null;
	private final View actions;

	public SceneActionFragment(GridItems gridItems, View view, BaseAdapter adapter, Context context) {
		this.gridItem = gridItems;
		this.view = view;
		this.adapter = adapter;
		actions = LayoutInflater.from(context).inflate(R.layout.scenario_actions, null);
	}

	public void setOnClickStartAction(OnClickListener onClickStartAction) {
		this.onClickStartAction = onClickStartAction;
	}

	@Override
	public void onDestroy() {
		this.view.setBackgroundColor(Color.WHITE);
		super.onDestroy();
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
//		Window window = getDialog().getWindow();
//
//	    // set "origin" to top left corner, so to speak
//	    window.setGravity(Gravity.TOP|Gravity.LEFT);
//
//	    // after that, setting values for x and y works "naturally"
//	    WindowManager.LayoutParams params = window.getAttributes();
//	    params.x = 300;
//	    params.y = 100;
//	    window.setAttributes(params);
//
//	} 

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View backBttn = actions.findViewById(R.id.sceneActionBack);
		backBttn.setOnClickListener(this);
		View editNameScenarioBttn = actions.findViewById(R.id.new_scene_edit_scenario_name);
		editNameScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new ChangeNameDialogFragment(gridItem.getElementGridView(), adapter);
				newFragment.onAttach(getActivity());
				newFragment.show(getActivity().getSupportFragmentManager(), "insert_text");
				SceneActionFragment.this.dismiss();
			}
		});

		View startScenarioBttn = actions.findViewById(R.id.new_scene_start);
		if (ImageSettingsActivity.class.toString().equals(getActivity().getClass().toString())) {
			ImageSettingsActivity activity = (ImageSettingsActivity) getActivity();
			if (!activity.isContact()) {
				startScenarioBttn.setVisibility(View.GONE);
			} else {
				startScenarioBttn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						ContactInfoFragment newFragment = new ContactInfoFragment();

						Bundle args = new Bundle();
						args.putInt("imageId", (int) gridItem.getElementGridView().getId());
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
		View deleteScenarioBttn = actions.findViewById(R.id.new_scene_delete_scenario_name);
		deleteScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DeleteScenarioConfirmationDialogFragment(gridItem.getElementGridView());
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

	@Override
	public void onStart() {
		// This MUST be called first! Otherwise the view tweaking will not be present in the displayed Dialog (most likely overriden)
		super.onStart();

		//forceWrapContent(actions);
	}

	protected void forceWrapContent(View v) {
		// Start with the provided view
		View current = v;
		// Travel up the tree until fail, modifying the LayoutParams
		do {
			// Get the parent
			ViewParent parent = current.getParent();
			// Check if the parent exists
			if (parent != null) {
				// Get the view
				try {
					current = (View) parent;
				} catch (ClassCastException e) {
					// This will happen when at the top view, it cannot be cast to a View
					break;
				}
				// Modify the layout
				current.getLayoutParams().width = LayoutParams.WRAP_CONTENT;
			}
		} while (current.getParent() != null);
		// Request a layout to be re-done
		current.requestLayout();
	}

}
