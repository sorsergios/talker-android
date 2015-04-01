package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public abstract class CalculatorExpression {
	
	public abstract double getValue();
	
	public abstract void execute(TextView text);

	public abstract void addValue(double value);
	
	public abstract String toString();
	
}
