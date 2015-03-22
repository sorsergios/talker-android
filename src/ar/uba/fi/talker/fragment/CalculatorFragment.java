package ar.uba.fi.talker.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.calculator.CalculatorDiv;
import ar.uba.fi.talker.calculator.CalculatorExpression;
import ar.uba.fi.talker.calculator.CalculatorMinus;
import ar.uba.fi.talker.calculator.CalculatorMult;
import ar.uba.fi.talker.calculator.CalculatorOpetation;
import ar.uba.fi.talker.calculator.CalculatorPlus;
import ar.uba.fi.talker.calculator.CalculatorValue;

public class CalculatorFragment extends DialogFragment implements OnClickListener {

	private boolean result = false;
	private SparseArray<CalculatorExpression> buttons;
	private TextView text;
	private CalculatorValue currentValue;
	private CalculatorOpetation calculatorExpression;

	public CalculatorFragment() {
				
		currentValue = new CalculatorValue(0);
		
		buttons = new SparseArray<CalculatorExpression>();
		
		buttons.put(R.id.button1, new CalculatorValue(1));
		buttons.put(R.id.button2, new CalculatorValue(2));
		buttons.put(R.id.button3, new CalculatorValue(3));
		buttons.put(R.id.button4, new CalculatorValue(4));
		buttons.put(R.id.button5, new CalculatorValue(5));
		buttons.put(R.id.button6, new CalculatorValue(6));
		buttons.put(R.id.button7, new CalculatorValue(7));
		buttons.put(R.id.button8, new CalculatorValue(8));
		buttons.put(R.id.button9, new CalculatorValue(9));
		buttons.put(R.id.button0, new CalculatorValue(0));
		buttons.put(R.id.buttonPlus, null);
		buttons.put(R.id.buttonMinus, null);
		buttons.put(R.id.buttonMult, null);
		buttons.put(R.id.buttonDiv, null);
		buttons.put(R.id.buttonClear, null);
		buttons.put(R.id.buttonResult, null);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View calculator = getActivity().getLayoutInflater().inflate(
				R.layout.calculator, null);

		text = (TextView) calculator.findViewById(R.id.calc_text);
		for (int i = 0; i < this.buttons.size(); i++) {
			Button button = (Button) calculator.findViewById(this.buttons.keyAt(i));
			button.setOnClickListener((OnClickListener) this);
		}
		builder.setView(calculator).setNegativeButton(R.string.close_modal,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		return builder.create();
	}

	@Override
	public void onClick(View v) {
		
		boolean newValue = true;
		
		switch (v.getId()) {
		case R.id.buttonPlus:
			calculatorExpression = new CalculatorPlus(currentValue);
			break;
		case R.id.buttonMinus:
			calculatorExpression = new CalculatorMinus(currentValue);
			break;
		case R.id.buttonMult:
			calculatorExpression = new CalculatorMult(currentValue);
			break;
		case R.id.buttonDiv:
			calculatorExpression = new CalculatorDiv(currentValue);
			break;
		case R.id.buttonClear:
			newValue = false;
			int prevValue = currentValue.getValue() / 10;
			currentValue = new CalculatorValue(prevValue);
			break;
		case R.id.buttonResult:
			result = true;
			newValue = false;
			int resultValue = calculatorExpression.getValue();
			currentValue = new CalculatorValue(resultValue);
			calculatorExpression = null;
			break;

		default:
			newValue = false;
			if (result) {
				currentValue = new CalculatorValue(this.buttons.get(v.getId()).getValue());
				result = false;
			} else {
				currentValue.addValue(this.buttons.get(v.getId()).getValue());
			}
			
			break;
		}
		
		if (newValue) {
			currentValue = new CalculatorValue(0);
			calculatorExpression.addEpression(currentValue);
		}
		
		if (calculatorExpression != null) {
			text.setText(calculatorExpression.toString());
		} else {
			text.setText(currentValue.toString());
		}
	}
}
