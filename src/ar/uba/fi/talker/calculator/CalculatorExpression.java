package ar.uba.fi.talker.calculator;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public abstract class CalculatorExpression implements OnClickListener {
	
	private TextView textView;

	public abstract double getValue();
	
	public abstract void execute(TextView text);

	public abstract void addValue(double value);
	
	public abstract String toString();

	public void setSecuence(StringBuilder secuence) {
	}
	
	@Override
	public void onClick(View v) {
		Button button = (Button) v;
		CharSequence text = button.getText();
		textView.append(text);
	}

	public void setSecuence(TextView text) {
		this.textView = text;
	}
	
}
