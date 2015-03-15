package Objects;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import GUI.Board;
import GUI.Map;
import Objects.Enemies.Enemy;
import Objects.Tiles.Tile;

public abstract class Element {
	public static final int SCALE = 32;
	protected double x,y;
	protected Image image;
	protected boolean visible;
	protected int width, height;
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
	
	//Returns colliding enemy otherwise nulls
	public Enemy enemyCollide() {
		Enemy[] fpquery = new Enemy[4];
		
		//0:top left, 1: top right, 2: bottom left, 3:bottom right;
		fpquery[0] = myBoard.checkEnemy(x,y);
		fpquery[1] = myBoard.checkEnemy(x + width,y);
		fpquery[2] = myBoard.checkEnemy(x,y + height);
		fpquery[3] = myBoard.checkEnemy(x + width,y + height);
		
		for (int i = 0; i < 4; i++) {
			if (fpquery[i] != null)
				return fpquery[i];
		}
		return null;
	}
	
	public boolean outOfBounds() {
		return ((x + width/2 > myMap.getWidth()) ||
				(y + height/2 > myMap.getHeight()) ||
				(x + width/2 < 0) ||
				(y + height/2 < 0));
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(String file) {
		ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(file)).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		image = ii.getImage();
		
		width = image.getWidth(null);
		height = image.getHeight(null);
	}
	
	public void setImage(Image ii) {
		image = ii;
	}
	
	public int getX() {
        return (int)(Math.round(x));
    }

    public int getY() {
        return (int)(Math.round(y));
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
        return new Rectangle(getX(), getY(), width, height);
    }
    
    public void setX(int x) {
    	this.x = x;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
}
