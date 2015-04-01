package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public class CalculatorMult extends CalculatorOperation {
	
	public CalculatorMult() {
		super(null);
	}

	public CalculatorMult(CalculatorExpression expresion) {
		super(expresion);
	}

	@Override
	public double getValue() {
		if (exp1 != null && exp1 != null) {
			return exp1.getValue() * exp2.getValue();
		}
		return 0;
	}

	@Override
	public void execute(TextView text) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addValue(double value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getOpp() {
		return " x ";
	}
}
