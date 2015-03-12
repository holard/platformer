package Objects;

import GUI.Pair;

public interface Tile {
	Pair<Integer,Integer> getPosition();
	boolean isWall();
	boolean canStick();
}
