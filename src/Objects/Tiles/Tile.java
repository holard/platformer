package Objects.Tiles;

import java.awt.Image;

import GUI.Pair;

public interface Tile {
	Pair<Integer,Integer> getPosition();
	int getX();
	int getY();
	boolean isWall();
	boolean canStick();
	boolean isLava();
	boolean isVisible();
	boolean isCheckPointer();
	Image getImage();
}
