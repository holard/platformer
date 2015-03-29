package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import GUI.Pages.Button;
import GUI.Pages.ConfigurePage;
import GUI.Pages.EquipPage;
import GUI.Pages.MenuButton;
import GUI.Pages.MenuPage;
import GUI.Pages.Page;
import Objects.Enemies.Enemy;
import Objects.Items.*;
import Objects.DataCenter;
import Objects.MainChar;
import Objects.NPCs.NPC;
import Objects.NPCs.NPCText;
import Objects.Projectiles.Projectile;
import Objects.Tiles.Tile;
import Objects.fieldItems.FieldItem;

public class Board extends JPanel implements ActionListener {
	// 0 = l, 1 = r, 2 = u, 3 = d, 4 = f, 5 = g, 6 = p
	private Timer timer;
	public static final int CAM_HB = 256;
	public static final int CAM_VB = 160;
	public static final int TIMER = 15;
	public static int GUN_INV_SIZE = 3;
	public static int IG_PAUSE = 0;
	public static int IG_EQUIP = 1;
	public static int IG_ITEMS = 2;
	public static int IG_QUEST = 3;
	public static int IG_CONTROLS = 4;
	public static int UP = KeyEvent.VK_UP;
	public static int LEFT = KeyEvent.VK_LEFT;
	public static int RIGHT = KeyEvent.VK_RIGHT;
	public static int DOWN = KeyEvent.VK_DOWN;
	public static int F = KeyEvent.VK_F;
	public static int G = KeyEvent.VK_G;
	public static int P = KeyEvent.VK_P;

	public static final int HEALTH_PER_HEART = 10;
	public static final int GLOBALX_START = 0;
	public static final int GLOBALY_START = 0;
	public static final String MAPPATH = "Maps/";
	public static final String START_MAPFILE = MAPPATH + "map1";
	public static final int INVINCIBILITY_FLASH_RATE = 50;

	private MainChar craft;
	private ArrayList<Tile> blocks;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Enemy> enemies;
	private ArrayList<NPC> NPCs;
	private ArrayList<FieldItem> fieldItems;
	private ArrayList<Pair<int[], String>> xychart;
	private NPC activeNPC;
	private boolean ingame;
	private Map M;
	private int camx;
	private int camy;
	private int camw;
	private int camh;
	private int globalx;
	private int globaly;
	public boolean paused = false;
	public int menu = 0;
	public ArrayList<Page> menupages;
	public ArrayList<Page> gamepages;
	public ArrayList<Item> storage;
	public int bIndex; // index of button currently selected
	public int toConf = -1;
	Item confirmStore = null;
	private MenusHandler mh;
	public DataCenter data;
	
	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ingame = false;
		setSize(400, 300);
		camx = 0;
		camy = 0;
		camw = MainMethod.WIDTH;
		camh = MainMethod.HEIGHT;

		initGame();
		initMenus();

