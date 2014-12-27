package ar.fi.uba.androidtalker.dao;

import ar.fi.uba.androidtalker.R;

/*
 * dao to images.
 */
public class ImagesDao {
	
	// references to our images
    private static Integer[] mThumbIds = {
            R.drawable.living,
            R.drawable.cocina,
            R.drawable.habitacion, 
            R.drawable.banio
    };
    
    private static Integer[] mTextsIds = {
    		R.string.living,
    		R.string.cocina,
    		R.string.habitacion,
    		R.string.banio
    };
    
	public static Integer getInnerScenarioImageByPos(long itemSelectedId, int position) {
		return mThumbIds[position];
	}
	
	public static Integer getInnerScenarioNameByPos(long itemSelectedId, int position) {
		return mTextsIds[position];
	}

	public static int getInnerScenarioSize() {
		return mThumbIds.length;
	}
}
