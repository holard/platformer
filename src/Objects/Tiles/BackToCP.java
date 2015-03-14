package Objects.Tiles;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import GUI.Pair;

public class BackToCP implements Tile {
	public static final String IMAGE_PATH = "Images/Tiles/";
	protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
	public BackToCP(int x, int y) {

		ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + "lava.png")).toURI()
					.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		image = ii.getImage();
		width = image.getWidth(null);
		height = image.getHeight(null);
		visible = true;
		this.x = x;
		this.y = y;
	}

	@Override
	public Pair<Integer, Integer> getPosition() {
		return new Pair<Integer,Integer>(x,y);
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public boolean isWall() {
		return false;
	}

	@Override
	public boolean canStick() {
		return false;
	}

	@Override
	public boolean isLava() {
		return false;
	}

	@Override
	public boolean isVisible() {
		return false;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public boolean isCheckPointer() {
		return true;
	}
}
