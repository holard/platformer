package GUI;

import javax.swing.ImageIcon;

public class BasicBullet extends Projectile {
	public BasicBullet(int x, int y, int dx, int dy, Map M, Board B) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("missile.png"));
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
		
		super.move();
	}
}
