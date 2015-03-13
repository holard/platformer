package Objects.Enemies;

import GUI.Board;
import GUI.Map;
import Objects.Element;
import Objects.Projectiles.Projectile;

public abstract class Enemy extends Element {
	protected int health;
	public static final String IMAGE_PATH = "Images/Enemies/";
	
	public void initEnemy(double x, double y, Map M, Board B) {
		this.x = x;
		this.y = y;
		myMap = M;
		myBoard = B;
	}
	
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void hit(Projectile p) {
		health -= p.getDamage();
		if (health <= 0)
			die();
	}
	
	public void die() {
		myBoard.getEnemies().remove(this);
	}
	
	public abstract void move();
}
