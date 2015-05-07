package ar.uba.fi.talker.dao;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import ar.uba.fi.talker.CanvasActivity;
import ar.uba.fi.talker.R;
import ar.uba.fi.talker.component.Setting;

public class TalkerSettingManager {

	public static Setting getSettings(CanvasActivity canvasActivity) {

		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(canvasActivity);

		Setting setting = new Setting();
		sharedPref.getAll();
		int textColor = sharedPref.getInt(canvasActivity.getResources().getString(R.string.settings_text_color_key), Color.BLACK);
		setting.setTextColor(textColor);
		String textSize = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_text_size_key), "100");
		setting.setTextSize(Integer.parseInt(textSize));
		String textWidth = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_text_width_key), "10");
		setting.setTextWidth(Integer.parseInt(textWidth));
		int pencilColor = sharedPref.getInt(canvasActivity.getResources().getString(R.string.settings_pencil_color_key), Color.BLUE);
		setting.setPencilColor(pencilColor);
		String pencilSize = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_pencil_size_key), "20");
		setting.setPencilSize(Integer.parseInt(pencilSize));
		String eraserSize = sharedPref.getString(canvasActivity.getResources().getString(R.string.settings_eraser_size_key), "30");
		setting.setEraserSize(Integer.parseInt(eraserSize));
		Boolean isEnabledLabelImage = sharedPref.getBoolean(canvasActivity.getResources().getString(R.string.settings_image_tag_key),
				Boolean.FALSE);
		setting.setIsEnabledLabelImage(isEnabledLabelImage);
		Boolean isEnabledLabelContact = sharedPref.getBoolean(canvasActivity.getResources().getString(R.string.settings_contact_tag_key),
				Boolean.FALSE);
		setting.setIsEnabledLabelContact(isEnabledLabelContact);

		return setting;
	}

}
