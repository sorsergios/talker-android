package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.R;

/*
 * DAO for default images
 */

public class ImagesDao {
	
	private static ImagesDao instance = null;
	
	public static ImagesDao getInstance() {
	      if(instance == null) {
	         instance = new ImagesDao();
	      }
	      return instance;
	}
		
	/*IMAGES OF SCENARIO*/
    private static Integer[] mThumbIdsScenario = {
    		R.drawable.blanco,    
    		R.drawable.casa,
            R.drawable.oficina,
            R.drawable.colectivo, 
            R.drawable.escuela,
            R.drawable.living,
            R.drawable.cocina,
            R.drawable.habitacion, 
            R.drawable.banio,
            R.drawable.aulaescuela,
            R.drawable.banioescuela,
            R.drawable.patioescuela
    };
    
    private static Integer[] mTextsIdsScenario = {
    		R.string.blanco,
    		R.string.casa,
    		R.string.oficina,
    		R.string.colectivo,
    		R.string.escuela,
    		R.string.living,
    		R.string.cocina,
    		R.string.habitacion,
    		R.string.banio,
    		R.string.aula,
    		R.string.banio,
    		R.string.patio
    };
    
	public static Integer getScenarioImageByPos(int position) {
		return mThumbIdsScenario[position];
	}
	
	public static Integer getScenarioNameByPos(int position) {
		return mTextsIdsScenario[position];
	}

	public static int getScenarioSize() {
		return mThumbIdsScenario.length;
	}
	
}