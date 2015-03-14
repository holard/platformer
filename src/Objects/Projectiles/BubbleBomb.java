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

public class BubbleBomb extends Projectile {

	public BubbleBomb(int x, int y, int dx, int dy, Map M, Board B) {
		ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + "bubblebomb.png"))
					.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		damage = 1;
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
		// Collision checking against tiles
		if (dy < 14) {
			dy += 1;
		}
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		epquery(points, tiles, (int) (x), (int) (y), width, height);
		boolean collided = false;
		for (int i = 0; i < points.length; i++) {
			if (points[i]) {
				collided = true;
			}
		}
		if (collided) {
			myBoard.getProjectiles().remove(this);
			death();
		} else
			super.move();
	}

	@Override
	public void death() {
		Random r = new Random();
		x = x-dx;
		y = y-dy;
		for (int i = 0; i < 100; i++) {
			double d = r.nextDouble() * 2 * Math.PI;
			int dx = (int) (Math.cos(d) * 3 + this.dx/4);
			int dy = (int) (Math.sin(d) * 3 + this.dy/4);
			myBoard.getProjectiles().add(
					new BubbleProj((int)x, (int)y, dx, dy, myMap, myBoard));
		}
	}
	
	@Override
	public boolean outOfBounds() {
		if (y > 0) {
			return super.outOfBounds();
		} else
			return false;
	}
}
