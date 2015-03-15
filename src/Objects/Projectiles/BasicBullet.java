package Objects.Projectiles;

import GUI.Board;
import GUI.Map;
import Objects.Tiles.Tile;

public class BasicBullet extends Projectile {
	public BasicBullet(double x, double y, double dx, double dy, Map M, Board B) {
		setImage(IMAGE_PATH + "basicBullet.png");	
		initProjectile(x,y,dx,dy,M,B);
		
		damage = 90;
		mass = 30;
		visible = true;
		friendly = true;	
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
	
	@Override
	public void death() {
		
	}
}
