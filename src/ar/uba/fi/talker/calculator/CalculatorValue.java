package ar.uba.fi.talker.calculator;

import java.text.DecimalFormat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorValue extends CalculatorExpression {

	private double value;
	
	public CalculatorValue(double d) {
		this.value = d;
	}

	public CalculatorValue() {		
	}

	@Override
	public void execute(TextView text) {
		CharSequence innetText = text.getText();
		text.setText(innetText.toString() + getValue());
	}

	public double getValue() {
		return value;
	}

	public void setValue(double d) {
		this.value = d;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.##");
		return String.valueOf(df.format(value));
	}

	@Override
	public void addValue(double value) {
		// TODO Auto-generated method stub
	}
	
}
