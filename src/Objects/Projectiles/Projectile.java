package Objects.Projectiles;

import GUI.Board;
import GUI.Map;
import Objects.Element;
import Objects.Enemies.Enemy;


public abstract class Projectile extends Element {	
	public double dx,dy;
	public boolean friendly; //friendly if projectile of mainchar
	public static final String IMAGE_PATH = "Images/Projectiles/";
	protected int damage;
    public int mass;
    
    public void initProjectile(double x, double y, double dx, double dy, Map M, Board B) {
		myMap = M;
		myBoard = B;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
    
    public void move() {
    	x += dx;
    	y += dy;
    	
    	if (outOfBounds()) {
    		myBoard.getProjectiles().remove(this);
    	}
    	
    	Enemy e = enemyCollide();
    	if (e != null) {
    		e.hit(this);
    		myBoard.getProjectiles().remove(this);
    	}
    }
    
    public int getDamage() {
    	return damage;
    }
    
}
