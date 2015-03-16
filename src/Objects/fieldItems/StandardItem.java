package Objects.fieldItems;

import GUI.Board;
import GUI.Map;
import Objects.Items.Item;

public class StandardItem extends FieldItem {
	public StandardItem(int x, int y, Map m, Board b, Item i) {
		super.initItem(x, y, m, b, i);
	}

	@Override
	boolean questItem() {
		return false;
	}
}
