package ar.uba.fi.talker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import ar.uba.fi.talker.fragment.OutdoorScenarioDialogFragment;

public class NewSceneActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scenes);
		
		FragmentManager fm = getFragmentManager();
		OutdoorScenarioDialogFragment fragmentOutdoor = (OutdoorScenarioDialogFragment)fm.findFragmentById(R.id.fragmentOutdoors);
        
		FragmentTransaction tran = fm.beginTransaction();
		tran.show(fragmentOutdoor);
		tran.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scenes, menu);
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
