package GUI;

import java.util.ArrayList;

public class MenuPage implements Page {

	private ArrayList<Button> buttons;
	private String title;
	private int tSize = 80;
	public MenuPage(ArrayList<Button> b, String t) {
		buttons = b;
		if (b.size() > 3) {
			for (int i = 0; i < 3; i++) {
				b.get(i).setX(80);
				b.get(i).setY(100+80*i);
			}
			for (int i = 3; i < b.size(); i++) {
				b.get(i).setX(480);
				b.get(i).setY(100+80*(i-3));
			}
		}
		setTitle(t);
	}
	public MenuPage(ArrayList<Button> b, String t, int size) {
		buttons = b;
		settSize(size);
		if (b.size() > 3) {
			for (int i = 0; i < 3; i++) {
				b.get(i).setX(80);
				b.get(i).setY(100+80*i);
			}
			for (int i = 3; i < b.size(); i++) {
				b.get(i).setX(480);
				b.get(i).setY(100+80*(i-3));
			}
		}
		setTitle(t);
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
}
