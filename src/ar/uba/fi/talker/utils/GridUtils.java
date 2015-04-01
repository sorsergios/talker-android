package ar.uba.fi.talker.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import ar.uba.fi.talker.fragment.GridFragment;
import ar.uba.fi.talker.fragment.OutdoorScenarioDialogFragment;
import ar.uba.fi.talker.fragment.ScenesGridFragment;

public final class GridUtils {

	public static List<GridFragment> setGridFragments(Activity activity, ArrayList<Category> a) {

		Iterator<Category> it = a.iterator();

		List<GridFragment> gridFragments = new ArrayList<GridFragment>();

		ArrayList<GridItems> itmLst = new ArrayList<GridItems>();
		while (it.hasNext()) {

			for (int i = 0; i < 6 && it.hasNext(); i++) {
				GridItems itm = new GridItems(i, it.next());
				itmLst.add(itm);
			}
			
			GridItems[] gridPage = itmLst.toArray(new GridItems[]{});
			itmLst.clear();
			gridFragments.add(new GridFragment(gridPage, activity));
		}
		return gridFragments;
	}
	
	public static List<ScenesGridFragment> setScenesGridFragments(Activity activity, ArrayList<Category> categories, OutdoorScenarioDialogFragment parent) {

		Iterator<Category> it = categories.iterator();

		List<ScenesGridFragment> gridFragments = new ArrayList<ScenesGridFragment>();

		while (it.hasNext()) {
			ArrayList<GridItems> itmLst = new ArrayList<GridItems>();
			for (int i = 0; i < 6 && it.hasNext(); i++) {
				GridItems itm = new GridItems(i, it.next());
				itmLst.add(itm);
			}
			gridFragments.add(new ScenesGridFragment(itmLst, activity, parent));
		}
		return gridFragments;
	}
}
