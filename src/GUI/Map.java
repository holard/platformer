package GUI;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	private HashMap<Pair<Integer, Integer>, Tile> grid;
	public static int DEFAULT_SIZE = 16;

	public Map() {
		grid = new HashMap<Pair<Integer, Integer>, Tile>();
	}

	public Map(ArrayList<Tile> tiles) {
		grid = new HashMap<Pair<Integer, Integer>, Tile>();
		for (Tile t : tiles) {
			grid.put(snap(t.getPosition()), t);
			grid.put(snap(new Pair<Integer,Integer>(t.getPosition().first()+16,t.getPosition().second())), t);
			grid.put(snap(new Pair<Integer,Integer>(t.getPosition().first(),t.getPosition().second()+16)), t);
			grid.put(snap(new Pair<Integer,Integer>(t.getPosition().first()+16,t.getPosition().second()+16)), t);
		}
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

}
