package ar.uba.fi.talker.calculator;

import java.text.DecimalFormat;


public abstract class CalculatorOperation extends CalculatorExpression {

	protected CalculatorExpression exp1;
	protected CalculatorExpression exp2;

	public CalculatorOperation(CalculatorExpression expresion) {
		this.exp1 = expresion;
	}
	
	public void addEpression(CalculatorExpression expresion) {
		this.exp2 = expresion;
	}
	
	protected abstract String getOpp();

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.##");
		double value1 = 0d;
		double value2 = 0d;
		StringBuffer string = new StringBuffer();
		if (exp1 != null && !exp1.toString().equals("")){
			value1 = Double.parseDouble(exp1.toString());
			string.append(df.format(value1));
		}
		string.append(this.getOpp());
		if (exp2 != null && !exp2.toString().equals("") && !exp2.toString().equals("0")){
			value2 = Double.parseDouble(exp2.toString());
			string.append(df.format(value2));
		}
		return string.toString();
	}
}
