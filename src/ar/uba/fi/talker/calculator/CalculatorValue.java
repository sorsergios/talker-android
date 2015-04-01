package ar.uba.fi.talker.calculator;

import java.text.DecimalFormat;

import android.widget.TextView;

public class CalculatorValue extends CalculatorExpression {

	private double value;
	
	public CalculatorValue(double d) {
		this.value = d;
	}

	@Override
	public void execute(TextView text) {
		CharSequence innetText = text.getText();
		text.setText(innetText.toString() + getValue());
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void addValue(double d) {
		this.value *= 10;
		this.value += d;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.##");
		return String.valueOf(df.format(value));
	}
}
