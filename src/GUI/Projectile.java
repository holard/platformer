package GUI;


public abstract class Projectile extends Element{	
	public int dx,dy;
	public boolean friendly; //friendly if projectile of mainchar
    
    public void move() {
    	x += dx;
    	y += dy;
    	
    	if (outOfBounds()) {
    		myBoard.getProjectiles().remove(this);
    	}
    }
    
}
