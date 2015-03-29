package ar.uba.fi.talker.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.adapter.InsertImageCategoryAdapter;

public class InsertImageDialogFragment extends DialogFragment {

	private static int RESULT_LOAD_IMAGE = 1;

	private ViewFlipper flipper;
	
	private AlertDialog alert;
	
	public interface InsertImageDialogListener {
		public void onDialogPositiveClickInsertImageDialogListener(Uri uri,
				Matrix matrix);
		public void onDialogPositiveClickInsertImageDialogListener(Bitmap bitmap);
	}

	private static final String[] CATEGORIES = new String[] { "ANIMAL", "COMIDA",
			"OBJETOS", "PATIO", "NUEVA" };
	private static final String[] INNER_CATEGORIES = new String[] { "IMAGEN 1",
			"IMAGEN 2", "IMAGEN 3", "IMAGEN 4", "NUEVA" };

	private InsertImageDialogListener listener;

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

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View gridViewContainer = inflater.inflate(
				R.layout.insert_image_gridview, null);

		this.flipper = (ViewFlipper) gridViewContainer
				.findViewById(R.id.vfImages);
		
		GridView gridView = (GridView) gridViewContainer
				.findViewById(R.id.insert_cat_gridview);
		GridView gridViewImages = (GridView) gridViewContainer
				.findViewById(R.id.insert_image_gridview);

		gridView.setAdapter(new InsertImageCategoryAdapter(getActivity(),
				CATEGORIES));
		gridViewImages.setAdapter(new InsertImageCategoryAdapter(getActivity(),
				INNER_CATEGORIES));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (position == 4) {
					// Intent i = new Intent(Intent.ACTION_PICK,
					// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					// getActivity().startActivityForResult(i,
					// RESULT_LOAD_IMAGE);
				} else {
					flipper.showNext();
					Button buttonNo = alert.getButton(AlertDialog.BUTTON_NEUTRAL);
					buttonNo.setEnabled(true);
				}
			}
		});

		gridViewImages.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (position == 4) {
					Intent i = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					getActivity().startActivityForResult(i, RESULT_LOAD_IMAGE);
				} else {
					ImageView imageView = (ImageView) v
							.findViewById(R.id.grid_item_image);
					imageView.buildDrawingCache();
					Bitmap bmap = Bitmap.createBitmap(imageView
							.getDrawingCache());
					listener.onDialogPositiveClickInsertImageDialogListener(bmap);
				}
				getDialog().dismiss();
			}
		});

		builder.setView(gridViewContainer)
				.setTitle(R.string.insert_image_title)
				.setNeutralButton(R.string.insert_image_back, null)
				.setNegativeButton(R.string.insert_image_cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		
		alert = builder.create();
		return alert; 
	}

	
	@Override
	public void onStart() {
		super.onStart(); // super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after
							// this point
		AlertDialog d = (AlertDialog) getDialog();
		if (d != null) {
			final Button neutralButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);
			neutralButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					flipper.showPrevious();
					neutralButton.setEnabled(false);
				}
			});
			neutralButton.setEnabled(false);
		}
	}
	
}
