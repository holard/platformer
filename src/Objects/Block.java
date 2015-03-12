package Objects;

import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import GUI.Pair;


public abstract class Block implements Tile{

    public static final String IMAGE_PATH = "Images/Tiles/";

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


	@Override
	public Pair<Integer, Integer> getPosition() {
		return new Pair<Integer,Integer>(x,y);
	}


	@Override
	public boolean isWall() {
		return true;
	}


	@Override
	public boolean canStick() {
		return true;
	}
}