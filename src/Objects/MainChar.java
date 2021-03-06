package Objects;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import GUI.Board;
import GUI.Map;
import Objects.Enemies.Enemy;
import Objects.Items.Bag;
import Objects.Items.Gun;
import Objects.Tiles.Tile;
import Objects.fieldItems.FieldItem;

public class MainChar extends Element {

	public static final int TIMER = 15;
	public static final int MAX_ENERGY = 100;
	public static final String IMAGE_PATH = "Images/";

	private double dx;
	private double dy;
	private int jumps = 0;
	int dir = 1; // -1 is facing left, 1 is facing right
	private boolean[] lrud = { false, false, false, false };
	private boolean[] lrud2 = { false, false, false, false };
	private boolean iChange = false; // image change needed
	private int stick = 0; // 0 if not stuck, -1 if stuck on left, 1 if stuck on
							// right
	private int sticktime = 0;
	private int lastkey = -1;
	private int doubletimer = 0;
	private int keycount = 0;
	private Tile hang; // null if not hanging, otherwise holds tile that is hung
						// on
	private Gun myGun;
	private boolean firing = false;
	private int health;
	private int maxhealth;
	private int invince;
	private int checkX;
	private int checkY;
	private Tile lastTile = null;
	private DataCenter data;

	public MainChar(Board myb) {
		setImage(IMAGE_PATH + "charRight.png");
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		x = Board.GLOBALX_START + 50;
		y = Board.GLOBALY_START + 500;
		checkX = (int) x;
		checkY = (int) y;
		System.out.println(checkX);
		jumps = 0;
		myBoard = myb;
		invince = 0;
		maxhealth = 50;
		health = 50;
	}

	public void checkPoint() {
		checkX = (int) x;
		checkY = (int) y;
	}

	public void setMap(Map newMap) {
		myMap = newMap;
	}

	public int getDirection() {
		return dir;
	}

