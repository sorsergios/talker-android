package ar.uba.fi.talker.calculator;

import android.widget.TextView;

public class CalculatorPlus extends CalculatorOpetation {
	
	public CalculatorPlus(CalculatorExpression expresion) {
		super(expresion);
	}

	@Override
	public int getValue() {
		return exp1.getValue() + exp2.getValue();
	}

	@Override
	public void excecute(TextView text) {
		// TODO Auto-generated method stub
	}

	@Override
	public void addValue(int value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected String getOpp() {
		return " + ";
	}
}
