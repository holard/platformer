package Objects.Projectiles;

import Objects.Element;


public abstract class Projectile extends Element {	
	public int dx,dy;
	public boolean friendly; //friendly if projectile of mainchar
	public static final String IMAGE_PATH = "Images/Projectiles/";
    public int mass;
    public void move() {
    	x += dx;
    	y += dy;
    	
    	if (outOfBounds()) {
    		myBoard.getProjectiles().remove(this);
    	}
    }
    
}