	public void releaseAll() {
		lrud = new boolean[4];
		lrud2 = new boolean[4];
		firing = false;
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
			if (!myMap.checkWall(getX(), getY() - SCALE / 2)
					&& !myMap.checkWall(getX() - 1, getY() - 1)
					&& !myMap.checkWall(getX(), tiles[3].getPosition().second()
							+ SCALE) && dx < 0 && dy > 0) {
				hang = tiles[3];
				jumps = 0;
			} else if (!myMap.checkWall(getX(), getY() + height + SCALE)
					&& dx < -.15 * TIMER && tiles[3].canStick() && hang == null) {
				stick = -1;
				System.out.println("left stick set");
				sticktime = 150;
			}
			dx = 0;
		} else if (points[5]) {
			if (!myMap.checkWall(getX() + SCALE - 1, getY() - SCALE / 2)
					&& !myMap
							.checkWall(getX(), tiles[5].getPosition().second())
					&& !myMap.checkWall(getX(), tiles[5].getPosition().second()
							+ SCALE) && dx < -.15 * TIMER) {
				hang = tiles[5];
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
				System.out.println("left stick set");
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

	public void handleJump() {
		if (lrud[2]) { // pressing the UP arrow
			if (hang != null) {
				hang = null;
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
	}

	public void handleDoubleTap() {
		doubletimer -= TIMER;
		if (doubletimer <= 0) {
			doubletimer = 0;
			lastkey = -1;
			keycount = 0;
		}
	}

	public void handleStickHang() {
		if (lrud[3]) {
			stick = 0;
		}

		if (stick != 0) {
			if (dy > 0.1 * TIMER)
				dy = 0.1 * TIMER;
			firing = false;
			myGun.dud();
		}
		if (!myMap.checkWall(getX(), getY() + height)
				&& dy < TIMER - Math.abs(stick * 0.9 * TIMER)) {
			if (hang == null || hang.getPosition().second() > y)
				dy += TIMER * 0.05;
		}
		if (hang != null) {
			myGun.dud();
			if (getY() <= hang.getPosition().second() - 32
					&& Math.abs(hang.getPosition().first() - getX()) < SCALE) {
				hang = null;
			}
		}
	}

	public void handleGravity(boolean[] points, Tile[] tiles) {
		if (points[6]) {
			Tile t = tiles[6];
			dy = 0;
			stick = 0;
			if (hang == null)
				jumps = 1;
			y = t.getPosition().second() - height;
		}
	}

	public int getInvince() {
		return invince;
	}

	public void handleStickHangCheckers() {
		if (!myMap.checkWall(getX() + width, getY() + height / 2)
				&& !myMap.checkWall(getX() - 1, getY() + height / 2))
			stick = 0;

		if (hang != null && y >= hang.getPosition().second()) {
			y = hang.getPosition().second();
			dy = 0;
			firing = false;
		}
		if (stick != 0 || hang != null) {
			firing = false;
		}

	}

	public void handleLava() {
		if (myMap.getTileAt((int) (x + width / 2), (int) (y + height / 2)) != null)
			if (myMap.getTileAt((int) (x + width / 2), (int) (y + height / 2))
					.isLava()) {
				if (lastTile != null) {
					health -= 1;
					x = lastTile.getX();
					y = lastTile.getY() - SCALE - 1;
					dx = 0;
					dy = 0;
					invince = 500;
					releaseAll();
				}
			}
	}

	public void handleEnemyCollisions() {
		if (invince != 0) {
			return;
		}
		Enemy e = enemyCollide();
		if (e == null)
			return;
		health -= e.getDamage();
		if (e.getX() > getX()) {
			dx = -e.getXKnockBack();
		} else {
			dx = e.getXKnockBack();
		}
		dy = -e.getYKnockBack();
		invince = 500;
		hang = null;
		stick = 0;
	}

	private boolean collides(double ox, double oy) {
		return (ox > x && ox > y && ox < x + width && oy < y + height);
	}

	public void handleItemPickup() {
		FieldItem fi = null;
		Bag myBag = data.getMyBag();
		if (myBag.isFull()) { 
			return;
		}
		for (FieldItem f : myBoard.getFieldItems()) {
			if (collides(f.x, f.y) || collides(f.x + f.width, f.y)
					|| collides(f.x, f.y + f.height)
					|| collides(f.x + f.width, f.y + f.height)) {
				fi = f;
			}
		}
		if (fi != null) {
			data.addToBag(fi.getItem());
			myBoard.getFieldItems().remove(fi);
		}
	}

	public void handleLastTile() {
		int cx = (int) (x + width / 2);
		int cy = (int) (y + height);
		if (myMap.checkWall(cx, cy)) {
			if (myMap.getTileAt(cx, cy - SCALE) == null
					|| !myMap.getTileAt(cx, cy - SCALE).isLava())
				lastTile = myMap.getWallAt(cx, cy);
		}
	}

	public void handleBTCP() {
		if (myMap.getTileAt((int) (x + width / 2), (int) (y + height / 2)) != null)
			if (myMap.getTileAt((int) (x + width / 2), (int) (y + height / 2))
					.isCheckPointer()) {
				health -= 1;
				x = checkX;
				y = checkY;
				dx = 0;
				dy = 0;
				invince = 500;

			}
	}

	public void move() {
		handleLR();
		handleDoubleTap();
		handleJump();
		handleStickHang();
		if (invince > 0) {
			invince -= TIMER;
		}
		if (invince < 0) {
			invince = 0;
		}
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		epquery(points, tiles, (int) (x + dx), (int) (y + dy), width, height);

		handleLateralCollision(points, tiles);
		handleCeilingCollision(points, tiles);
		handleGravity(points, tiles);
		handleStickHangCheckers();
		handleLastTile();
		handleLava();
		handleBTCP();
		handleItemPickup();
		x += dx;
		y += dy;
		handleEnemyCollisions();
		if (firing) {
			fireBasic();
		}
		myGun.loadMe(TIMER);
		if (outOfBounds()) {
			myBoard.changeMap(0);
		}

	}

	public void keyPressed(int key) {

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
			setImage(IMAGE_PATH + "charLeft.png");
			dir = -1;
			myGun.setImage(myGun.getLeftImage());

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
			setImage(IMAGE_PATH + "charRight.png");
			dir = 1;
			myGun.setImage(myGun.getRightImage());
			if ((myMap.checkWall(getX(), getY() + height) || myMap.checkWall(
					getX() + width - 1, getY() + height))
					&& lastkey == key
					&& keycount == 2) {
				dx = 0.9 * TIMER;
			}
			lastkey = key;
		}

		if (key == Board.DOWN) {
			if (myBoard.checkNPC(x + width / 2, y + height / 2) != null) {
				myBoard.setNPC(myBoard.checkNPC(x + width / 2, y + height / 2));
			} else {
				lrud[3] = true;
				lrud2[3] = true;
				hang = null;
				lastkey = key;
			}
		}

		if (key == Board.F) {
			lastkey = key;
			if (stick == 0)
				firing = true;

		}

		keycount = 1;
	}

	public void fireBasic() {
		myGun.fire(getX(), getY(), width, height, dir, myMap, myBoard);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == Board.LEFT) {
			lrud[0] = false;
			lrud2[0] = false;
			if (lrud[1]) {
				setImage(IMAGE_PATH + "charRight.png");
				myGun.setImage(myGun.getRightImage());
			}
		}

		if (key == Board.RIGHT) {
			lrud[1] = false;
			lrud2[1] = false;
			if (lrud[0]) {
				setImage(IMAGE_PATH + "charLeft.png");
				myGun.setImage(myGun.getLeftImage());
			}
		}

		if (key == Board.UP) {
			lrud[2] = false;
			lrud2[2] = false;
		}
		if (key == Board.DOWN) {
			lrud[3] = false;
			lrud2[3] = false;
		}
		if (key == Board.F) {
			firing = false;
			if (myGun.charged()) {
				myGun.release(getX(), getY(), width, height, dir, myMap,
						myBoard);
			}
		}
		lastkey = key;
		keycount = 2;
	}

	public Gun getMyGun() {
		return myGun;
	}

	public void setMyGun(Gun myGun) {
		this.myGun = myGun;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMaxhealth() {
		return maxhealth;
	}

	public void setMaxhealth(int maxhealth) {
		this.maxhealth = maxhealth;
	}

	public Tile getHang() {
		return hang;
	}

	public int getStick() {
		return stick;
	}

	public DataCenter getData() {
		return data;
	}

	public void setData(DataCenter data) {
		this.data = data;
	}
}