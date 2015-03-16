package Objects.fieldItems;

import GUI.Board;
import GUI.Map;
import Objects.Element;
import Objects.Items.Item;

public abstract class FieldItem  extends Element{
	private Item myItem;
	abstract boolean questItem();
	
	public void initItem(double x, double y, Map M, Board B, Item i) {
		this.x = x;
		this.y = y;
		myMap = M;
		myBoard = B;
		myItem = i;
		visible = true;
		image = i.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public Item getItem() {
		return myItem;
	}
}
