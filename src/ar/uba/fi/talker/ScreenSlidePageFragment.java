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
			view.setImageResource(R.drawable.manner_of_use_1_main);
			break;
		case 1:
			view.setImageResource(R.drawable.manner_of_use_2_select_scene);
			break;
		case 2:
			view.setImageResource(R.drawable.manner_of_use_3_select_action);
			break;
		case 3:
			view.setImageResource(R.drawable.manner_of_use_4_canvas);
			break;
		case 4:
			view.setImageResource(R.drawable.manner_of_use_5_history_action);
			break;
		case 5:
			view.setImageResource(R.drawable.manner_of_use_6_config_action);
			break;
		default:
			break;
		}

		return rootView;
	}
}