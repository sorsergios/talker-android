/**
 * 
 */
package ar.uba.fi.talker.calculator;

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
	
	private CalcStatus state;
	private int sections;
	
	public CalculatorState() {
		state = CalcStatus.ONLY_NUMBER;
		sections = 0;
	}
	
	public void setValue() {
		switch (state) {
			case SOLVED:
				break;
			case ONLY_DECIMAL:
			case NO_DOT:
				state = CalcStatus.NO_DOT;
				break;
			case ONLY_NUMBER:
			default:
				state = CalcStatus.ANY_SIMBOL;
				break;
		}
	}

	public void setDotted() {
		state = CalcStatus.ONLY_DECIMAL;
	}

	public boolean dotEnabled() {
		return CalcStatus.ANY_SIMBOL.equals(state)
				&& !CalcStatus.SOLVED.equals(state);
	}
	
	public void setOperation() {
		state = CalcStatus.ONLY_NUMBER;
	}

	public boolean operationEnabled() {
		return (CalcStatus.ANY_SIMBOL.equals(state) 
				|| CalcStatus.NO_DOT.equals(state))
				&& !CalcStatus.SOLVED.equals(state);
	}

	public void setOpen() {
		sections++;
	}

	public boolean openEnabled() {
		return (this.operationEnabled() 
				|| CalcStatus.ONLY_NUMBER.equals(state))
				&& !CalcStatus.SOLVED.equals(state);
	}


	public void setClose() {
		sections--;
	}

	public boolean closeEnabled() {
		return sections > 0
				&& this.operationEnabled()
				&& !CalcStatus.SOLVED.equals(state);
	}

	public void setSolved() {
		state = CalcStatus.SOLVED;
	}

	public boolean isSolved() {
		return CalcStatus.SOLVED.equals(state);
	}
}
