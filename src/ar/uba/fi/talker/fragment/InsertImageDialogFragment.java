package ar.uba.fi.talker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import ar.uba.fi.talker.R;

public class InsertImageDialogFragment extends DialogFragment {

	private static int RESULT_LOAD_IMAGE = 1;

	public interface InsertImageDialogListener {
		public void onDialogPositiveClickInsertImageDialogListener(Uri uri, Matrix matrix);
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

		// TODO arranca codigo prueba
		Button buttonLoadImage = new Button(getActivity());
		buttonLoadImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});

		// termina codigo prueba
		builder.setView(buttonLoadImage).setTitle(R.string.insert_image_title)
		// .setPositiveButton(R.string.insert_image_accept,
		// new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// listener.onDialogPositiveClickInsertImageDialogListener(InsertImageDialogFragment.this);
		// dialog.dismiss();
		// }
		// })
				.setNegativeButton(R.string.insert_image_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		return builder.create();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
            
			String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
            
			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int orientation = -1;
			if (cursor != null && cursor.moveToFirst()) {
				orientation = cursor.getInt(cursor.getColumnIndex(filePathColumn[1]));
			}  
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);
			cursor.close();

			getDialog().dismiss();
			listener.onDialogPositiveClickInsertImageDialogListener(selectedImage, matrix);
		}

	}

}
