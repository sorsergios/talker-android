package ar.fi.uba.androidtalker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import ar.fi.uba.androidtalker.action.userlog.TextDialogFragment.TextDialogListener;

public class InsertImageDialogFragment extends DialogFragment {
	
	
	public interface InsertImageDialogListener {
		public void onDialogPositiveClickInsertImageDialogListener(
				DialogFragment dialog);
	}
	
	InsertImageDialogListener listener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (InsertImageDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement InsertImageDialogListener");
		}
	}

	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(R.string.insert_image_title)
				.setPositiveButton(R.string.insert_image_accept,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								listener.onDialogPositiveClickInsertImageDialogListener(InsertImageDialogFragment.this);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.insert_image_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		return builder.create();
	}
}
