package ar.uba.fi.talker.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import ar.uba.fi.talker.fragment.GridFragment;
import ar.uba.fi.talker.fragment.ScenesGridFragment;

public final class GridUtils {

	public static List<GridFragment> setGridFragments(Activity activity, ArrayList<ScenarioView> a) {

		Iterator<ScenarioView> it = a.iterator();

		List<GridFragment> gridFragments = new ArrayList<GridFragment>();
		int maxImages = calculateImagesPerPage(activity);
		ArrayList<GridItems> itmLst = new ArrayList<GridItems>();
		while (it.hasNext()) {

			for (int i = 0; i < maxImages && it.hasNext(); i++) {
				GridItems itm = new GridItems(i, it.next());
				itmLst.add(itm);
			}
			
			GridItems[] gridPage = itmLst.toArray(new GridItems[]{});
			itmLst.clear();
			gridFragments.add(new GridFragment(gridPage, activity));
		}
		return gridFragments;
	}
	
	public static List<ScenesGridFragment> setScenesGridFragments(Activity activity, ArrayList<ScenarioView> scenarioViews) {

		Iterator<ScenarioView> it = scenarioViews.iterator();
		int maxImages = calculateImagesPerPage(activity);
		List<ScenesGridFragment> gridFragments = new ArrayList<ScenesGridFragment>();

		while (it.hasNext()) {
			ArrayList<GridItems> itmLst = new ArrayList<GridItems>();
			for (int i = 0; i < maxImages && it.hasNext(); i++) {
				GridItems itm = new GridItems(i, it.next());
				itmLst.add(itm);
			}
			gridFragments.add(new ScenesGridFragment(itmLst, activity));
		}
		return gridFragments;
	}

	private static int calculateImagesPerPage(Activity activity) {
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		float density = activity.getResources().getDisplayMetrics().density;
		int maxImages = (int) ((height / (300*density)) * (width / (300*density)));
		Log.d(""+density, "Densidad");
        Log.d(""+width, "Ancho de la pantalla");
        Log.d(""+height, "Alto de la pantalla");
        Log.d(""+maxImages, "Cantidad de imagenes antes");
        if (maxImages > 6 ){
        	maxImages = 6;
        } else if (maxImages < 2) {
        	maxImages = 2;
        }
        Log.d(""+maxImages, "Cantidad de imagenes despues");
		return maxImages;
	}
}
