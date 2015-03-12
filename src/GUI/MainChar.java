package GUI;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class MainChar extends Element {

	public static final int SCALE = 32;
	public static final int TIMER = 15;
	public static final int MAX_ENERGY = 100;
	private double dx;
	private double dy;
	private int jumps = 0;
	int dir = 1; // -1 is facing left, 1 is facing right
	private boolean[] lrud = { false, false, false, false };
	private boolean[] lrud2 = { false, false, false, false };
	private boolean iChange = false; // image change needed
	private int stick = 0;
	private int sticktime = 0;
	private int energy = MAX_ENERGY;
	private int lastkey = -1;
	private int doubletimer = 0;
	private int keycount = 0;
	private Tile hang;
	private boolean hung = false;

	public MainChar(Board myb) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("craft.png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		x = Board.GLOBALX_START + 2500;
		y = Board.GLOBALY_START + 50;
		jumps = 0;
		myBoard = myb;
	}

	public void setMap(Map nu) {
		myMap = nu;
	}
	
	public void releaseAll() {
		lrud = new boolean[4];
		lrud2 = new boolean[4];
	}

	public void handleLR() {
		// deceleration
		if ((!lrud[0] && !lrud[1]) || (lrud[0] && lrud[1])) {
			if (dx > 1) {
				dx -= 1;
			} else if (dx < -1) {
				dx += 1;
			} else
				dx = 0;
		}

		// move left
		if (lrud[0] && !lrud[1]) {
			if (hang != null && hang.getPosition().first() > x) {
				hang = null;
			}
			if (stick != 1 && dx > -.3 * TIMER)
				dx -= 1;
			if (stick == 1) {
				sticktime -= TIMER;
				if (sticktime <= 0) {
					stick = 0;
				}
			}
		}

		// move right
		if (lrud[1] && !lrud[0]) {
			if (hang != null && hang.getPosition().first() < x) {
				hang = null;
			}
			if (stick != -1 && dx < .3 * TIMER)
				dx += 1;
			if (stick == -1) {
				sticktime -= TIMER;
				if (sticktime <= 0) {
					stick = 0;
				}
			}
		}

		// decay to max speed
		if (dx > .3 * TIMER) {
			dx -= 0.015 * TIMER;
		}
		if (dx < -.3 * TIMER) {
			dx += 0.015 * TIMER;
		}
	}

	public void handleCeilingCollision(boolean[] points, Tile[] tiles) {
		// hitting your head on the ceiling
		if (points[1]) {
			dy = 0;
			y = tiles[1].getPosition().second() + SCALE;
		} else if (points[0]) {
			Tile t = tiles[0];
			if ((y + dy) - t.getPosition().second() > (x + dx)
					- t.getPosition().first()) {
				dy = 0;
				y = t.getPosition().second() + SCALE;
			} else {
				dx = 0;
				x = t.getPosition().first() + SCALE;
			}
		} else if (points[2]) {
			Tile t = tiles[2];
			if ((y + dy) - t.getPosition().second() > t.getPosition().first()
					- (x + dx)) {
				dy = 0;
				y = t.getPosition().second() + SCALE;
			} else {
				dx = 0;
				x = t.getPosition().first() - width;
			}
		}
	}

	public void handleLateralCollision(boolean[] points, Tile[] tiles) {
		if (points[3]) { // LEFT SIDE
			x = tiles[3].getPosition().first() + SCALE;
			if (!myMap.checkWall(getX(),getY() - SCALE)
					&& !myMap.checkWall(getX() - 1, getY() - 1)
					&& !myMap.checkWall(getX(), tiles[3].getPosition().second()
							+ SCALE) && dx < 0 && dy > 0) {
				hang = tiles[3];
				hung = false;
				jumps = 0;
			} else if (!myMap.checkWall(getX(), getY() + height + SCALE)
					&& dx < -.15 * TIMER && tiles[3].canStick() && hang == null) {
				stick = -1;
				sticktime = 150;
			}
			dx = 0;
		} else if (points[5]) {
			if (!myMap.checkWall(getX() + SCALE - 1,getY() - SCALE)
					&& !myMap
							.checkWall(getX(), tiles[5].getPosition().second())
					&& !myMap.checkWall(getX(), tiles[5].getPosition().second()
							+ SCALE) && dx < -.15 * TIMER) {
				hang = tiles[5];
				hung = false;
				jumps = 0;
			}
			Tile t = tiles[5];
			if (dy >= 0
					&& t.getPosition().second() - (y + dy) > (getX() + dx)
							- t.getPosition().first()) {
				dy = 0;
				y = t.getPosition().second() - height;
				if (hang == null)
					jumps = 1;
				stick = 0;
			} else {
				dx = 0;
				x = t.getPosition().first() + SCALE;
			}
		}
		if (points[4]) { // RIGHT SIDE
			x = tiles[4].getPosition().first() - width;
			if (!myMap.checkWall(getX() + width, getY() - 1)
					&& !myMap.checkWall(getX() + width - 1, tiles[4]
							.getPosition().second() + SCALE) && dx > 0
					&& dy > 0) {
				hang = tiles[4];
			} else if (!myMap.checkWall(getX() + width - 1, getY() + height
					+ SCALE)
					&& dx > .15 * TIMER && tiles[4].canStick()) {
				stick = 1;
				sticktime = 150;
			}
			dx = 0;
		} else if (points[7]) {
			if (!myMap.checkWall(getX() + width - 1, tiles[7].getPosition()
					.second())
					&& !myMap.checkWall(getX() + width - 1, tiles[7]
							.getPosition().second() + SCALE)
					&& dx > .15 * TIMER) {
				hang = tiles[7];
				hung = false;
				jumps = 0;
			}
			Tile t = tiles[7];
			if (dy >= 0
					&& t.getPosition().second() - (y + dy) > t.getPosition()
							.first() - (getX() + dx)) {
				dy = 0;
				y = t.getPosition().second() - height;
				if (hang == null)
					jumps = 1;
				stick = 0;
			} else {
				dx = 0;
				x = t.getPosition().first() - width;
			}
		}
	}

	public void move() {
		handleLR();
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		doubletimer -= TIMER;
		if (doubletimer <= 0) {
			doubletimer = 0;
			lastkey = -1;
			keycount = 0;
		}
		if (lrud[2]) { // pressing the UP arrow
			if (hang != null) {
				hang = null;
				hung = false;
				dy = -7.5;
			} else {
				if (stick == -1) {
					if (lrud[1]) {
						stick = 0;
						jumps = 0;
						dy = -TIMER * 0.75;
						dx = 0.6 * TIMER;
					}
				} else if (stick == 1) {
					if (lrud[0]) {
						stick = 0;
						jumps = 0;
						dy = -TIMER * 0.75;
						dx = -0.6 * TIMER;
					}
				} else if (myMap.checkWall(getX(), getY() + height)
						|| myMap.checkWall(getX() + width - 1, getY() + height)) {
					dy = -TIMER * 0.85;
				} else if (jumps == 1) {
					dy = -TIMER * 0.85;
					jumps -= 1;
				}
			}
			lrud[2] = false;
		}
		if (lrud[3]) {
			stick = 0;
		}

		if (stick != 0) {
			if (dy > 0.1 * TIMER)
				dy = 0.1 * TIMER;
		}
		if (!myMap.checkWall(getX(), getY() + height)
				&& dy < TIMER - Math.abs(stick * 0.9 * TIMER)) {
			if (hang == null || hang.getPosition().second() > y)
				dy += TIMER * 0.05;
		}

		epquery(points, tiles, (int) (x + dx), (int) (y + dy), width, height);
		// 0: topl, 1: top, 2: topr, 3: l, 4: r, 5: bl, 6: b, 7: br
		if (dx != 0) {
			dir = (int) (dx / (Math.abs(dx)));
		}

		if (stick != 0)
			dir = -stick;

		handleLateralCollision(points, tiles);
		handleCeilingCollision(points, tiles);
		if (points[6]) {
			Tile t = tiles[6];
			dy = 0;
			stick = 0;
			if (hang == null)
				jumps = 1;
			y = t.getPosition().second() - height;
		}
		if (!myMap.checkWall(getX() + width, getY() + height / 2)
				&& !myMap.checkWall(getX() - 1, getY() + height / 2))
			stick = 0;

		if (hang != null && y >= hang.getPosition().second()) {
			y = hang.getPosition().second();
			if (dy >= 0)
				hung = true;
			dy = 0;
		}

		x += dx;
		y += dy;

		if (outOfBounds()) {
			myBoard.changeMap(0);
		}
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (keycount == 0)
			doubletimer = 200;
		

		if (key == Board.UP) {
			lrud[2] = true;
			lrud2[2] = true;

			lastkey = key;
		}

		if (key == Board.LEFT) {
			lrud[0] = true;
			lrud2[0] = true;
			setImage("craft2.png");
			if ((myMap.checkWall(getX(), getY() + height) || myMap.checkWall(
					getX() + width - 1, getY() + height))
					&& lastkey == key
					&& keycount == 2) {
				dx = -0.9 * TIMER;
			}
			lastkey = key;
		}

		if (key == Board.RIGHT) {
			lrud[1] = true;
			lrud2[1] = true;
			setImage("craft.png");
			if ((myMap.checkWall(getX(), getY() + height) || myMap.checkWall(
					getX() + width - 1, getY() + height))
					&& lastkey == key
					&& keycount == 2) {
				dx = 0.9 * TIMER;
			}
			lastkey = key;
		}

		if (key == Board.DOWN) {
			lrud[3] = true;
			lrud2[3] = true;
			hang = null;
			lastkey = key;
		}

		if (key == Board.F) {
			lastkey = key;
			if (stick == 0)
				fireBasic();
		}

		keycount = 1;
	}

	public void fireBasic() {
		int bullet_dx = 10 * dir;
		int bullet_dy = 0;
		int bullet_x, bullet_y;

		BasicBullet bullet = new BasicBullet(0, 0, bullet_dx, bullet_dy, myMap,
				myBoard);
		int b_width = bullet.getWidth();
		int b_height = bullet.getHeight();
		if (dir == 1) {
			bullet_x = getX() + width;
			bullet_y = getY() + height / 2 - b_height / 2;
		} else {
			bullet_x = getX() - b_width;
			bullet_y = getY() + height / 2 - b_height / 2;
		}

		myBoard.getProjectiles().add(
				new BasicBullet(bullet_x, bullet_y, bullet_dx, bullet_dy,
						myMap, myBoard));
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == Board.LEFT) {
			lrud[0] = false;
			lrud2[0] = false;
			if (lrud[1])
				setImage("craft.png");
		}

		if (key == Board.RIGHT) {
			lrud[1] = false;
			lrud2[1] = false;
			if (lrud[0])
				setImage("craft2.png");
		}

		if (key == Board.UP) {
			lrud[2] = false;
			lrud2[2] = false;
		}
		if (key == Board.DOWN) {
			lrud[3] = false;
			lrud2[3] = false;
		}
		lastkey = key;
		keycount = 2;
	}
}