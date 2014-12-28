package ar.fi.uba.androidtalker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import ar.fi.uba.androidtalker.fragment.OutdoorScenariDialogFragment;
import ar.fi.uba.androidtalker.fragment.InnerScenariDialogFragment;

public class NewSceneActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_scenes);
		
		FragmentManager fm = getFragmentManager();
		OutdoorScenariDialogFragment fragmentOutdoor = (OutdoorScenariDialogFragment)fm.findFragmentById(R.id.fragmentOutdoors);
		InnerScenariDialogFragment fragmentInner = (InnerScenariDialogFragment)fm.findFragmentById(R.id.fragmentInner);
        
		FragmentTransaction tran = fm.beginTransaction();
		tran.show(fragmentOutdoor);
		tran.hide(fragmentInner);
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
