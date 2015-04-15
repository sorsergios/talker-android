package ar.uba.fi.talker;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import ar.uba.fi.talker.dao.ConversationDAO;
import ar.uba.fi.talker.dao.ConversationTalkerDataSource;
import ar.uba.fi.talker.fragment.ExitApplicationConfirmationDialogFragment;
import ar.uba.fi.talker.fragment.ExitApplicationConfirmationDialogFragment.ExitAplicationDialogListener;

public class TalkerMainActivity extends ActionBarActivity implements ExitAplicationDialogListener {

    private static Context context;
	final TalkerMainActivity self = this;

    public static Context getAppContext() {
        return TalkerMainActivity.context;
    }
	
    private void init(){
		Button historicalBttn = (Button) findViewById(R.id.history_panel_button);
    	evaluateVisibilityButton(historicalBttn);
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_talker_main);
		
		TalkerMainActivity.context = getApplicationContext();

		
		Button exitBttn = (Button) findViewById(R.id.exit_app_button);
		Button settingsBttn = (Button) findViewById(R.id.action_settings_button);
		Button scenarioBttn = (Button) findViewById(R.id.new_conversation_button);
		Button historicalBttn = (Button) findViewById(R.id.history_panel_button);
		Button mannerOfUseBttn = (Button) findViewById(R.id.manner_of_use_button);
		init();
		
		exitBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new ExitApplicationConfirmationDialogFragment();
				newFragment.onAttach(self);
				newFragment.show(self.getSupportFragmentManager(),"exit");
			}
		});
		
		settingsBttn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						SettingsActivity.class);
				startActivity(i);
			}
		});
		
		scenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						NewSceneActivity.class);
				startActivity(i);
			}
		});
		
		historicalBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						HistoricalActivity.class);
				startActivity(i);
			}
		});
		
		mannerOfUseBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
	}

	private void evaluateVisibilityButton(Button historicalBttn) {
		ConversationTalkerDataSource datasource = new ConversationTalkerDataSource(this);
		datasource.open();
		List<ConversationDAO> conversations = datasource.getAllConversations();
		if (!conversations.isEmpty()){
			historicalBttn.setVisibility(View.VISIBLE);
		}
		datasource.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.talker_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onDialogPositiveClickExitApplicationDialogListener(DialogFragment dialog) {
		finish();
		System.exit(0);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		init();
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		init();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		init();
	}
}
