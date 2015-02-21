package ar.fi.uba.talker.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import ar.fi.uba.androidtalker.fragment.GridFragment;
import ar.fi.uba.androidtalker.fragment.OutdoorScenarioDialogFragment;
import ar.fi.uba.androidtalker.fragment.ScenesGridFragment;

public final class GridUtils {

	public static List<GridFragment> setGridFragments(Activity activity, ArrayList<Category> a) {

		Iterator<Category> it = a.iterator();

		List<GridFragment> gridFragments = new ArrayList<GridFragment>();

		int i = 0;
		while (it.hasNext()) {
			ArrayList<GridItems> itmLst = new ArrayList<GridItems>();

			GridItems itm = new GridItems(0, it.next());
			itmLst.add(itm);
			i = i + 1;

			if (it.hasNext()) {
				GridItems itm1 = new GridItems(1, it.next());
				itmLst.add(itm1);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm2 = new GridItems(2, it.next());
				itmLst.add(itm2);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm3 = new GridItems(3, it.next());
				itmLst.add(itm3);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm4 = new GridItems(4, it.next());
				itmLst.add(itm4);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm5 = new GridItems(5, it.next());
				itmLst.add(itm5);
				i = i + 1;
			}
			
			GridItems[] gp = {};
			GridItems[] gridPage = itmLst.toArray(gp);
			gridFragments.add(new GridFragment(gridPage, activity));
		}
		return gridFragments;
	}
	
	public static List<ScenesGridFragment> setScenesGridFragments(Activity activity, ArrayList<Category> a, OutdoorScenarioDialogFragment parent) {

		Iterator<Category> it = a.iterator();

		List<ScenesGridFragment> gridFragments = new ArrayList<ScenesGridFragment>();

		int i = 0;
		while (it.hasNext()) {
			ArrayList<GridItems> itmLst = new ArrayList<GridItems>();

			GridItems itm = new GridItems(0, it.next());
			itmLst.add(itm);
			i = i + 1;

			if (it.hasNext()) {
				GridItems itm1 = new GridItems(1, it.next());
				itmLst.add(itm1);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm2 = new GridItems(2, it.next());
				itmLst.add(itm2);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm3 = new GridItems(3, it.next());
				itmLst.add(itm3);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm4 = new GridItems(4, it.next());
				itmLst.add(itm4);
				i = i + 1;
			}

			if (it.hasNext()) {
				GridItems itm5 = new GridItems(5, it.next());
				itmLst.add(itm5);
				i = i + 1;
			}
			
			GridItems[] gp = {};
			GridItems[] gridPage = itmLst.toArray(gp);
			gridFragments.add(new ScenesGridFragment(gridPage, activity, parent));
		}
		return gridFragments;
	}
}
