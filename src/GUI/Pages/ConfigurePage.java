package GUI.Pages;

import java.util.ArrayList;

public class ConfigurePage implements Page{

	private ArrayList<Button> buttons;
	private String title;
	private int tSize = 60;
	public ConfigurePage() {
		title = "Configure controls:";
		buttons = new ArrayList<Button>();
		buttons.add(new MenuButton(100, 150, 0, "left", 40));
		buttons.add(new MenuButton(100, 200, 1, "right", 40));
		buttons.add(new MenuButton(100, 250, 2, "up", 40));
		buttons.add(new MenuButton(100, 300, 3, "down", 40));
		buttons.add(new MenuButton(100, 350, 4, "fire/confirm", 40));
		buttons.add(new MenuButton(100, 400, 5, "cancel", 40));
		buttons.add(new MenuButton(400, 150, 6, "pause", 40));
		buttons.add(new MenuButton(400, 200, -1, "Back to Menu", 50));
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
