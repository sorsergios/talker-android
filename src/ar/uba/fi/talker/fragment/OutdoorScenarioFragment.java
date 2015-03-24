package ar.uba.fi.talker.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import ar.uba.fi.talker.CanvasActivity;
import ar.uba.fi.talker.NewSceneActivity;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.adapter.GridScenesAdapter;
import ar.uba.fi.talker.adapter.PagerScenesAdapter;
import ar.uba.fi.talker.dao.ImageTalkerDataSource;
import ar.uba.fi.talker.dao.ScenarioDAO;
import ar.uba.fi.talker.fragment.ChangeNameDialogFragment.TextDialogListener;
import ar.uba.fi.talker.fragment.DeleteScenarioConfirmationDialogFragment.DeleteScenarioDialogListener;
import ar.uba.fi.talker.utils.GridItems;
import ar.uba.fi.talker.utils.GridUtils;
import ar.uba.fi.talker.utils.ImageUtils;
import ar.uba.fi.talker.utils.ScenarioView;

import com.viewpagerindicator.PageIndicator;

public class OutdoorScenarioFragment extends Fragment implements TextDialogListener, DeleteScenarioDialogListener {

	// Use this instance of the interface to deliver action events
	NewSceneActivity newSceneActivity;
	private static int RESULT_LOAD_IMAGE = 1;
	private GridView gridView = null;
	public PageIndicator pageIndicator;
	private ViewPager viewPager;
	private PagerScenesAdapter pagerAdapter;
	private ImageTalkerDataSource datasource = null;
	private int position;
	private static final int HEIGHT = 200; // TODO Make it a parameter.
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
		viewPager = (ViewPager) v.findViewById(R.id.pager);
		pageIndicator = (PageIndicator) v.findViewById(R.id.pagerIndicator);
		ArrayList<ScenarioView> a = new ArrayList<ScenarioView>();

		ScenarioView m = null;
		if (datasource == null ) {
			datasource = new ImageTalkerDataSource(newSceneActivity.getApplicationContext());
		}
	    datasource.open();
		List<ScenarioDAO> allImages = datasource.getAllImages();
		for (int i = 0; i < allImages.size(); i++) {
			ScenarioDAO scenarioDAO = (ScenarioDAO) allImages.get(i);
			m = new ScenarioView(scenarioDAO.getId(), scenarioDAO.getIdCode(), scenarioDAO.getPath(), scenarioDAO.getName());
			a.add(m);
		}
		List<ScenesGridFragment> gridFragments = GridUtils.setScenesGridFragments(newSceneActivity, a, this);

		pagerAdapter = new PagerScenesAdapter(newSceneActivity.getSupportFragmentManager(), gridFragments);
		viewPager.setAdapter(pagerAdapter);
		pageIndicator.setViewPager(viewPager);
		
		ImageButton startScenarioBttn = (ImageButton) v.findViewById(R.id.new_scene_start);
		ImageButton editNameScenarioBttn = (ImageButton) v.findViewById(R.id.new_scene_edit_scenario_name);
		ImageButton deleteScenarioBttn = (ImageButton) v.findViewById(R.id.new_scene_delete_scenario_name);
		ImageButton galleryScenarioBttn = (ImageButton) v.findViewById(R.id.new_scene_gallery);
		
		startScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int imageViewId = GridScenesAdapter.getItemSelectedId().intValue();
				ScenarioDAO scenariodao = datasource.getScenarioByID(imageViewId);
				long imageDatasourceID = scenariodao.getIdCode();
				byte[] bytes = null;
				if (imageDatasourceID != 0){
					bytes = ImageUtils.transformImage(getResources(), imageDatasourceID); 
				} else {
					Context ctx = newSceneActivity.getApplicationContext();
					bytes = ImageUtils.transformImage(getImageBitmap(ctx, scenariodao.getPath()));
				}
				Bundle extras = new Bundle();
				extras.putByteArray("BMP",bytes);
				Intent intent = new Intent(newSceneActivity.getApplicationContext(), CanvasActivity.class);
				intent.putExtras(extras);
				startActivity(intent);				
			}
		});
		
		galleryScenarioBttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ScenesGridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		editNameScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ScenesGridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				final int numVisibleChildren = gridView.getChildCount();
				final int firstVisiblePosition = gridView.getFirstVisiblePosition();

				int positionIamLookingFor = (int) GridScenesAdapter.getPosition();
				for (int i = 0; i < numVisibleChildren; i++) {
					if ((firstVisiblePosition + i) == positionIamLookingFor) {
						position = i;
					}
				}
				DialogFragment newFragment = new ChangeNameDialogFragment();
				newFragment.show(newSceneActivity.getSupportFragmentManager(),"insert_text");
			}
		});
		
		deleteScenarioBttn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ScenesGridFragment sgf = pagerAdapter.getItem(viewPager.getCurrentItem());
				gridView = sgf.getmGridView();
				final int numVisibleChildren = gridView.getChildCount();
				final int firstVisiblePosition = gridView.getFirstVisiblePosition();

				int positionIamLookingFor = (int) GridScenesAdapter.getPosition();
				for (int i = 0; i < numVisibleChildren; i++) {
					if ((firstVisiblePosition + i) == positionIamLookingFor) {
						position = i;
					}
				}
				
				DialogFragment newFragment = new DeleteScenarioConfirmationDialogFragment();
				newFragment.show(newSceneActivity.getSupportFragmentManager(), "delete_scenario");
			}
		});
		return v;
	}	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//TODO acá esta configurado el codigo de empezar directo y guardarlo en la base
		byte[] bytes = null;
		ScenarioDAO scenario = null;
		if (requestCode == RESULT_LOAD_IMAGE && null != data) {
			Uri imageUri = data.getData();
			String scenarioName = imageUri.getLastPathSegment(); 
	        Bitmap bitmap = null;
			try {
				bitmap = MediaStore.Images.Media.getBitmap(newSceneActivity.getContentResolver(), imageUri);
				bytes = ImageUtils.transformImage(bitmap);
				Context ctx = newSceneActivity.getApplicationContext();
				saveFileInternalStorage(scenarioName, bitmap, ctx);
				File file = new File(ctx.getFilesDir(), scenarioName);
				scenario = datasource.createScenario(file.getPath(), scenarioName);
				GridScenesAdapter gsa = (GridScenesAdapter) gridView.getAdapter();
				ScenarioView scenarioView = new ScenarioView(scenario.getId(),
						scenario.getIdCode(), scenario.getPath(),
						scenario.getName());
				GridItems gridItem = new GridItems(scenario.getId(), scenarioView);
				gsa.addItem(gridItem);
				gsa.notifyDataSetInvalidated();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Bundle extras = new Bundle();
			extras.putByteArray("BMP",bytes);
			Intent intent = new Intent(newSceneActivity.getApplicationContext(), CanvasActivity.class);
			intent.putExtras(extras);
			startActivity(intent);
		}		
	}
	
	
	private void saveFileInternalStorage(String name, Bitmap b, Context context) {

		FileOutputStream out;
		try {
			out = context.openFileOutput(name, Context.MODE_PRIVATE);
			b = setContent(b);
			b.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Bitmap setContent(Bitmap image) {
		int bWidth = image.getWidth();
        int bHeight = image.getHeight();

        float parentRatio = (float) bHeight / bWidth;

        int nHeight = HEIGHT; 
        int nWidth = (int) (HEIGHT / parentRatio);
        
        return Bitmap.createScaledBitmap(image, nWidth, nHeight, true);
	}
	
	private Bitmap getImageBitmap(Context context, String name) {
		try {
			File f = new File(name);
			Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void onDialogPositiveClickTextDialogListener(DialogFragment dialog) {
		Dialog dialogView = dialog.getDialog();
		EditText inputText = (EditText) dialogView.findViewById(R.id.insert_text_input);
		GridScenesAdapter gsa = (GridScenesAdapter) gridView.getAdapter();
		String newScenarioName = inputText.getText().toString();
		((GridItems)gsa.getItem(position)).getScenarioView().setName(newScenarioName);
		gsa.notifyDataSetInvalidated();
		datasource.updateScenario(GridScenesAdapter.getItemSelectedId(), newScenarioName);

	}

	@Override
	public void onDialogPositiveClickDeleteScenarioDialogListener(
			DialogFragment dialog) {
		int imageViewId = GridScenesAdapter.getItemSelectedId().intValue();
		ScenarioDAO scenarioDAO = datasource.getScenarioByID(imageViewId);
		File file = new File(scenarioDAO.getPath());
		boolean deleted = file.delete();
		if (deleted){
			datasource.deleteScenario(GridScenesAdapter.getItemSelectedId());
		}
		GridScenesAdapter gsa = (GridScenesAdapter) gridView.getAdapter();
		gsa.removeItem(position);
		gsa.notifyDataSetInvalidated();
	}
}
