package ar.uba.fi.talker.calculator;


public abstract class CalculatorOpetation extends CalculatorExpression {

	protected CalculatorExpression exp1;
	protected CalculatorExpression exp2;

	public CalculatorOpetation(CalculatorExpression expresion) {
		this.exp1 = expresion;
	}
	
	public void addEpression(CalculatorExpression expresion) {
		this.exp2 = expresion;
	}
	
	protected abstract String getOpp();

	@Override
	public String toString() {
		return exp1.toString() + this.getOpp() + exp2.toString();
	}
}
