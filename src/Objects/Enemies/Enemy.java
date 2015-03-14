package Objects.Enemies;

import java.awt.Color;
import java.awt.Graphics2D;

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
	protected int mhealth;

	public void initEnemy(double x, double y, Map M, Board B) {
		this.x = x;
		this.y = y;
		myMap = M;
		myBoard = B;
		mhealth = health;
	}

	public void drawHealth(Graphics2D g2d, int camX, int camY) {
		if (health < mhealth) {
			g2d.setColor(Color.RED);
			g2d.drawRect((int) x - camX, (int) y - 20 - camY, width, 16);
			g2d.fillRect((int) x - camX, (int) y - 20 - camY, (int)(width
					* ((double) health / (double) mhealth)), 16);
		}
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
