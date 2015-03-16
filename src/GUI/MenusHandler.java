package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import GUI.Pages.Button;
import GUI.Pages.EquipPage;
import GUI.Pages.ItemButton;
import GUI.Pages.MenuButton;
import GUI.Pages.MenuPage;
import GUI.Pages.Page;
import Objects.DataCenter;
import Objects.Items.Bag;
import Objects.Items.Gun;
import Objects.Items.Item;

public class MenusHandler {
	public static int ITEMS_PER_ROW = 6;

	Board b;
	ArrayList<Page> gamepages;
	ArrayList<Page> menupages;
	int menu;
	int bIndex;
	Item confirmStore;
	int toConf;
	DataCenter data;

	public MenusHandler(Board myb) {
		b = myb;
	}

	public void setUp() {
		gamepages = b.gamepages;
		menupages = b.menupages;
		menu = b.menu;
		bIndex = b.bIndex;
		confirmStore = b.confirmStore;
		toConf = b.toConf;
		data = b.data;
	}

	public MenuPage initControls() {
		ArrayList<Button> buttons = new ArrayList<Button>();
		buttons.add(new MenuButton(100, 150, 0, "move left", 40));
		buttons.add(new MenuButton(100, 200, 1, "move right", 40));
		buttons.add(new MenuButton(100, 250, 2, "jump", 40));
		buttons.add(new MenuButton(100, 300, 3, "slide/drop", 40));
		buttons.add(new MenuButton(100, 350, 4, "fire/confirm", 40));
		buttons.add(new MenuButton(100, 400, 5, "cancel", 40));
		buttons.add(new MenuButton(400, 150, 6, "pause", 40));
		return new MenuPage(buttons, "Controls");
	}

	public void handleControlsPage(Graphics2D g2d) {
		setUp();
		g2d.setColor(Color.WHITE);
		Page curMenu = gamepages.get(Board.IG_CONTROLS);

		ArrayList<Button> curButtons = curMenu.getButtons();
		g2d.setFont(new Font("SERIF", 0, curMenu.gettSize()));

		for (int i = 0; i < curButtons.size(); i++) {
			g2d.setColor(Color.WHITE);
			Button b = curButtons.get(i);
			g2d.setFont(new Font("SERIF", 0, b.getSize()));
			String toDraw = b.getName();
			int offset = 0;
			if (b.getDestination() != -1) {
				int wut = b.getDestination();
				String toAdd = "";
				switch (wut) {
				case 0:
					toAdd = KeyEvent.getKeyText(Board.LEFT);
					break;
				case 1:
					toAdd = KeyEvent.getKeyText(Board.RIGHT);
					break;
				case 2:
					toAdd = KeyEvent.getKeyText(Board.UP);
					break;
				case 3:
					toAdd = KeyEvent.getKeyText(Board.DOWN);
					break;
				case 4:
					toAdd = KeyEvent.getKeyText(Board.F);
					break;
				case 5:
					toAdd = KeyEvent.getKeyText(Board.G);
					break;
				case 6:
					toAdd = KeyEvent.getKeyText(Board.P);
					break;
				}
				toDraw = toDraw + "[" + toAdd + "]";
				if (toConf != -1 && toDraw.charAt(0) == '>')
					g2d.setColor(Color.CYAN);
			}
			g2d.drawString(toDraw, b.getX() + offset, b.getY());

		}
	}

	public void handleEquipPage(Graphics2D g2d) {
		setUp();
		g2d.setFont(new Font("SERIF", 0, 20));

		g2d.setColor(Color.WHITE);

		EquipPage ep = (EquipPage) gamepages.get(1);
		ArrayList<Button> curButtons = ep.getButtons();
		g2d.drawImage(ep.getCurrent().getImage(), 60, 40, b);
		g2d.drawString(ep.getCurrent().getName(), 160, 40);
		g2d.drawString(ep.getCurrent().getDescription(), 160, 80);
		g2d.drawString("press [" + KeyEvent.getKeyText(Board.F)
				+ "] to equip, and [" + KeyEvent.getKeyText(Board.G)
				+ "] to store.", 300, 200);
		if (confirmStore != null) {
			g2d.drawString(
					"Store " + confirmStore.getName() + "? Yes["
							+ KeyEvent.getKeyText(Board.F) + "] / No["
							+ KeyEvent.getKeyText(Board.G) + "]", 300, 300);
		}
		g2d.setColor(Color.YELLOW);
		g2d.drawString(ep.getButtons().size() + "/" + Board.GUN_INV_SIZE, 500,
				40);
		g2d.setColor(Color.CYAN);

		for (int i = 0; i < curButtons.size(); i++) {
			ItemButton ib = (ItemButton) (curButtons.get(i));
			g2d.drawImage(ib.getItem().getImage(), ib.getX(), ib.getY(), b);
			if (i == bIndex) {
				g2d.drawRect(ib.getX() - 3, ib.getY() - 3, ib.getItem()
						.getImage().getWidth(null) + 6, ib.getItem().getImage()
						.getHeight(null) + 6);
				g2d.drawString(ib.getItem().getName(), ib.getX() + 48,
						ib.getY() + 20);
			}
		}
	}

