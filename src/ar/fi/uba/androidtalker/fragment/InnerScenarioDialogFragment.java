package ar.fi.uba.androidtalker.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import ar.fi.uba.androidtalker.CanvasActivity;
import ar.fi.uba.androidtalker.NewSceneActivity;
import ar.fi.uba.androidtalker.R;
import ar.fi.uba.androidtalker.adapter.ImageNewInnerSceneAdapter;

public class InnerScenarioDialogFragment extends Fragment {

	public static long position;
	
	// Use this instance of the interface to deliver action events
	NewSceneActivity listener;
	GridView gridViewInner;
	
	@Override
	public void onAttach(Activity activity){
	    super.onAttach(activity);
	    try{
	        listener = (NewSceneActivity) activity;
	    }catch(ClassCastException e){
	        throw new ClassCastException(activity.toString() + " must implement NewSceneActivity");
	    }
	}
	/***
	 * Initialize to reload images show in GridView.
	 */
	public void initAdapter() {
		 gridViewInner = (GridView) this.getView().findViewById(R.id.gridViewInner);
		 ImageNewInnerSceneAdapter innerAdapter = new ImageNewInnerSceneAdapter(listener, getPosition());
		 gridViewInner.setAdapter(innerAdapter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_inner_scenes, container, false);
		Button startBttn = (Button) v.findViewById(R.id.start_conversation);
		Button exitBttn = (Button) v.findViewById(R.id.backGridView);
		
		startBttn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long imageViewId = (Long) ImageNewInnerSceneAdapter.getItemSelectedId();
				byte[] bytes = transformImage(imageViewId); 

				Bundle extras = new Bundle();
				extras.putByteArray("BMP",bytes);
				Intent intent = new Intent(listener.getApplicationContext(), CanvasActivity.class);
				intent.putExtras(extras);
				startActivity(intent);
			}

			private byte[] transformImage(long imageViewId) {
				Bitmap image = BitmapFactory.decodeResource(getResources(),(int) imageViewId);
				int newHeight = (int) ( image.getHeight() * (512.0 / image.getWidth()) );
				Bitmap scaled = Bitmap.createScaledBitmap(image, 512, newHeight, true);
		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        scaled.compress(Bitmap.CompressFormat.PNG, 70, stream);
		        byte[] bytes = stream.toByteArray();
				return bytes;
			}
		});
		
		exitBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				OutdoorScenarioDialogFragment fragmentOutdoor = (OutdoorScenarioDialogFragment)fm.findFragmentById(R.id.fragmentOutdoors);
				InnerScenarioDialogFragment fragmentInner = (InnerScenarioDialogFragment)fm.findFragmentById(R.id.fragmentInner);
		        
				FragmentTransaction tran = fm.beginTransaction();
				tran.show(fragmentOutdoor);
				tran.hide(fragmentInner);
				tran.commit();
				listener.setTitle(R.string.title_activity_new_scene);
			}
		});
        return v;
	}

	public long getPosition() {
		return InnerScenarioDialogFragment.position;
	}
	
	public void setPosition(long position) {
		InnerScenarioDialogFragment.position = position;
	}
	
}
