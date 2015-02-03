package ar.fi.uba.androidtalker.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import ar.fi.uba.androidtalker.adapter.ImageNewSceneAdapter;
import ar.fi.uba.androidtalker.dao.ImagesDao;

public class OutdoorScenarioDialogFragment extends Fragment {

	// Use this instance of the interface to deliver action events
	NewSceneActivity listener;
	private static int RESULT_LOAD_IMAGE = 1;
	private ImageNewSceneAdapter imageAdapter;
	private GridView gridView = null;
	
	
	@Override
	public void onAttach(Activity activity){
	    super.onAttach(activity);
	    try{
	        listener = (NewSceneActivity) activity;
	    }catch(ClassCastException e){
	        throw new ClassCastException(activity.toString() + " must implement StartPayperiodDialogListener");
	    }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.layout_ext_scenes, container, false);
        gridView = (GridView) v.findViewById(R.id.gridView);
        imageAdapter = new ImageNewSceneAdapter(listener);
        imageAdapter.setParentFragment(this);
        gridView.setAdapter(imageAdapter);
        
		Button exitBttn = (Button) v.findViewById(R.id.new_scene_exit);
		Button innerBttn = (Button) v.findViewById(R.id.new_scene_inner);
		Button startScenarioBttn = (Button) v.findViewById(R.id.new_scene_start);
		
		exitBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.finish();
				System.exit(0);
			}
		});

		innerBttn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//FIXME validar que si tiene cero interiores no cambie de pantalla o vuelva atras
				FragmentManager fm = getFragmentManager();
				OutdoorScenarioDialogFragment fragmentOutdoor = (OutdoorScenarioDialogFragment)fm.findFragmentById(R.id.fragmentOutdoors);
				InnerScenarioDialogFragment fragmentInner = (InnerScenarioDialogFragment)fm.findFragmentById(R.id.fragmentInner);
				fragmentInner.initAdapter();
				
				FragmentTransaction tran = fm.beginTransaction();
				tran.hide(fragmentOutdoor);
				tran.show(fragmentInner);
				tran.commit();
				// TODO: por ahora se usa la posicion como key para buscar las imagenes 
				//de escenarios interiores, cambiar cuando se traiga de la base de datos
				int position = (int) ImageNewSceneAdapter.getPosition();
				ImagesDao.getInstance().setPositionDao(position);
				String title = getResources().getString(ImagesDao.getScenarioNameByPos(position));
				listener.setTitle(getResources().getString(R.string.title_activity_new_scene_inner) + " " + title);
			}
		});
		
		startScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long imageViewId = (Long) ImageNewSceneAdapter.getItemSelectedId();
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
				
		return v;
	}	
	Bitmap selectedImage=null;
	String picturePath;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			Uri selectedImage = data.getData();
			// View gridItem = imageAdapter.addItem(5, null, gridView);
			View gridItem = imageAdapter.getItemGrid(6);
			imageAdapter.setItem(gridItem, "IMAGEN NUEVA", selectedImage);
		}
	}
}
