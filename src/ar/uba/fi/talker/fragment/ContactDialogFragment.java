package ar.uba.fi.talker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import ar.uba.fi.talker.R;

public class ContactDialogFragment extends ParentDialogFragment {

	public interface ContactDialogListener {
		public void onDialogPositiveClickTextDialogListener(
				DialogFragment dialog);

	}

	// Use this instance of the interface to deliver action events
	ContactDialogListener listener;
	private static int RESULT_LOAD_IMAGE = 1;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (ContactDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ContactDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		ImageView imageView = new ImageView(getActivity());
		imageView.setImageResource(R.drawable.image_icon);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		EditText inputPhone = new EditText(getActivity());
		inputPhone.setId(R.id.insert_text_input_phone);
		inputPhone.setHint("Teléfono");
		inputPhone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		inputPhone.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					listener.onDialogPositiveClickTextDialogListener(ContactDialogFragment.this);
					ContactDialogFragment.this.dismiss();
					return true;
				}
				return false;
			}
		});
		EditText inputAddress = new EditText(getActivity());
		inputAddress.setId(R.id.insert_text_input);
		inputAddress.setHint("Dirección");
		inputAddress.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		inputAddress.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					listener.onDialogPositiveClickTextDialogListener(ContactDialogFragment.this);
					ContactDialogFragment.this.dismiss();
					return true;
				}
				return false;
			}
		});

		LinearLayout lila= new LinearLayout(getActivity());
		lila.setOrientation(LinearLayout.VERTICAL);
		lila.addView(imageView);
		lila.addView(inputPhone);
		lila.addView(inputAddress);
		
		builder.setView(lila)
				.setTitle(R.string.insert_contact_title)
				.setPositiveButton(R.string.delete_conversation_accept,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								listener.onDialogPositiveClickTextDialogListener(ContactDialogFragment.this);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.insert_text_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		return builder.create();
	}

}
