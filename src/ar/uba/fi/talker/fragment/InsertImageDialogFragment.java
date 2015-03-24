package ar.uba.fi.talker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.adapter.InsertImageCategoryAdapter;

public class InsertImageDialogFragment extends DialogFragment {

	private static int RESULT_LOAD_IMAGE = 1;

	public interface InsertImageDialogListener {
		public void onDialogPositiveClickInsertImageDialogListener(Uri uri, Matrix matrix);
		public void onDialogPositiveClickInsertImageDialogListener2(Bitmap bmap);
	}
	static final String[] CATEGORIES = new String[] { 
		"ANIMALES", "COMIDA","OBJETOS", "PATIO" };
	
	InsertImageDialogListener listener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (InsertImageDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement InsertImageDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		// TODO arranca codigo prueba
//		Button buttonLoadImage = new Button(getActivity());
//		buttonLoadImage.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//
//				Intent i = new Intent(
//						Intent.ACTION_PICK,
//						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				startActivityForResult(i, RESULT_LOAD_IMAGE);
//			}
//		});

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View gridViewContainer = inflater.inflate(R.layout.insert_image_gridview, null);

		GridView gridView = (GridView) gridViewContainer.findViewById(R.id.insert_image_gridview);
		 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, CATEGORIES);
		
		gridView.setAdapter(new InsertImageCategoryAdapter(getActivity(), CATEGORIES));
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
			   
				ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

				imageView.buildDrawingCache();
				Bitmap bmap = imageView.getDrawingCache();
				getDialog().dismiss();
				listener.onDialogPositiveClickInsertImageDialogListener2(bmap);
			}
		});
		
		// termina codigo prueba
		builder.setView(gridViewContainer).setTitle(R.string.insert_image_title)
				.setNegativeButton(R.string.insert_image_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		return builder.create();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE
				&& resultCode == Activity.RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
            
			String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.ORIENTATION};
            
			Cursor cursor = getActivity().getContentResolver().query(
					selectedImage, filePathColumn, null, null, null);
			cursor.moveToFirst();
			int orientation = -1;
			if (cursor != null && cursor.moveToFirst()) {
				orientation = cursor.getInt(cursor.getColumnIndex(filePathColumn[1]));
			}  
			Matrix matrix = new Matrix();
			matrix.postRotate(orientation);
			cursor.close();

			getDialog().dismiss();
			listener.onDialogPositiveClickInsertImageDialogListener(selectedImage, matrix);
		}

	}

}
