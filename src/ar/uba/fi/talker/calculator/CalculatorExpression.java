package ar.uba.fi.talker.calculator;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class CalculatorExpression implements OnClickListener {
	
	private TextView textView;
	private CalculatorState state;

	public CalculatorExpression(CalculatorState state) {
		this.state = state;
	}
	
	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		CharSequence text = button.getText();
		textView.append(text);
	}

	public void setTextView(TextView text) {
		this.textView = text;
	}
	
	protected TextView getTextView() {
		return textView;
	}
	
	protected CalculatorState getState() {
		return state;
	}
}
