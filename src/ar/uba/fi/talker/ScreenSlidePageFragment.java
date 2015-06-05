package ar.uba.fi.talker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ScreenSlidePageFragment extends Fragment {

	private final Integer position;

	public ScreenSlidePageFragment(Integer position) {
		this.position = position;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.manner_of_use_slide_page, container, false);
		ImageView view = (ImageView) rootView.findViewById(R.id.mannerOfUseSlide);

		switch (position) {
		case 0:
			view.setImageResource(R.drawable.abc_ab_bottom_solid_dark_holo);
			break;
		case 1:
			view.setImageResource(R.drawable.contact_icon);
			break;
		case 2:
			view.setImageResource(R.drawable.calculator_icon);
			break;

		default:
			break;
		}

		return rootView;
	}
}