package Objects.Tiles;

import GUI.Pair;

public interface Tile {
	Pair<Integer,Integer> getPosition();
	int getX();
	int getY();
	boolean isWall();
	boolean canStick();
}
