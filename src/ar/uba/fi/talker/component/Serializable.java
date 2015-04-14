package ar.uba.fi.talker.component;

import org.json.JSONObject;

public interface Serializable {

	public JSONObject save();
	
	public void restore(JSONObject jsonObject);
}
