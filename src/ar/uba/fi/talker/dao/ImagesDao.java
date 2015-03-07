package ar.uba.fi.talker.dao;

import ar.uba.fi.talker.R;

/*
 * dao to images.
 */

public class ImagesDao {
	
	private static ImagesDao instance = null;
	
	public static ImagesDao getInstance() {
	      if(instance == null) {
	         instance = new ImagesDao();
	      }
	      return instance;
	}
	
	private int positionDao;
	
	// references to our images
	public Integer getInnerScenarioImageByPos(int position) {
		Integer id = null;
		switch (getPositionDao()) {
		case 0:
			id = getHouseInnerScenarioImageByPos(position);
			break;
		case 3:
			id = getSchoolInnerScenarioImageByPos(position);
			break;
		default:
			break;
		}
		return id;
	}

	public Integer getInnerScenarioNameByPos(int position) {
		Integer id = null;
		switch (getPositionDao()) {
		case 0:
			id = getHouseInnerScenarioNameByPos(position);
			break;
		case 3:
			id = getSchoolInnerScenarioNameByPos(position);
			break;
		default:
			break;
		}
		return id;
	}

	public int getInnerScenarioSize() {
		int size = 0;
		switch (getPositionDao()) {
		case 0:
			size = getHouseInnerScenarioSize();
			break;
		case 3:
			size = getSchoolInnerScenarioSize();
			break;
		default:
			break;
		}
		return size;
	}
	
	private Integer[] mThumbIdsHouseInnerScenario = {
            R.drawable.living,
            R.drawable.cocina,
            R.drawable.habitacion, 
            R.drawable.banio
    };
    
    private static Integer[] mTextsIdsHouseInnerScenario = {
    		R.string.living,
    		R.string.cocina,
    		R.string.habitacion,
    		R.string.banio
    };
    
    
	public Integer getHouseInnerScenarioImageByPos(int position) {
		return mThumbIdsHouseInnerScenario[position];
	}
	
	public static Integer getHouseInnerScenarioNameByPos(int position) {
		return mTextsIdsHouseInnerScenario[position];
	}

	public int getHouseInnerScenarioSize() {
		return mThumbIdsHouseInnerScenario.length;
	}
	
	
	/*IMAGES OF SCENARIO*/
    private static Integer[] mThumbIdsScenario = {
            R.drawable.casa,
            R.drawable.oficina,
            R.drawable.colectivo, 
            R.drawable.escuela,
            R.drawable.blanco,
            R.drawable.living,
            R.drawable.cocina,
            R.drawable.habitacion, 
            R.drawable.banio,
            R.drawable.aulaescuela,
            R.drawable.banioescuela,
            R.drawable.patioescuela
    };
    
    private static Integer[] mTextsIdsScenario = {
    		R.string.casa,
    		R.string.oficina,
    		R.string.colectivo,
    		R.string.escuela,
    		R.string.blanco,
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
	
	
	/*IMAGES OF SCHOOL*/
    private static Integer[] mThumbIdsSchoolInnerScenario = {
            R.drawable.aulaescuela,
            R.drawable.banioescuela,
            R.drawable.patioescuela
    };
    
    private static Integer[] mTextsIdsSchoolInnerScenario = {
    		R.string.aula,
    		R.string.banio,
    		R.string.patio
    };
    
	public static Integer getSchoolInnerScenarioImageByPos(int position) {
		return mThumbIdsSchoolInnerScenario[position];
	}
	
	public static Integer getSchoolInnerScenarioNameByPos(int position) {
		return mTextsIdsSchoolInnerScenario[position];
	}

	public static int getSchoolInnerScenarioSize() {
		return mThumbIdsSchoolInnerScenario.length;
	}

	public int getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(int positionDao) {
		this.positionDao = positionDao;
	}
	
}