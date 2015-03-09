package GUI;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;


public class Block implements Tile{

    private String craft = "block.png";

    private int x;
    private int y;
    private int width;
    private int height;
    private boolean visible;
    private Image image;

    public Block(int x, int y) {
        ImageIcon ii = new ImageIcon(this.getClass().getResource(craft));
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
    }


    public void move() {
        
    }

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
}