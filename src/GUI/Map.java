package GUI;

import java.util.ArrayList;
import java.util.HashMap;

import Objects.Tile;

public class Map {
	private HashMap<Pair<Integer, Integer>, Tile> grid;
	public static int DEFAULT_SIZE = 16;
	public static int TILE_SIZE = 32;
	private int width, height;

	public Map() {
		grid = new HashMap<Pair<Integer, Integer>, Tile>();
	}

	public Map(ArrayList<Tile> tiles) {
		grid = new HashMap<Pair<Integer, Integer>, Tile>();
		int tx, ty, max_x = 0, max_y = 0;
		for (Tile t : tiles) {
			tx = t.getPosition().first();
			ty = t.getPosition().second();
			
			//Make this 32x32 tile cover four 16x16 blocks in the grid.
			grid.put(snap(t.getPosition()), t);
			grid.put(snap(new Pair<Integer,Integer>(tx + DEFAULT_SIZE, ty)), t);
			grid.put(snap(new Pair<Integer,Integer>(tx, ty + DEFAULT_SIZE)), t);
			grid.put(snap(new Pair<Integer,Integer>(tx + DEFAULT_SIZE, ty + DEFAULT_SIZE)), t);
			
			//Set the width/height of the map
			if (tx > max_x) 
				max_x = tx;			
			if (ty > max_y)
				max_y = ty;
		}
		
		width = max_x + TILE_SIZE;
		height = max_y + TILE_SIZE * 2;
	}

	public static Pair<Integer, Integer> snap(Pair<Integer, Integer> old) {
		return new Pair<Integer, Integer>(old.first() / DEFAULT_SIZE,
				old.second() / DEFAULT_SIZE);
	}

	public Tile getWallAt(int x, int y) {
		Pair<Integer, Integer> pos = new Pair<Integer,Integer>(x, y);
		Tile t = grid.get(snap(pos));
		if (t == null) {
			return null;
		}
		if (!t.isWall())
			return null;
		return t;
	}
	
	public Tile getTileAt(int x, int y) {
		Pair<Integer, Integer> pos = new Pair<Integer,Integer>(x, y);
		Tile t = grid.get(snap(pos));
		if (t != null) {
			return t;
		}
		return t;
	}
	
	public boolean checkWall(int x, int y) {
		Tile t1 = getWallAt(x,y);
		return (t1!= null);
	}
	
	public int getWidth() {
    	return width;
    }
    
    public int getHeight() {
    	return height;
    }
}
