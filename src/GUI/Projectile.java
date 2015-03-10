package GUI;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class Projectile {
	public int x,y;
	public Image image;
	boolean visible;
	public int width, height;
	
	public int dx,dy;
	
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public void move() {
    	x += dx;
    	y += dy;
    }
    
}
