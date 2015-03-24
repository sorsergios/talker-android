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
import android.widget.GridView;
import android.widget.ImageView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.adapter.InsertImageCategoryAdapter;

public class InsertImageDialogFragment extends DialogFragment {

	private static int RESULT_LOAD_IMAGE = 1;

	public interface InsertImageDialogListener {
		public void onDialogPositiveClickInsertImageDialogListener(Uri uri, Matrix matrix);
		public void onDialogPositiveClickInsertImageDialogListener(Bitmap bitmap);
	}

	static final String[] CATEGORIES = new String[] { "ANIMAL", "COMIDA", "OBJETOS", "PATIO", "NUEVA" };

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
		
		gridView.setAdapter(new InsertImageCategoryAdapter(getActivity(), CATEGORIES));
 
		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				if (position == 4) {
					Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					getActivity().startActivityForResult(i, RESULT_LOAD_IMAGE);
				} else {
					ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);
					imageView.buildDrawingCache();
					Bitmap bmap = Bitmap.createBitmap(imageView.getDrawingCache());
					listener.onDialogPositiveClickInsertImageDialogListener(bmap);
				}
				getDialog().dismiss();
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

}
