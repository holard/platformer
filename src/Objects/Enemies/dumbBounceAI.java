package Objects.Enemies;

import GUI.Map;
import Objects.Element;
import Objects.Tiles.Tile;

public class dumbBounceAI implements AI{

	@Override
	public double[] move(double ox, double oy, double odx, double ody, Map myMap,
			Enemy me) {
		double x = ox;
		double y = oy;
		double dx = odx;
		double dy = ody;
		if (dy < me.MAX_VERTICAL_SPEED) 
			dy += 0.5;
		if (myMap.checkWall((int)x,(int)(y + me.getHeight())) || myMap.checkWall((int)x + me.getWidth() - 1, (int)(y + me.getHeight())))
			dy = me.JUMP_SPEED;
		
		boolean[] points = new boolean[8];
		Tile[] tiles = new Tile[8];
		me.epquery(points, tiles, (int)(x + dx), (int)(y + dy), me.getWidth(), me.getHeight());
		
		// hitting your head on the ceiling
		if (points[1]) {
			dy = 0;
			y = tiles[1].getPosition().second() + Element.SCALE;
		} else if (points[0]) {
			Tile t = tiles[0];
			if ((y + dy) - t.getPosition().second() > (x + dx)
					- t.getPosition().first()) {
				dy = 0;
				y = t.getPosition().second() + Element.SCALE;
			} else {
				dx = 0;
				x = t.getPosition().first() + Element.SCALE;
			}
		} else if (points[2]) {
			Tile t = tiles[2];
			if ((y + dy) - t.getPosition().second() > t.getPosition().first()
					- (x + dx)) {
				dy = 0;
				y = t.getPosition().second() + Element.SCALE;
			} else {
				dx = 0;
				x = t.getPosition().first() - me.getWidth();
			}
		}
		
		//Lateral collision
		if (points[0] || points[3]) {
			int maxX = 0;
			if (tiles[0] != null && tiles[0].getX() > maxX)
				maxX = tiles[0].getX();
			if (tiles[3] != null && tiles[3].getX() > maxX)
				maxX = tiles[3].getX();
			x = maxX + Element.SCALE + 1;
			dx = me.SPEED;
		}	
		else if (points[2] || points[4]) {
			int minX = myMap.getWidth();
			if (tiles[2] != null && tiles[2].getX() < minX)
				minX = tiles[2].getX();
			if (tiles[4] != null && tiles[4].getX() < minX)
				minX = tiles[4].getX();
			x = minX - me.getWidth() - 1;
			dx = -me.SPEED;
		}
		
		x += dx;
		y += dy;
		double[] results = new double[4];
		results[0] = x;
		results[1] = y;
		results[2] = dx;
		results[3] = dy;
		return results;
		
	}

}
