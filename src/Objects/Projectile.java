package Objects;


public abstract class Projectile extends Element {	
	public int dx,dy;
	public boolean friendly; //friendly if projectile of mainchar
	public static final String IMAGE_PATH = "Images/Projectiles/";
    
    public void move() {
    	x += dx;
    	y += dy;
    	
    	if (outOfBounds()) {
    		myBoard.getProjectiles().remove(this);
    	}
    }
    
}
