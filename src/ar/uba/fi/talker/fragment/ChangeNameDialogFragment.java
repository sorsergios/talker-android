package ar.uba.fi.talker.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dataSource.ConversationTalkerDataSource;
import ar.uba.fi.talker.dataSource.ScenarioTalkerDataSource;
import ar.uba.fi.talker.utils.GridElementDAO;

public class ChangeNameDialogFragment extends ParentDialogFragment implements DialogInterface.OnClickListener {

	private final GridElementDAO scenarioView;
	private final BaseAdapter adapter;
	private EditText input;
	
	public ChangeNameDialogFragment(GridElementDAO scenarioView, BaseAdapter adapter) {
		this.scenarioView = scenarioView;
		this.adapter = adapter; 
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		input = new EditText(getActivity());
		input.setId(R.id.insert_text_input);
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
		input.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE && v.getText().length() != 0) {
					ChangeNameDialogFragment.this.onClick(getDialog(), AlertDialog.BUTTON_POSITIVE);
					return true;
				}
				return false;
			}
		});
		builder.setView(input)
				.setTitle(R.string.change_conversation_title)
				.setPositiveButton(R.string.delete_conversation_accept, this)
				.setNegativeButton(R.string.insert_text_cancel, this);
		return builder.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which == AlertDialog.BUTTON_POSITIVE && input.getText().length() != 0) {
			String text = input.getText().toString();
			scenarioView.setName(text);
			if (scenarioView.isScenarioElement()) {
				new ScenarioNameChanger().execute(scenarioView);				
			} else {
				new ConversationNameChanger().execute(scenarioView);
			}
			adapter.notifyDataSetChanged();
		}
		dialog.dismiss();
	}

	private class ScenarioNameChanger extends AsyncTask<GridElementDAO, ProgressBar, Boolean> {

		@Override
		protected Boolean doInBackground(GridElementDAO... params) {
			ScenarioTalkerDataSource datasource = new ScenarioTalkerDataSource(ChangeNameDialogFragment.this.getActivity().getApplicationContext());
			GridElementDAO scenarioView = params[0];
			datasource.update(scenarioView);
			return true;
		}
		
	}
	
	private class ConversationNameChanger extends AsyncTask<GridElementDAO, ProgressBar, Boolean> {

		@Override
		protected Boolean doInBackground(GridElementDAO... params) {
			ConversationTalkerDataSource datasource = new ConversationTalkerDataSource(ChangeNameDialogFragment.this.getActivity().getApplicationContext());
			GridElementDAO scenarioView = params[0];
			datasource.open();
			datasource.updateConversation(scenarioView.getId(), scenarioView.getName());
			datasource.close();
			return true;
		}
		
	}
}
