package ar.uba.fi.talker.fragment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.dao.ContactDAO;
import ar.uba.fi.talker.dao.ImageDAO;
import ar.uba.fi.talker.dataSource.ContactTalkerDataSource;
import ar.uba.fi.talker.dataSource.ImageTalkerDataSource;
import ar.uba.fi.talker.fragment.TextDialogFragment.TextDialogListener;
import ar.uba.fi.talker.utils.ImageUtils;

public class ContactDialogFragment extends ParentDialogFragment {

	TextDialogListener listener;
	private static int RESULT_LOAD_IMAGE_CONTACT = 3;
	ImageView imageView = null;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			listener = (TextDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement ContactDialogListener");
		}
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		final View lila = View.inflate(getActivity(), R.layout.contact_form, null);
		imageView = (ImageView) lila.findViewById(R.id.contact_image);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				ContactDialogFragment.this.startActivityForResult(i, RESULT_LOAD_IMAGE_CONTACT);
			}
		});

		// Use the Builder class for convenient dialog construction
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(lila)
				.setTitle(R.string.insert_contact_title)
				.setPositiveButton(R.string.delete_conversation_accept,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								listener.onDialogPositiveClickTextDialogListener(ContactDialogFragment.this);
								dialog.dismiss();
							}
						})
				.setNegativeButton(R.string.insert_text_cancel,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
							}
						});
		return builder.create();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RESULT_LOAD_IMAGE_CONTACT && null != data) {
				Uri imageUri = data.getData();
		        Bitmap bitmap = null;
		        int orientation = ImageUtils.getImageRotation(this.getActivity(), imageUri);
				try {/*Entra al if cuando se elige una foto de google +*/
					ContentResolver contentResolver = getActivity().getContentResolver();
					InputStream is = contentResolver.openInputStream(imageUri);
					if (imageUri != null && imageUri.getHost().contains("com.google.android.apps.photos.content")){
						/* if image belongs to google+*/
						bitmap = BitmapFactory.decodeStream(is);
					} else {
						bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
					}
				} catch (IOException e) {
					Log.e("ADD_SCENARIO", "Unexpected problem new scenario process.", e);
				}
				
				DisplayMetrics metrics = getResources().getDisplayMetrics();
				Float size = metrics.density * 300;
				bitmap = Bitmap.createScaledBitmap(bitmap, size.intValue(), size.intValue(), true);
				imageView.setImageBitmap(bitmap);
				new ContactSaverTask(this.getActivity(), orientation).execute(bitmap);
			}
		}
	}

	private class ContactSaverTask extends AsyncTask<Bitmap, Boolean, File> {

		private Context context;
		private int orientation;

		public ContactSaverTask(Context context, int orientation) {
			this.context = context;
			this.orientation = orientation;
		}
		
		@Override
		protected void onPostExecute(File result) {
			ImageDAO image = new ImageDAO();
			image.setPath(result.getPath());
			image.setName(result.getName());
			ImageTalkerDataSource dataSource = new ImageTalkerDataSource(context);
			long imageId = dataSource.add(image);
			ContactTalkerDataSource contactDataSource = new ContactTalkerDataSource(context);
			
			ContactDAO contact = new ContactDAO();
			contact.setImageId(imageId);
			contact.setName(image.getName());
			contactDataSource.add(contact);
		}
		
		@Override
		protected File doInBackground(Bitmap... params) {
			Bitmap bitmap = params[0];
			String name = "NUEVO";
			ImageUtils.saveFileInternalStorage(name , bitmap , context, orientation);
			
			return new File(context.getFilesDir(), name);
		}
		
	}
}
