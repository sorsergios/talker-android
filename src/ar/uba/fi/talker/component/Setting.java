package ar.uba.fi.talker.component;

public class Setting {

	private int textColor;
	private int pencilColor;
	private float pencilSize;
	private float eraserSize;
	private Boolean isEnabledLabelImage;
	private Boolean isEnabledLabelContact;

	public float getPencilSize() {
		return pencilSize;
	}

	public void setPencilSize(float pencilSize) {
		this.pencilSize = pencilSize;
	}

	public int getPencilColor() {
		return pencilColor;
	}

	public void setPencilColor(int pencilColor) {
		this.pencilColor = pencilColor;
	}

	public Boolean getIsEnabledLabelImage() {
		return isEnabledLabelImage;
	}

	public void setIsEnabledLabelImage(Boolean isEnabledLabelImage) {
		this.isEnabledLabelImage = isEnabledLabelImage;
	}

	public Boolean getIsEnabledLabelContact() {
		return isEnabledLabelContact;
	}

	public void setIsEnabledLabelContact(Boolean isEnabledLabelContact) {
		this.isEnabledLabelContact = isEnabledLabelContact;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public float getEraserSize() {
		return eraserSize;
	}

	public void setEraserSize(float eraserSize) {
		this.eraserSize = eraserSize;
	}

}
