package ar.uba.fi.talker.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import ar.uba.fi.talker.R;
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
		
		float scenarioWidth = activity.getResources().getDimension(R.dimen.scenarioWidth);
		float scenarioHeight = activity.getResources().getDimension(R.dimen.scenarioHeight);
		
		DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

        float dpHeight = displayMetrics.heightPixels / (displayMetrics.densityDpi/160);
        float dpWidth = displayMetrics.widthPixels / (displayMetrics.densityDpi/160);
		
		float maxImagesFloat =  Math.round(dpHeight/scenarioHeight) *  Math.round(dpWidth /scenarioWidth);
		int maxImages = Math.round(maxImagesFloat);

		Log.d(maxImagesFloat+":"+dpHeight+":"+dpWidth+ " width "+ scenarioWidth +" heig " +scenarioHeight, "Densidad");
		return maxImages;
	}
}
