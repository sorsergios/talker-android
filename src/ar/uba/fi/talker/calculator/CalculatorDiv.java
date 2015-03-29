package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public class CalculatorDiv extends CalculatorOperation {
	
	public CalculatorDiv(CalculatorExpression expresion) {
		super(expresion);
	}

	@Override
	public double getValue() {
		if (exp1 != null && exp1 != null) {
			if (exp2.getValue() != 0){
				return exp1.getValue() / exp2.getValue();
			}
		}
		return 0d;
	}

	@Override
	public void execute(TextView text) {
		
	}

	@Override
	public void addValue(double value) {
		
	}
	
	@Override
	protected String getOpp() {
		return " / ";
	}
}
