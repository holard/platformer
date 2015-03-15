package Objects.Tiles;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

import GUI.Pair;

public class Lava implements Tile {
	public static final String IMAGE_PATH = "Images/Tiles/";
	protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;
	public Lava(int x, int y) {

		ImageIcon ii = null;
		String filename = "lava2.png";
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + filename)).toURI()
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
	
	public Lava(int x, int y, int n) {

		ImageIcon ii = null;
		String filename = "lava.png";
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + filename)).toURI()
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
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public boolean isCheckPointer() {
		// TODO Auto-generated method stub
		return false;
	}
}
