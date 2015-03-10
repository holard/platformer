package GUI;

public interface Tile {
	Pair<Integer,Integer> getPosition();
	boolean isWall();
	boolean canStick();
}
