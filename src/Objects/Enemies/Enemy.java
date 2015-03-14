package Objects.Enemies;

import GUI.Board;
import GUI.Map;
import Objects.Element;
import Objects.Projectiles.Projectile;

public abstract class Enemy extends Element {
	protected int health;
	public static final String IMAGE_PATH = "Images/Enemies/";
	protected int damage;
	protected int xKnockBack;
	protected int yKnockBack;
	protected int SPEED;
	protected int MAX_VERTICAL_SPEED;
	protected int JUMP_SPEED;
	protected double dx;
	protected double dy;
	protected AI myAI;
	public void initEnemy(double x, double y, Map M, Board B) {
		this.x = x;
		this.y = y;
		myMap = M;
		myBoard = B;
	}
	public int getDamage() {
		return damage;
	}
	
	public int getXKnockBack() {
		return xKnockBack;
	}
	public int getYKnockBack() {
		return yKnockBack;
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
	
	public void move() {
		double[] toDo = myAI.move(x, y, dx, dy, myMap, this);
		x = toDo[0];
		y = toDo[1];
		dx = toDo[2];
		dy = toDo[3];
	}
}
