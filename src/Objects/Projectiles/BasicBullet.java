package Objects.Projectiles;

import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import GUI.Board;
import GUI.Map;
import Objects.Tiles.Tile;

public class BasicBullet extends Projectile {
	public BasicBullet(int x, int y, int dx, int dy, Map M, Board B) {
		ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + "missile.png")).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		friendly = true;
		
		myMap = M;
		myBoard = B;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public void move() {		
		//Collision checking against tiles
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		epquery(points, tiles, (int) (x), (int) (y), width, height);
		boolean collided = false;
		for (int i = 0; i < points.length; i++) {
			if (points[i]) {
				collided = true;
			}
		}
		if (collided)
			myBoard.getProjectiles().remove(this);
		else
			super.move();
	}
}
