package GUI;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public abstract class Element {
	public int x,y;
	public Image image;
	boolean visible;
	public int width, height;
	public Map myMap;
	public Board myBoard;
	
	public void epquery(boolean[] points, Tile[] tiles, int x, int y, int width, int height) {
		// 0: topl, 1: top, 2: topr, 3: l, 4: r, 5: bl, 6: b, 7: br
		points[0] = myMap.checkWall(x, y);
		points[1] = myMap.checkWall(x + width / 2, y);
		points[2] = myMap.checkWall(x + width, y);
		points[3] = myMap.checkWall(x, y + height / 2);
		points[4] = myMap.checkWall(x + width, y + height / 2);
		points[5] = myMap.checkWall(x, y + height);
		points[6] = myMap.checkWall(x + width / 2, y + height);
		points[7] = myMap.checkWall(x + width, y + height);
		tiles[0] = myMap.getWallAt(x, y);
		tiles[1] = myMap.getWallAt(x + width / 2, y);
		tiles[2] = myMap.getWallAt(x + width, y);
		tiles[3] = myMap.getWallAt(x, y + height / 2);
		tiles[4] = myMap.getWallAt(x + width, y + height / 2);
		tiles[5] = myMap.getWallAt(x, y + height);
		tiles[6] = myMap.getWallAt(x + width / 2, y + height);
		tiles[7] = myMap.getWallAt(x + width, y + height);
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(String file) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(file));
		image = ii.getImage();
	}
	
	public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
    	return width;
    }
    
    public int getHeight() {
    	return height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
}
