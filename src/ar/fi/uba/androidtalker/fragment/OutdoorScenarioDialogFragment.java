package ar.fi.uba.androidtalker.fragment;

import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import ar.fi.uba.androidtalker.CanvasActivity;
import ar.fi.uba.androidtalker.NewSceneActivity;
import ar.fi.uba.androidtalker.R;
import ar.fi.uba.androidtalker.adapter.ImageNewSceneAdapter;
import ar.fi.uba.talker.utils.ImageUtils;

public class OutdoorScenarioDialogFragment extends Fragment {

	// Use this instance of the interface to deliver action events
	NewSceneActivity newSceneActivity;
	private static int RESULT_LOAD_IMAGE = 1;
	private static int RESULT_SELECT_IMAGE = 2;
	private ImageNewSceneAdapter imageAdapter;
	private GridView gridView = null;
	
	
	@Override
	public void onAttach(Activity activity){
	    super.onAttach(activity);
	    try{
	        newSceneActivity = (NewSceneActivity) activity;
	    }catch(ClassCastException e){
	        throw new ClassCastException(activity.toString() + " must implement StartPayperiodDialogListener");
	    }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.layout_ext_scenes, container, false);
        gridView = (GridView) v.findViewById(R.id.gridView);
        imageAdapter = new ImageNewSceneAdapter(newSceneActivity);
        imageAdapter.setParentFragment(this);
        gridView.setAdapter(imageAdapter);
       
		ImageButton exitBttn = (ImageButton) v.findViewById(R.id.new_scene_exit);
		ImageButton startScenarioBttn = (ImageButton) v.findViewById(R.id.new_scene_start);
		
		exitBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				newSceneActivity.finish();
				System.exit(0);
			}
		});

		
		startScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long imageViewId = (Long) ImageNewSceneAdapter.getItemSelectedId();
				byte[] bytes = ImageUtils.transformImage(getResources(), imageViewId); 
				
				Bundle extras = new Bundle();
				extras.putByteArray("BMP",bytes);
				Intent intent = new Intent(newSceneActivity.getApplicationContext(), CanvasActivity.class);
				intent.putExtras(extras);
				startActivity(intent);
			}
		});
				
		return v;
	}	
	Bitmap selectedImage=null;
	String picturePath;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//TODO acá esta configurado el codigo de empezar directo
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			Uri imageUri = data.getData();
	        Bitmap bitmap = null;
			try {
				bitmap = MediaStore.Images.Media.getBitmap(newSceneActivity.getContentResolver(), imageUri);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			// View gridItem = imageAdapter.addItem(5, null, gridView);
//			View gridItem = imageAdapter.getItemGrid(6);
//			imageAdapter.setItem(gridItem, "IMAGEN NUEVA", selectedImage);
			 
			byte[] bytes = ImageUtils.transformImage(bitmap); 
			
			Bundle extras = new Bundle();
			extras.putByteArray("BMP",bytes);
			Intent intent = new Intent(newSceneActivity.getApplicationContext(), CanvasActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
		}		
	}
}
