package ar.fi.uba.androidtalker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import ar.fi.uba.androidtalker.action.userlog.LogoutDialogFragment;

public class SettingsActivity extends ActionBarActivity {
	
	Settings settings;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		Button backBttn = (Button) findViewById(R.id.settings_back_button);
		Button loginBttn = (Button) findViewById(R.id.settings_login);
		Button logoutBttn = (Button) findViewById(R.id.settings_logout);
		
		final CheckBox elementTxtChkbx = (CheckBox) findViewById(R.id.checkbox_settings_element_text);
		
		backBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		loginBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(
						SettingsActivity.this);

				dialog.setTitle(R.string.login_title);
				
				LayoutInflater factory = LayoutInflater.from(SettingsActivity.this);
				final View mainView = factory.inflate(R.layout.login, null);
				
				dialog.setView(mainView);
				
				dialog.setPositiveButton(R.string.login_accept,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								Log.i("Talker", "corriendo login");
							}
						});

				dialog.setNegativeButton(R.string.login_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.dismiss();
							}
						});
				
				dialog.setNeutralButton(R.string.login_logup,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								//FIXME CREAR CUENTA??
							}
						});

				dialog.show();

			}
		});
		
		logoutBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("Talker", "corriendo logout");

				DialogFragment newFragment = new LogoutDialogFragment();
				newFragment.show(getSupportFragmentManager(), "logout");

			}
		});
		
		elementTxtChkbx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (elementTxtChkbx.isChecked()) {
					System.out.println("Checked");
				} else {
					System.out.println("Un-Checked");
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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