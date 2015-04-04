package ar.uba.fi.talker.calculator;

import android.view.View;
import android.widget.Button;


public class CalculatorOperation extends CalculatorExpression {

	public CalculatorOperation(CalculatorState state) {
		super(state);
	}
	
	@Override
	public void onClick(View v) {
		if (getState().operationEnabled()) {
			getState().setOperation();
			Button button = (Button) v;
			CharSequence text = button.getText();
			getTextView().append(" " + text + " ");
		}
	}
}
