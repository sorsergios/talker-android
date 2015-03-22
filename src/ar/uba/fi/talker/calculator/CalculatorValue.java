package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public class CalculatorValue extends CalculatorExpression {

	private int value;
	
	public CalculatorValue(int initValue) {
		this.value = initValue;
	}

	@Override
	public void excecute(TextView text) {
		CharSequence innetText = text.getText();
		text.setText(innetText.toString() + getValue());
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void addValue(int value) {
		this.value *= 10;
		this.value += value;
	}
	
	@Override
	public String toString() {
		return value > 0 ? String.valueOf(value) : "";
	}
}
