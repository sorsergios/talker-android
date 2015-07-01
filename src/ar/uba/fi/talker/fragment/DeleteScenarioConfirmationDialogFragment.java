package ar.uba.fi.talker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dto.TalkerDTO;

public class DeleteScenarioConfirmationDialogFragment extends ParentDialogFragment implements DialogInterface.OnClickListener {

	public interface DeleteScenarioDialogListener {
		public void onDialogPositiveClickDeleteScenarioDialogListener(
				TalkerDTO scenarioView);
	}

	DeleteScenarioDialogListener listener;
	private TalkerDTO scenarioView;

	public DeleteScenarioConfirmationDialogFragment(TalkerDTO scenarioView) {
		this.scenarioView = scenarioView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (DeleteScenarioDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement DeleteScenarioDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(R.string.delete_scenario_title)
			.setMessage(R.string.delete_scenario_message)
			.setPositiveButton(R.string.delete_scenario_accept, this)
			.setNegativeButton(R.string.delete_scenario_cancel, this);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == AlertDialog.BUTTON_POSITIVE) {
			listener.onDialogPositiveClickDeleteScenarioDialogListener(this.scenarioView);
		}
		dialog.dismiss();
	}

}
