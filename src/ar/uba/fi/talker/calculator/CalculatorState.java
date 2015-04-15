/**
 * 
 */
package ar.uba.fi.talker.calculator;

import java.util.Stack;

/**
 * @author sergio
 *
 */
public class CalculatorState {

	private enum CalcStatus {
		ANY_SIMBOL,
		ONLY_NUMBER,
		ONLY_DECIMAL,
		NO_DOT,
		SOLVED
	}
	
	private Stack<CalcStatus> states;
	private int sections;
	
	public CalculatorState() {
		states = new Stack<CalcStatus>();
		this.clearStates();
	}
	
	public void clearStates() {
		states.clear();
		states.add(CalcStatus.ONLY_NUMBER);
		sections = 0;
	}
	
	public void setValue() {
		CalcStatus state = states.peek();
		switch (state) {
			case SOLVED:
				break;
			case ONLY_DECIMAL:
				states.add(CalcStatus.NO_DOT);
			case NO_DOT:
				break;
			case ONLY_NUMBER:
				states.add(CalcStatus.ANY_SIMBOL);
			default:
				break;
		}
	}

	public void setDotted() {
		states.add(CalcStatus.ONLY_DECIMAL);
	}

	public boolean dotEnabled() {
		CalcStatus state = states.peek();
		return CalcStatus.ANY_SIMBOL.equals(state)
				&& !CalcStatus.SOLVED.equals(state);
	}
	
	public void setOperation() {
		states.add(CalcStatus.ONLY_NUMBER);
	}

	public boolean operationEnabled() {
		CalcStatus state = states.peek();
		return CalcStatus.ANY_SIMBOL.equals(state) 
				|| CalcStatus.NO_DOT.equals(state)
				|| CalcStatus.SOLVED.equals(state);
	}

	public void setOpen() {
		sections++;
	}

	public boolean openEnabled() {
		CalcStatus state = states.peek();
		return CalcStatus.ONLY_NUMBER.equals(state)
				&& !CalcStatus.SOLVED.equals(state);
	}


	public void setClose() {
		sections--;
	}

	public boolean closeEnabled() {
		return sections > 0
				&& this.operationEnabled()
				&& !CalcStatus.SOLVED.equals(states.peek());
	}

	public void setSolved() {
		states.add(CalcStatus.SOLVED);
	}

	public boolean isSolved() {
		return CalcStatus.SOLVED.equals(states.peek());
	}

	public void stateBack() {
		if (!states.isEmpty()) {
			states.pop();
		}
		if (states.isEmpty()) {
			this.clearStates();
		}
	}
}
