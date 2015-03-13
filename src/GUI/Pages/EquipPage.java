package GUI.Pages;

import java.util.ArrayList;

import Objects.Items.Item;

public class EquipPage implements Page {

	private ArrayList<Button> buttons;
	private Item current;
	private String title;
	private int tSize = 60;

	public EquipPage() {
		title = "Equips";

	}

	public void setItems(Item curr, ArrayList<Item> items) {
		current = curr;
		buttons = new ArrayList<Button>();
		for (int i = 0; i <= items.size() / 6; i++) {
			for (int j = 0; j < 6 && i * 6 + j < items.size(); j++) {
				buttons.add(new ItemButton(100 + i * 120, 100 + j * 48, -1,
						items.get(i * 6 + j), 24));
			}

		}
	}

	public ArrayList<Button> getButtons() {
		return buttons;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int gettSize() {
		return tSize;
	}

	public void settSize(int tSize) {
		this.tSize = tSize;
	}

	public Item getCurrent() {
		return current;
	}

	public void setCurrent(Item current) {
		this.current = current;
	}
}
