package ar.uba.fi.talker.listener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import ar.uba.fi.talker.ImageSettingsActivity;
import ar.uba.fi.talker.utils.GridItems;

public class OnClickStartActionSettings implements OnClickListener {

	
	private final Context context;
	protected GridItems gridItem = null;
	protected DialogFragment parent=null;
	
	public OnClickStartActionSettings(final Context context,GridItems gridItem,DialogFragment parent){
		this.context=context;
		this.gridItem=gridItem;
		this.parent = parent;
	}	
	
	@Override
	public void onClick(View v) {
		//Muestro la grilla de imagenes
		Intent intent = new Intent(context, ImageSettingsActivity.class);
		Bundle b = new Bundle();
		b.putInt("keyId", (int) gridItem.getScenarioView().getId());
		intent.putExtras(b);
		context.startActivity(intent);
	}

}