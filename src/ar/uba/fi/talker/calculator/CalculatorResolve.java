package ar.uba.fi.talker.calculator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import android.view.View;

public class CalculatorResolve extends CalculatorExpression {

	public CalculatorResolve(CalculatorState state) {
		super(state);
	}

	private int priority(char data) {
		int priority = 0;
		switch (data) {
		case '+':
		case '-':
			priority = 1;
			break;
		case '*':
		case '/':
			priority = 2;
			break;

		default:
			priority = -1;
			break;
		}

		return priority;
	}

	private boolean isDouble(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private double resuelve(Queue<String> postfix) {
		Stack<Double> stack = new Stack<Double>();
		double a, b;
		while (postfix.peek() != null) {
			String dato = postfix.poll();
			if (this.isDouble(dato)) {
				stack.push(Double.valueOf(dato));
			} else {
				b = stack.pop();
				a = stack.pop();

				if ("+".equals(dato))
					stack.push(a + b);
				else if ("-".equals(dato))
					stack.push(a - b);
				else if ("*".equals(dato))
					stack.push(a * b);
				else if ("/".equals(dato))
					stack.push(a / b);
			}
		}
		
		getState().setSolved();
		return stack.pop();
	}

	@Override
	public void onClick(View v) {
		// String uno="(5+3)*(2+3)";
		if (getState().isSolved()) return;
		
		CharSequence expression = getTextView().getText();

		char character;
		Character x;
		StringBuilder numberBuilder = new StringBuilder();

		Queue<String> postfix = new LinkedList<String>();
		Stack<Character> stack = new Stack<Character>();

		for (int i = 0; i < expression.length(); i++) {
			character = expression.charAt(i);
			if (character == ' ' && numberBuilder.length() > 0) {
				postfix.offer(numberBuilder.toString());
				numberBuilder.delete(0, numberBuilder.length());
			} else if (Character.isDigit(character) || character == '.') {
				numberBuilder.append(character);
			} else if (stack.empty() || character == '('
					|| priority(character) > priority(stack.peek())) {

				stack.push(character);
			} else {
				if (priority(character) == priority(stack.peek())) {
					x = stack.peek();
					if (x == character) {
						stack.push(character);
					} else {
						x = stack.pop();
						postfix.offer(x.toString());
						stack.push(character);
					}
				}
				if (character != ' ') {
					if (numberBuilder.length() > 0) {
						postfix.offer(numberBuilder.toString());
						numberBuilder.delete(0, numberBuilder.length());
					}
					while (!stack.isEmpty() && stack.peek() != '(') {
						x = stack.pop();
						postfix.offer(x.toString());
					}
					if (!stack.isEmpty()) {
						stack.pop();
					}
					if (character != ')') {
						stack.push(character);
					}
				}
			}
		}

		if (numberBuilder.length() > 0) {
			postfix.offer(numberBuilder.toString());
			numberBuilder.delete(0, numberBuilder.length());
		}
		while (!stack.empty()) {
			Character rest = stack.pop();
			if (!rest.equals('(')) {
				postfix.offer(rest.toString());
			}
		}

		String aString = Double.toString(resuelve(postfix));
		getTextView().append(" = " + aString);
	}
}
