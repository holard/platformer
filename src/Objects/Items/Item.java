package Objects.Items;

import java.awt.Image;

import GUI.Board;
import GUI.Map;

public abstract class Item {
	protected Image image;
	public int xOffset;
	public int yOffset;
	
	public abstract String getName();
	public abstract String getDescription();
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image ii) {
		image = ii;
	}
	
	public abstract boolean isFireable();
	public abstract boolean isConsumable();
	public abstract boolean isWearable();
}
