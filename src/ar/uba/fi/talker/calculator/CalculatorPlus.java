package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public class CalculatorPlus extends CalculatorOperation {
		
	public CalculatorPlus() {
		super(null);
	}
	
	public CalculatorPlus(CalculatorExpression expresion) {
		super(expresion);
	}

	@Override
	public double getValue() {
		if (exp1 != null && exp2 != null){
			return exp1.getValue() + exp2.getValue();	
		}
		return 0;
	}

	@Override
	public void execute(TextView text) {
	}

	@Override
	public void addValue(double value) {
		
	}
	
	@Override
	protected String getOpp() {
		return " + ";
	}
}
