package GUI;

import java.awt.Color;
import java.awt.Font;
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

import Objects.Block;
import Objects.MainChar;
import Objects.Projectile;
import Objects.Tile;

public class Board extends JPanel implements ActionListener {
	// 0 = l, 1 = r, 2 = u, 3 = d, 4 = f, 5 = g, 6 = p
	private Timer timer;
	public static final int CAM_HB = 192;
	public static final int CAM_VB = 128;
	public static final int TIMER = 15;
	public static int UP = KeyEvent.VK_UP;
	public static int LEFT = KeyEvent.VK_LEFT;
	public static int RIGHT = KeyEvent.VK_RIGHT;
	public static int DOWN = KeyEvent.VK_DOWN;
	public static int F = KeyEvent.VK_F;
	public static int G = KeyEvent.VK_G;
	public static int P = KeyEvent.VK_P;
	public static final int GLOBALX_START = 0;
	public static final int GLOBALY_START = 0;
	public static final String START_MAPFILE = "src/GUI/map1.txt";
	private MainChar craft;
	private ArrayList<Tile> blocks;
	private ArrayList<Projectile> projectiles;
	private ArrayList<Pair<int[], String>> xychart;
	private boolean ingame;
	private Map M;
	private int camx;
	private int camy;
	private int camw;
	private int camh;
	private int globalx;
	private int globaly;
	public boolean paused = false;
	private int cursX = 100;
	private int cursY = 200;
	private int menu = 0;
	private ArrayList<Page> menupages;
	private int bIndex;
	private int toConf = -1;

	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		ingame = false;
		setSize(400, 300);
		menupages = new ArrayList<Page>();
		camx = 0;
		camy = 0;
		camw = MainMethod.WIDTH;
		camh = MainMethod.HEIGHT;
		initGame();
		bIndex = 0;
		timer = new Timer(TIMER, this);
		timer.start();
		ArrayList<Button> mmButtons = new ArrayList<Button>();
		/*
		 * g2d.drawString("MAIN MENU", 150, 100); g2d.drawString("START GAME",
		 * 150, 200); g2d.drawString("CREDITS", 150, 300); g2d.drawString(">",
		 * cursX, cursY);
		 */
		mmButtons.add(new Button(150, 200, -1, "START GAME", 64));
		mmButtons.add(new Button(150, 280, 1, "CREDITS", 64));
		mmButtons.add(new Button(150, 360, 2, "CONFIGURE", 64));
		MenuPage mainMenu = new MenuPage(mmButtons, "MAIN MENU");
		ArrayList<Button> crButtons = new ArrayList<Button>();
		crButtons.add(new Button(100, 200, 0, "BACK TO MAIN MENU", 48));
		MenuPage credits = new MenuPage(crButtons, "CREDITS: Poop.");
		ConfigurePage confPage = new ConfigurePage();
		menupages.add(mainMenu);
		menupages.add(credits);
		menupages.add(confPage);
	}

	public void initGame() {
		craft = new MainChar(this);
		setMap(START_MAPFILE);
		globalx = GLOBALX_START;
		globaly = GLOBALY_START;
		readXYChart();
	}

	/*
	 * Format for xychart.txt: sample line: <x> <y> <width> <height> <map
	 * filename> x and y are the map's global position.
	 */
	public void readXYChart() {
		ArrayList<Pair<int[], String>> chart = new ArrayList<Pair<int[], String>>();
		try {
			BufferedReader BR = new BufferedReader(new FileReader(
					"src/GUI/xychart.txt"));
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
			if (new_xywh[0] == globalx && new_xywh[1] == globaly) {
				return;
			}
			// Fix globalx, globaly
			globalx = new_xywh[0];
			globaly = new_xywh[1];
		}

		setMap(newfile);
	}

	public void setMap(String file) {
		MapReader MR = new MapReader(file);
		blocks = MR.makeMap();
		projectiles = new ArrayList<Projectile>();
		M = new Map(blocks);
		craft.myMap = M;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (ingame) {
			if (craft.getX() < camx + CAM_HB) {
				camx = Math.max(0, craft.getX() - CAM_HB);
			}
			if (craft.getX() + craft.getWidth() > camx + camw - CAM_HB) {
				camx = Math.min(M.getWidth() - camw, craft.getX() + CAM_HB
						- camw + craft.getWidth());
			}
			if (craft.getY() < camy + CAM_VB) {
				camy = Math.max(0, craft.getY() - CAM_VB);
			}
			if (craft.getY() + craft.getHeight() > camy + camh - CAM_VB) {
				camy = Math.min(M.getHeight() - camh, craft.getY() + CAM_VB
						- camh + craft.getHeight());
			}

			Graphics2D g2d = (Graphics2D) g;
			if (craft.isVisible())
				g2d.drawImage(craft.getImage(), craft.getX() - camx,
						craft.getY() - camy, this);

			for (int i = 0; i < blocks.size(); i++) {
				Block a = (Block) blocks.get(i);
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
			if (paused) {
				g2d.setFont(new Font("SERIF", 0, 100));
				g2d.setColor(Color.WHITE);
				g2d.drawString("PAUSED [" + KeyEvent.getKeyText(P) + "]", 128,
						200);
				g2d.setFont(new Font("SERIF", 0, 50));
				g2d.drawString("[" + KeyEvent.getKeyText(F) + "] for restart",
						128, 320);
				g2d.drawString("[" + KeyEvent.getKeyText(G) + "] to menu",
						128, 380);

			}
			g2d.setColor(Color.WHITE);
		} else {
			Graphics2D g2d = (Graphics2D) g;
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
					offset = -b.getSize()/2;
				}
				if (menu != 2) {
					g2d.drawString(toDraw, b.getX() + offset, b.getY());
				} else {
					if (b.getDestination() != -1) {
						int wut = b.getDestination();
						String toAdd = "";
						switch (wut) {
						case 0:
							toAdd = KeyEvent.getKeyText(LEFT);
							break;
						case 1:
							toAdd = KeyEvent.getKeyText(RIGHT);
							break;
						case 2:
							toAdd = KeyEvent.getKeyText(UP);
							break;
						case 3:
							toAdd = KeyEvent.getKeyText(DOWN);
							break;
						case 4:
							toAdd = KeyEvent.getKeyText(F);
							break;
						case 5:
							toAdd = KeyEvent.getKeyText(G);
							break;
						case 6:
							toAdd = KeyEvent.getKeyText(P);
							break;
						}
						toDraw = toDraw + "[" + toAdd + "]";
						if (toConf != -1 && toDraw.charAt(0) == '>')
							g2d.setColor(Color.CYAN);
					}
					g2d.drawString(toDraw, b.getX() + offset,
							b.getY());
				}
			}
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
		repaint();
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	private class TAdapter extends KeyAdapter {
		public void keyReleased(KeyEvent e) {
			if (ingame && !paused)
				craft.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (!ingame) {
				if (menu != -1) // -1 is in game
				{
					if (menu == 2) {// special case: configure page
						if (toConf == -1) {
							if (key == UP) {
								if (bIndex > 0) {
									bIndex -= 1;
								} else {
									bIndex = menupages.get(menu).getButtons()
											.size() - 1;
								}
							}
							if (key == DOWN) {
								ArrayList<Button> curButtons = menupages.get(
										menu).getButtons();
								if (bIndex < curButtons.size() - 1) {
									bIndex += 1;
								} else {
									bIndex = 0;
								}
							}
							if (key == F) {
								toConf = menupages.get(menu).getButtons()
										.get(bIndex).getDestination();
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
			} else {
				if (key == P) {
					paused = !paused;
					craft.releaseAll();
				}
				if (paused && key == F) {
					initGame();
					paused = false;
				}
				else if (paused && key == G) {
					initGame();
					menu = 0;
					ingame = false;
					paused = false;
				}
			}
			if (ingame && !paused)
				craft.keyPressed(e);
		}
	}
}