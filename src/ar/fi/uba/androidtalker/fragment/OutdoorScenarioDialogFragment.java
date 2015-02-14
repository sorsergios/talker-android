package ar.fi.uba.androidtalker.fragment;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;
import ar.fi.uba.androidtalker.CanvasActivity;
import ar.fi.uba.androidtalker.NewSceneActivity;
import ar.fi.uba.androidtalker.R;
import ar.fi.uba.androidtalker.action.userlog.TextDialogFragment.TextDialogListener;
import ar.fi.uba.androidtalker.adapter.ChangeNameDialogFragment;
import ar.fi.uba.androidtalker.adapter.ImageNewSceneAdapter;
import ar.fi.uba.talker.utils.ImageUtils;
import ar.uba.fi.talker.component.command.ActivityCommand;

public class OutdoorScenarioDialogFragment extends Fragment implements TextDialogListener {

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
	        throw new ClassCastException(activity.toString() + " must implement NewSceneActivity");
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
		ImageButton editNameScenarioBttn = (ImageButton) v.findViewById(R.id.new_scene_edit_scenario_name);
		
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
				if (ImageNewSceneAdapter.getItemSelectedId() == null) {
					Toast.makeText(newSceneActivity, "Debe elegir una imagen para continuar", Toast.LENGTH_SHORT).show();
				} else{
					long imageViewId = ImageNewSceneAdapter.getItemSelectedId();
					byte[] bytes = ImageUtils.transformImage(getResources(), imageViewId); 
					Bundle extras = new Bundle();
					extras.putByteArray("BMP",bytes);
					Intent intent = new Intent(newSceneActivity.getApplicationContext(), CanvasActivity.class);
					intent.putExtras(extras);
					startActivity(intent);
				}
			}
		});
		
		editNameScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ActivityCommand command = new ActivityCommand() {
					@Override
					public void execute() {
						DialogFragment newFragment = new ChangeNameDialogFragment();
						newFragment.show(newSceneActivity.getSupportFragmentManager(), "insert_text");
					}
				};
				command.execute();
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
			byte[] bytes = ImageUtils.transformImage(bitmap); 
			
			Bundle extras = new Bundle();
			extras.putByteArray("BMP",bytes);
			Intent intent = new Intent(newSceneActivity.getApplicationContext(), CanvasActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
		}		
	}

	@Override
	public void onDialogPositiveClickTextDialogListener(DialogFragment dialog) {
		Dialog dialogView = dialog.getDialog();
		EditText inputText = (EditText) dialogView.findViewById(R.id.insert_text_input);
		Bitmap selectedImage=null;
		View gridItem = imageAdapter.getItemGrid(6);
		imageAdapter.setItem(gridItem, inputText.getText().toString(), selectedImage);
	}
}