		timer = new Timer(TIMER, this);
		timer.start();
	}

	public void initGame() {
		craft = new MainChar(this);
		craft.setMyGun(new BasicGun());
		setMap(START_MAPFILE);
		globalx = GLOBALX_START;
		globaly = GLOBALY_START;
		readXYChart();
		activeNPC = null;
		ArrayList<Gun> myGuns;
		data = new DataCenter();
		data.setMain(craft);
		craft.setData(data);
		myGuns = new ArrayList<Gun>();
		mh = new MenusHandler(this);
		myGuns.add(new BubbleBombLauncher());
		myGuns.add(new BubbleGun());
		myGuns.add(new DoubleGun());
		data.setMyGuns(myGuns);
		
		data.setMyBag(new Bag(6));
	}

	public void initMenus() {
		menupages = new ArrayList<Page>();
		gamepages = new ArrayList<Page>();

		ArrayList<Button> mmButtons = new ArrayList<Button>();
		mmButtons.add(new MenuButton(150, 200, -1, "START GAME", 64));
		mmButtons.add(new MenuButton(150, 280, 1, "CREDITS", 64));
		mmButtons.add(new MenuButton(150, 360, 2, "CONFIGURE", 64));
		MenuPage mainMenu = new MenuPage(mmButtons, "MAIN MENU");
		ArrayList<Button> crButtons = new ArrayList<Button>();
		crButtons.add(new MenuButton(100, 200, 0, "BACK TO MAIN MENU", 48));
		MenuPage credits = new MenuPage(crButtons, "CREDITS: Poop.");
		ConfigurePage confPage = new ConfigurePage();
		ArrayList<Button> pausepage = new ArrayList<Button>();
		ArrayList<Button> itempage = new ArrayList<Button>();
		MenuPage controls = mh.initControls();
		storage = new ArrayList<Item>();
		gamepages.add(new MenuPage(pausepage, "Pause"));
		gamepages.add(new EquipPage());
		gamepages.add(new MenuPage(itempage, "Items"));
		gamepages.add(new MenuPage(new ArrayList<Button>(), "Quests"));
		gamepages.add(controls);
		menupages.add(mainMenu);
		menupages.add(credits);
		menupages.add(confPage);

		bIndex = 0;
	}

	/*
	 * Format for xychart.txt: sample line: <x> <y> <width> <height> <map
	 * filename> x and y are the map's global position.
	 */
	public void readXYChart() {
		ArrayList<Pair<int[], String>> chart = new ArrayList<Pair<int[], String>>();
		try {
			BufferedReader BR = new BufferedReader(
					new FileReader("xychart.txt"));
			String line = BR.readLine();
			String[] elems;
			int[] xywh;
			while (line != null) {
				elems = line.split(" ");
				xywh = new int[4];
				xywh[0] = Integer.parseInt(elems[0]);
				xywh[1] = Integer.parseInt(elems[1]);
				xywh[2] = Integer.parseInt(elems[2]);
				xywh[3] = Integer.parseInt(elems[3]);
				chart.add(new Pair<int[], String>(xywh, elems[4]));
				line = BR.readLine();
			}

			BR.close();
			xychart = chart;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Change map depending on what code is 0: change because of main char
	 */
	public void changeMap(int code) {
		String newfile = "no file!";

		if (code == 0) {
			int char_globalx = globalx + craft.getX() + craft.getWidth() / 2;
			int char_globaly = globaly + craft.getY() + craft.getHeight() / 2;

			// Find the map containing the out of bounds character
			Pair<int[], String> newMap;
			int[] new_xywh = new int[4];
			for (int i = 0; i < xychart.size(); i++) {
				newMap = xychart.get(i);
				new_xywh = newMap.first();
				if (char_globalx >= new_xywh[0] && char_globaly >= new_xywh[1]
						&& char_globalx < new_xywh[0] + new_xywh[2]
						&& char_globaly < new_xywh[2] + new_xywh[3]) {
					newfile = newMap.second();
					break;
				}
			}

			if (newfile == "no file!")
				return;

			// Set the character's new relative x and y
			craft.setX(craft.getX() + (globalx - new_xywh[0]));
			craft.setY(craft.getY() + (globaly - new_xywh[1]));
			craft.checkPoint();
			if (new_xywh[0] == globalx && new_xywh[1] == globaly) {
				return;
			}
			// Fix globalx, globaly
			globalx = new_xywh[0];
			globaly = new_xywh[1];
		}

		setMap(MAPPATH + newfile);
	}

	public void setMap(String file) {
		MapReader MR = new MapReader(file + "/tiles.txt");
		blocks = MR.makeMap();
		M = new Map(blocks);
		craft.myMap = M;

		ObjectReader OR = new ObjectReader(file + "/objects.txt", M, this);
		enemies = OR.makeEnemies();
		NPCs = OR.makeNPCs();
		fieldItems = new ArrayList<FieldItem>();
		projectiles = new ArrayList<Projectile>(40000);
	}
	
	public void setNPC(NPC n) {
		System.out.println("setNPC");
		activeNPC = n;
	}

	// Returns enemy at x and y, if none return null
	public Enemy checkEnemy(double x, double y) {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if ((x >= e.getX()) && (y >= e.getY())
					&& (x < e.getX() + e.getWidth())
					&& (y < e.getY() + e.getHeight()))
				return e;
		}
		return null;
	}
	
	// Returns NPC at x and y, if none return null
	public NPC checkNPC(double x, double y) {
		for (int i = 0; i < NPCs.size(); i++) {
			NPC n = NPCs.get(i);
			if ((x >= n.getX()) && (y >= n.getY())
					&& (x < n.getX() + n.getWidth())
					&& (y < n.getY() + n.getHeight()))
				return n;
		}
		return null;
	}

	public void updateCam() {
		if (craft.getX() < camx + CAM_HB) {
			camx = Math.max(0, craft.getX() - CAM_HB);
		}
		if (craft.getX() + craft.getWidth() > camx + camw - CAM_HB) {
			camx = Math.min(M.getWidth() - camw, craft.getX() + CAM_HB - camw
					+ craft.getWidth());
		}
		if (craft.getY() < camy + CAM_VB) {
			camy = Math.max(0, craft.getY() - CAM_VB);
		}
		if (craft.getY() + craft.getHeight() > camy + camh - CAM_VB) {
			camy = Math.min(M.getHeight() - camh, craft.getY() + CAM_VB - camh
					+ craft.getHeight());
		}
	}

	public void handleGunBar(Graphics2D g2d) {
		if (craft.isVisible()) {
			g2d.setColor(Color.WHITE);
			g2d.drawRect(40, 40, 102, 12);
			g2d.setColor(Color.LIGHT_GRAY);
			double rat = craft.getMyGun().reloadRatio();

			g2d.fillRect(41, 41, (int) (100 * rat), 10);
			if (craft.getMyGun().charged() && rat >= 1) {
				g2d.setColor(Color.BLUE);
				g2d.fillRect(41, 41, (int) (100 * craft.getMyGun()
						.chargeRatio()), 10);
			}
		}
	}

	public void handleHealthBar(Graphics2D g2d) {
		if (craft.isVisible()) {
			g2d.setColor(Color.PINK);

			for (int i = 0; i < craft.getHealth() / HEALTH_PER_HEART; i++) {
				g2d.fillRect(40 + 16 * i, 20, 16, 16);
			}
			g2d.fillRect(
					40 + 16 * (int) (craft.getHealth() / HEALTH_PER_HEART), 20,
					(int) ((double) (16 / (double) HEALTH_PER_HEART) * (craft
							.getHealth() % HEALTH_PER_HEART)), 16);
			g2d.setColor(Color.RED);
			for (int i = 0; i < craft.getMaxhealth() / HEALTH_PER_HEART; i++) {
				g2d.drawRect(40 + 16 * i, 20, 16, 16);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		if (ingame) { // pause page or in game

			if (menu <= 0) {
				updateCam();

				if (craft.isVisible()
						&& (craft.getInvince() / INVINCIBILITY_FLASH_RATE) % 4 != 1) {
					g2d.drawImage(craft.getImage(), craft.getX() - camx,
							craft.getY() - camy, this);
					if (craft.getMyGun() != null && craft.getStick() == 0
							&& craft.getHang() == null) {
						Item i = craft.getMyGun();
						g2d.drawImage(i.getImage(), craft.getX() - camx
								+ i.xOffset, craft.getY() - camy + i.yOffset,
								this);
					}
				}

				for (int i = 0; i < blocks.size(); i++) {
					Tile a = (Tile) blocks.get(i);
					if (a.isVisible())
						g2d.drawImage(a.getImage(), a.getX() - camx, a.getY()
								- camy, this);
				}

				for (int i = 0; i < projectiles.size(); i++) {
					Projectile p = (Projectile) projectiles.get(i);
					if (p.isVisible())
						g2d.drawImage(p.getImage(), p.getX() - camx, p.getY()
								- camy, this);
				}

				for (int i = 0; i < fieldItems.size(); i++) {
					FieldItem fI = fieldItems.get(i);
					if (fI.isVisible())
						g2d.drawImage(fI.getItem().getImage(), fI.getX() - camx, fI.getY()
								- camy, this);
				}

				
				for (int i = 0; i < enemies.size(); i++) {
					Enemy e = enemies.get(i);
					if (e.isVisible())
						g2d.drawImage(e.getImage(), e.getX() - camx, e.getY()
								- camy, this);
					e.drawHealth(g2d, camx, camy);
				}
				
				for (int i = 0; i < NPCs.size(); i++) {
					NPC n = NPCs.get(i);
					if (n.isVisible())
						g2d.drawImage(n.getImage(), n.getX() - camx, n.getY()
								- camy, this);
				}
				
				if (activeNPC != null) {
					activeNPC.draw(g2d);
				}
				data.drawNotification(g2d);
				handleGunBar(g2d);
				handleHealthBar(g2d);
			}
			if (paused) {
				if (menu == IG_PAUSE) { // Pause page
					mh.handlePausePage(g2d);
				}

				if (menu == IG_EQUIP) { // equip page
					mh.handleEquipPage(g2d);
				}
				
				if (menu == IG_ITEMS) { // items page
					mh.handleItemPage(g2d);
				}
				
				if (menu == IG_QUEST) { // quest page
					mh.handleQuestPage(g2d);
				}

				if (menu == IG_CONTROLS) { // controls page
					mh.handleControlsPage(g2d);
				}
				
				mh.handleIGBar(g2d);

			}
			g2d.setColor(Color.WHITE);
		} else {
			mh.handleMenus(g2d);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {

		if (menu == -1) {
			ingame = true;
		}
		if (paused) {
			repaint();
			return;
		}

		craft.move();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.move();
		}
		for (int i = 0; i < enemies.size(); i++) {
			Enemy e1 = enemies.get(i);
			e1.move();
		}
		repaint();
	}

	public void handleConfig(int key) {
		if (toConf == -1) {
			if (key == UP) {
				if (bIndex > 0) {
					bIndex -= 1;
				} else {
					bIndex = menupages.get(menu).getButtons().size() - 1;
				}
			}
			if (key == DOWN) {
				ArrayList<Button> curButtons = menupages.get(menu).getButtons();
				if (bIndex < curButtons.size() - 1) {
					bIndex += 1;
				} else {
					bIndex = 0;
				}
			}
			if (key == F) {
				toConf = menupages.get(menu).getButtons().get(bIndex)
						.getDestination();
				if (toConf == -1) {
					bIndex = 0;
					menu = 0;
				}
				return;
			}
		} else {
			switch (toConf) {
			case 0:
				LEFT = key;
				break;
			case 1:
				RIGHT = key;
				break;
			case 2:
				UP = key;
				break;
			case 3:
				DOWN = key;
				break;
			case 4:
				F = key;
				break;
			case 5:
				G = key;
				break;
			case 6:
				P = key;
				break;
			}

			toConf = -1;
		}
	}

	public void initEquips() {
		if (menu == 1) {
			EquipPage ep = (EquipPage) gamepages.get(1);
			ArrayList<Item> temp = new ArrayList<Item>(data.getMyGuns());
			ep.setItems(craft.getMyGun(), temp);
		}
	}

	public void handleEquipPage(int key) {
		ArrayList<Gun> myGuns = data.getMyGuns();
		if (confirmStore == null) {
			if (key == F) {

				if (myGuns.size() > 0) {
					Gun n = myGuns.remove(bIndex);
					craft.getMyGun().setImage(craft.getMyGun().getRightImage());
					myGuns.add(craft.getMyGun());
					craft.setMyGun(n);
					if (craft.getDirection() == 1)
						craft.getMyGun().setImage(
								craft.getMyGun().getRightImage());
					else
						craft.getMyGun().setImage(
								craft.getMyGun().getLeftImage());

					initEquips();
				}

			}
			if (key == G) {
				if (myGuns.size() > 0) {
					confirmStore = myGuns.get(bIndex);
				}
			}
		} else {
			if (key == F) {
				if (myGuns.size() > 0) {
					storage.add(confirmStore);
					myGuns.remove(confirmStore);
					if (bIndex > 0) {
						bIndex -= 1;
					} else {
						bIndex = 0;
					}
					initEquips();
				}
				confirmStore = null;

			}
			if (key == G) {
				confirmStore = null;
			}

		}
	}

	public void handleInGame(int key) {
		if (key == P) {
			paused = !paused;
			bIndex = 0;
			if (menu == -1) {
				menu = 0;
			} else {
				menu = -1;
			}
			craft.releaseAll();
			
		} else if (paused) { // in game menu
			if (key == LEFT) {
				bIndex = 0;
				if (menu > 0) {
					menu -= 1;
				} else
					menu = gamepages.size() - 1;
				if (menu == 1)
					initEquips();
				
			} else if (key == RIGHT) {
				bIndex = 0;
				if (menu < gamepages.size() - 1) {
					menu += 1;
				} else
					menu = 0;
				if (menu == 1)
					initEquips();
			}
			
			if (menu != 0) { // navigable pages
				if (key == UP) {
					if (bIndex > 0) {
						bIndex -= 1;
					} else {
						bIndex = gamepages.get(menu).getButtons().size() - 1;
					}
				}
				if (key == DOWN) {
					ArrayList<Button> curButtons = gamepages.get(menu)
							.getButtons();
					if (bIndex < curButtons.size() - 1) {
						bIndex += 1;
					} else {
						bIndex = 0;
					}
				}
				if (menu == 1) { // EQUIP PAGE
					handleEquipPage(key);
				}
			}
			
		} else if (activeNPC != null) { //Ingame and not paused
			activeNPC.interact(key);
		} else {
			craft.keyPressed(key);
		}
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public ArrayList<FieldItem> getFieldItems() {
		return fieldItems;
	}
	
	public MainChar getMain() {
		return craft;
	}

	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			if (ingame && !paused && activeNPC == null)
				craft.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (!ingame) {
				if (menu != -1) // -1 is in game
				{
					if (menu == 2) {// special case: configure page
						handleConfig(key);
					} else {
						if (key == UP) {
							if (bIndex > 0) {
								bIndex -= 1;
							} else {
								bIndex = menupages.get(menu).getButtons()
										.size() - 1;
							}
						}
						if (key == DOWN) {
							ArrayList<Button> curButtons = menupages.get(menu)
									.getButtons();
							if (bIndex < curButtons.size() - 1) {
								bIndex += 1;
							} else {
								bIndex = 0;
							}
						}
						if (key == F) {
							menu = menupages.get(menu).getButtons().get(bIndex)
									.getDestination();
							bIndex = 0;
						}
					}
				}
			} else { // ingame == true
				handleInGame(key);
			}
				
		}
	}
}