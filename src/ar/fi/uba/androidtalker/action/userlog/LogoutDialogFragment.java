package ar.fi.uba.androidtalker.action.userlog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import ar.fi.uba.androidtalker.R;

public class LogoutDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.logout_message)
			.setTitle(R.string.logout_title)
				.setPositiveButton(R.string.logout_accept,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// proceed logout
								// call service
							}
						})
				.setNegativeButton(R.string.logout_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		// Create the AlertDialog object and return it
		return builder.create();
	}

}
