package ar.uba.fi.talker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import ar.uba.fi.talker.R;

public class TextDialogFragment extends ParentDialogFragment {

	private Integer title;
	
	public interface TextDialogListener {
		public void onDialogPositiveClickTextDialogListener(
				DialogFragment dialog);

	}
	
	TextDialogListener listener;

	public TextDialogFragment() {};
	public TextDialogFragment(Integer title) {
		this.title = title;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (TextDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement TextDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		EditText input = new EditText(getActivity());
		input.setId(R.id.insert_text_input);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		input.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (actionId == EditorInfo.IME_ACTION_DONE) {
					listener.onDialogPositiveClickTextDialogListener(TextDialogFragment.this);
					TextDialogFragment.this.dismiss();
					return true;
				}
				return false;
			}
		});
		builder.setView(input)
				.setTitle(title != null ? title : R.string.insert_text_title)
				.setPositiveButton(R.string.delete_conversation_accept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								listener.onDialogPositiveClickTextDialogListener(TextDialogFragment.this);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.insert_text_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		return builder.create();
	}

}
