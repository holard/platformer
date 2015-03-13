package Objects.Items;

import java.awt.Image;

import GUI.Board;
import GUI.Map;

public abstract class Item {
	public static final String IMAGE_PATH = "Images/Items/";
	protected Image image;
	public int xOffset;
	public int yOffset;
	
	public abstract String getName();
	public abstract String getDescription();
	public abstract boolean isFireable();
	public abstract Image getImage();
	public abstract boolean isConsumable();
	public abstract boolean isWearable();
}
