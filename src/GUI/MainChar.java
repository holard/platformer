 package GUI;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class MainChar extends Element {

	public static final int SCALE = 32;
	public static final int TIMER = 15;
	private double dx;
	private double dy;
	private int jumps = 0;
	int dir = 1; //-1 is facing left, 1 is facing right
	private boolean[] lrud = { false, false, false, false };
	private boolean[] lrud2 = { false, false, false, false };
	private boolean iChange = false; //image change needed
	private int stick = 0;
	private int sticktime = 0;

	public MainChar(Map b, Board myb) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("craft.png"));
		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		myMap = b;
		if (myMap == null)
			System.out.println("myMap is null!");
		visible = true;
		x = 40;
		y = 60;
		jumps = 0;
		myBoard = myb;
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
			if (stick != -1 && dx < .3 * TIMER)
				dx += 1;
			if (stick == -1) {
				sticktime -= TIMER;
				if (sticktime <= 0) {
					stick = 0;
				}
			}
		}
		
		//decay to max speed
		if (dx > .3 * TIMER) {
			dx -= 0.01*TIMER;
		}
		if (dx < -.3 * TIMER) {
			dx += 0.01*TIMER;
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
		} 
		else if (points[2]) 
		{
			Tile t = tiles[2];
			if ((y + dy) - t.getPosition().second() > t.getPosition().first()
					- (x + dx)) {
				dy = 0;
				y = t.getPosition().second() + SCALE;
			} else {
				dx = 0;
				x = t.getPosition().first() - SCALE;
			}
		}
	}
	
	public void handleLateralCollision(boolean[] points, Tile[] tiles) {
		if (points[3]) { // LEFT SIDE
			x = tiles[3].getPosition().first() + SCALE;
			if (!myMap.checkWall(x, y + 64) && dx < -.15 * TIMER && tiles[3].canStick()) {
				stick = -1;
				sticktime = 150;
			}
			dx = 0;
		} else if (points[5]) {
			Tile t = tiles[5];
			if (dy >= 0
					&& t.getPosition().second() - (y + dy) > (x + dx)
							- t.getPosition().first()) {
				dy = 0;
				y = t.getPosition().second() - SCALE;
				jumps = 1;
				stick = 0;
			} else {
				dx = 0;
				x = t.getPosition().first() + SCALE;
			}
		}
		if (points[4]) { // RIGHT SIDE
			x = tiles[4].getPosition().first() - SCALE;
			if (!myMap.checkWall(x+SCALE-1, y + 64) && dx > .15 * TIMER && tiles[4].canStick()) {
				stick = 1;
				sticktime = 150;
			}
			dx = 0;
		} else if (points[7]) {
			Tile t = tiles[7];
			if (dy >= 0
					&& t.getPosition().second() - (y + dy) > t.getPosition()
							.first() - (x + dx)) {
				dy = 0;
				y = t.getPosition().second() - SCALE;
				jumps = 1;
				stick = 0;
			} else {
				dx = 0;
				x = t.getPosition().first() - SCALE;
			}
		}
	}
	
	public void move() {
		handleLR();
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		
		if (lrud[2]) { // pressing the UP arrow
			if (stick == -1) {
				if (lrud[1]) {
					stick = 0;
					jumps = 0;
					dy = -TIMER * 0.75;
					dx = 0.5 * TIMER;
				}
			} else if (stick == 1) {
				if (lrud[0]) {
					stick = 0;
					jumps = 0;
					dy = -TIMER * 0.75;
					dx = -0.5 * TIMER;
				}
			}
			else if (myMap.checkWall(x, y+32) || myMap.checkWall(x+SCALE-1,y+32)) {
				dy = -TIMER * 0.85;
			}
			else if (jumps == 1) {
				dy = -TIMER * 0.85;
				jumps -= 1;
			}
			lrud[2] = false;
		}
		if (lrud[3]) {
			stick = 0;
		}
		
		if (stick != 0) {
			if (dy > 0.1*TIMER)
				dy = 0.1*TIMER;
		}
		if (!myMap.checkWall(x, y + SCALE)
				&& dy < TIMER - Math.abs(stick * 0.9 * TIMER))
			dy += TIMER * 0.05;
		epquery(points, tiles, (int) (x + dx), (int) (y + dy), SCALE);
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
			jumps = 1;
			y = t.getPosition().second() - SCALE;
		}
		if (!myMap.checkWall(x+SCALE,y+SCALE/2) && !myMap.checkWall(x-1,y+SCALE/2))
			stick = 0;
		x += dx;
		y += dy;
		if (x < 1) {
			x = 1;
		}
		if (x > myBoard.mapwidth) {
			x = myBoard.mapwidth;
		}
		if (y > myBoard.mapheight) {
			y = myBoard.mapheight;
		}
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {

		}

		if (key == KeyEvent.VK_UP) {
			lrud[2] = true;
			lrud2[2] = true;
		}

		if (key == KeyEvent.VK_LEFT) {
			lrud[0] = true;
			lrud2[0] = true;
			setImage("craft2.png");
		}

		if (key == KeyEvent.VK_RIGHT) {
			lrud[1] = true;
			lrud2[1] = true;
			setImage("craft.png");
		}
		
		if (key == KeyEvent.VK_DOWN) {
			lrud[3] = true;
			lrud2[3] = true;
		}
		
		if (key == KeyEvent.VK_F) {
			fireBasic();
		}
	}

	public void fireBasic() {
		int bullet_dx = 10 * dir;
		int bullet_dy = 0;
		int bullet_x, bullet_y;
		if (dir == 1) {
			bullet_x = x + width;
			bullet_y = y + height/2;			
		}
		else {
			bullet_x = x - 24; //24 hardcoded bullet width
			bullet_y = y + height/2;
		}
		myBoard.getProjectiles().add(new BasicBullet(bullet_x,bullet_y,bullet_dx,bullet_dy));
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			lrud[0] = false;
			lrud2[0] = false;
			if (lrud[1])
				setImage("craft.png");
		}

		if (key == KeyEvent.VK_RIGHT) {
			lrud[1] = false;
			lrud2[1] = false;
			if (lrud[0])
				setImage("craft2.png");
		}

		if (key == KeyEvent.VK_UP) {
			lrud[2] = false;
			lrud2[2] = false;
		}
		if (key == KeyEvent.VK_DOWN) {
			lrud[3] = false;
			lrud2[3] = false;
		}
	}
}