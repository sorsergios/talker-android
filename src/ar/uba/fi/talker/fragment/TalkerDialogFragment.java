package ar.uba.fi.talker.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import ar.uba.fi.talker.R;

public class TalkerDialogFragment extends DialogFragment {

	@Override
	public void onStart() {
	    super.onStart();
	    //Buttons
	    Button positiveButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
	    positiveButton.setText("");
	    Drawable drawablePositive = getActivity().getResources().getDrawable(R.drawable.accept);

        // set the bounds to place the drawable a bit right
        drawablePositive.setBounds((int) (drawablePositive.getIntrinsicWidth() * 2),
                0, (int) (drawablePositive.getIntrinsicWidth() * 3),
                drawablePositive.getIntrinsicHeight());
	    /*LayoutParams posParams = (LayoutParams) positiveButton.getLayoutParams();
        posParams.gravity = Gravity.CENTER;
        positiveButton.setLayoutParams(posParams);*/
        positiveButton.setCompoundDrawables(drawablePositive, null, null, null);
        
	    Button negativeButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
	    negativeButton.setText("");
	    Drawable drawableNegative = getActivity().getResources().getDrawable(R.drawable.cancel_back);

        // set the bounds to place the drawable a bit right
        drawableNegative.setBounds((int) (drawableNegative.getIntrinsicWidth() * 2),
                0, (int) (drawableNegative.getIntrinsicWidth() * 3),
                drawableNegative.getIntrinsicHeight());
       /* LayoutParams negParams = (LayoutParams) negativeButton.getLayoutParams();
        negParams.gravity = Gravity.CENTER;
        negativeButton.setLayoutParams(negParams);*/
        negativeButton.setCompoundDrawables(drawableNegative, null, null, null);

	}
	
}