	public void handlePausePage(Graphics2D g2d) {
		setUp();

		g2d.setFont(new Font("SERIF", 0, 100));
		g2d.setColor(Color.WHITE);
		g2d.drawString("PAUSED [" + KeyEvent.getKeyText(Board.P) + "]", 128,
				200);

	}

	public void handleItemPage(Graphics2D g2d) {
		setUp();
		Bag b = data.getMyBag();
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("SERIF",0,48));
		g2d.drawString("Inventory:", 60, 60);
		b.draw(g2d, 100, 100, ITEMS_PER_ROW);
	}

	public void handleIGBar(Graphics2D g2d) {
		setUp();
		g2d.setFont(new Font("SERIF", 0, 24));
		for (int i = 0; i < gamepages.size(); i++) {
			int len = gamepages.get(i).getTitle().length();
			if (i == menu) {
				g2d.setColor(Color.WHITE);
				g2d.fillRect(60 + i * 120, 400, 120, 60);
				g2d.setColor(Color.BLACK);
				g2d.drawString(gamepages.get(i).getTitle(), 120 + i * 120 - len
						* 5, 420);
			} else {
				g2d.setColor(Color.DARK_GRAY);
				g2d.fillRect(60 + i * 120, 400, 120, 60);
				g2d.setColor(Color.WHITE);
				g2d.drawString(gamepages.get(i).getTitle(), 120 + i * 120 - len
						* 5, 420);
			}
		}
	}

	public void handleMenus(Graphics2D g2d) {
		setUp();

		if (menu == -1) {
			return;
		}
		g2d.setColor(Color.WHITE);
		Page curMenu = menupages.get(menu);
		ArrayList<Button> curButtons = curMenu.getButtons();
		g2d.setFont(new Font("SERIF", 0, curMenu.gettSize()));
		g2d.drawString(curMenu.getTitle(), 60, 100);
		for (int i = 0; i < curButtons.size(); i++) {
			g2d.setColor(Color.WHITE);
			Button b = curButtons.get(i);
			g2d.setFont(new Font("SERIF", 0, b.getSize()));
			String toDraw = b.getName();
			int offset = 0;
			if (i == bIndex) {
				toDraw = ">" + toDraw;
				offset = -b.getSize() / 2;
			}
			if (menu != 2) {
				g2d.drawString(toDraw, b.getX() + offset, b.getY());
			} else {
				if (b.getDestination() != -1) {
					int wut = b.getDestination();
					String toAdd = "";
					switch (wut) {
					case 0:
						toAdd = KeyEvent.getKeyText(Board.LEFT);
						break;
					case 1:
						toAdd = KeyEvent.getKeyText(Board.RIGHT);
						break;
					case 2:
						toAdd = KeyEvent.getKeyText(Board.UP);
						break;
					case 3:
						toAdd = KeyEvent.getKeyText(Board.DOWN);
						break;
					case 4:
						toAdd = KeyEvent.getKeyText(Board.F);
						break;
					case 5:
						toAdd = KeyEvent.getKeyText(Board.G);
						break;
					case 6:
						toAdd = KeyEvent.getKeyText(Board.P);
						break;
					}
					toDraw = toDraw + "[" + toAdd + "]";
					if (toConf != -1 && toDraw.charAt(0) == '>')
						g2d.setColor(Color.CYAN);
				}
				g2d.drawString(toDraw, b.getX() + offset, b.getY());
			}
		}
	}

}
