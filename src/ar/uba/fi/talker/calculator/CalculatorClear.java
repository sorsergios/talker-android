package ar.uba.fi.talker.calculator;

import android.view.View;

public class CalculatorClear extends CalculatorExpression {

	private static final int SIMBOL = 3;
	private static final int NUMBER = 1;

	public CalculatorClear(CalculatorState state) {
		super(state);
	}
	
	@Override
	public void onClick(View v) {
		CharSequence text = this.getTextView().getText();
		if (text.length() > 0) {
			char lastChar = text.charAt(text.length()-1);
			int cut = (lastChar == ' ' ? SIMBOL : NUMBER);
			getState().setDotted();
			text = text.subSequence(0, text.length()-cut);
			
			this.getTextView().setText(text);
		}
	}
}
