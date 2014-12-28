package ar.fi.uba.androidtalker.fragment;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
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

	private long imageViewId;
	
	// Use this instance of the interface to deliver action events
	NewSceneActivity listener;
	
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
        View v = inflater.inflate(R.layout.layout_inner_scenes, container, false);
        
        GridView gridViewInner = (GridView) v.findViewById(R.id.gridViewInner);
	    gridViewInner.setAdapter(new ImageNewInnerSceneAdapter(listener, imageViewId));
	    
	    Log.i("lala", ""+imageViewId);
		Button startBttn = (Button) v.findViewById(R.id.start_conversation);
		Button exitBttn = (Button) v.findViewById(R.id.button3);
		
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
				Display display = listener.getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				int height = size.y;
		        Bitmap resized = Bitmap.createScaledBitmap(image, width , height, true);
		        ByteArrayOutputStream stream = new ByteArrayOutputStream();
		        resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
		        byte[] bytes = stream.toByteArray();
				return bytes;
			}
		});
		
		exitBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.finish();
				System.exit(0);
			}
		});
        return v;
	}

	public void setSelectId(long imageViewId) {
		this.imageViewId = imageViewId;
	}
}
