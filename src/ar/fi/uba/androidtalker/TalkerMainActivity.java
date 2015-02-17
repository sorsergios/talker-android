package ar.fi.uba.androidtalker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TalkerMainActivity extends ActionBarActivity {

    private static Context context;

    public static Context getAppContext() {
        return TalkerMainActivity.context;
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
		
		exitBttn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				System.exit(0);
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
}
