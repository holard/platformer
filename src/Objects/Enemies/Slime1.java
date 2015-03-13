package Objects.Enemies;

import GUI.Board;
import GUI.Map;
import Objects.Tiles.Tile;

public class Slime1 extends Enemy {
	private double dx,dy;
	public static final int SPEED = 2;
	public static final int MAX_VERTICAL_SPEED = 6;
	public static final int JUMP_SPEED = -3;
	
	public Slime1 (Double x, Double y, Map M, Board B) {
		System.out.println("Spawning slime at " + Double.toString(x) + " " + Double.toString(y));
		setImage(IMAGE_PATH + "slime1.png");
		initEnemy(x,y,M,B);
		health = 240;
		visible = true;
		dx = -SPEED;
		dy = 0;
	}
	
	public void move() {		
		if (dy < MAX_VERTICAL_SPEED) 
			dy += 0.5;
		if (myMap.checkWall((int)x,(int)(y + height)) || myMap.checkWall((int)x + width - 1, (int)(y + height)))
			dy = JUMP_SPEED;
		
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		epquery(points, tiles, (int)(x + dx), (int)(y + dy), width, height);
		
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
		
		//Lateral collision
		if (points[0] || points[3]) {
			int maxX = 0;
			if (tiles[0] != null && tiles[0].getX() > maxX)
				maxX = tiles[0].getX();
			if (tiles[3] != null && tiles[3].getX() > maxX)
				maxX = tiles[3].getX();
			x = maxX + SCALE + 1;
			dx = SPEED;
		}	
		else if (points[2] || points[4]) {
			int minX = myMap.getWidth();
			if (tiles[2] != null && tiles[2].getX() < minX)
				minX = tiles[2].getX();
			if (tiles[4] != null && tiles[4].getX() < minX)
				minX = tiles[4].getX();
			x = minX - width - 1;
			dx = -SPEED;
		}
		
		x += dx;
		y += dy;
	}
}
