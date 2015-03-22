package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public abstract class CalculatorExpression {
	
	public abstract int getValue();
	
	public abstract void excecute(TextView text);

	public abstract void addValue(int value);
	
	public abstract String toString();
	
}
