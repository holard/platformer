package Objects.Projectiles;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import GUI.Board;
import GUI.Map;
import Objects.MainChar;
import Objects.Tiles.Tile;

public class BubbleProj extends Projectile {
	private int time;
	public BubbleProj(int x, int y, int dx, int dy, Map M, Board B) {
		ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + "bubble.png")).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		time = new Random().nextInt(800)+1000;
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		friendly = true;
		mass = 1;
		myMap = M;
		myBoard = B;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public void move() {
		time -= MainChar.TIMER;
		
		//Collision checking against tiles
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		epquery(points, tiles, (int) (x), (int) (y), width, height);
		if (points[6]) {
			y -= dy + 2;
			dy = 0;
		} else {
			if (dy < 2)
				dy += 1;
		}
		if (points[3]) {
			dx = 0;
			x += 1;
		}
		if (points[4]) {
			dx = 0;
			x -= 1;
		}
		ArrayList<Projectile> projs = myBoard.getProjectiles();
		for (Projectile p : projs) {
			if (p.getBounds().intersects(getBounds())) {
				if (p.mass >= mass) {
					int disx = (int) ((p.getX() + p.getWidth()/2) - (x + getWidth()));
					int disy = (int) (p.getY() + p.getHeight()/2 - (y + getHeight()));
					x -= disx/(1+Math.abs(disx)) + new Random().nextInt(3)-1;
					y -= disy/(1+Math.abs(disy)) + new Random().nextInt(3)-1;
				}
			}
		}
		if (time <= 0)
			myBoard.getProjectiles().remove(this);
		else
			super.move();
	}
}
