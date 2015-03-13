package Objects.Projectiles;

import GUI.Board;
import GUI.Map;
import Objects.Element;
import Objects.Enemies.Enemy;

public abstract class Projectile extends Element {
	public double dx, dy;
	public boolean friendly; // friendly if projectile of mainchar
	public static final String IMAGE_PATH = "Images/Projectiles/";
	protected int damage;
	public int mass;
	protected int timer = -1;

	public void initProjectile(double x, double y, double dx, double dy, Map M,
			Board B) {
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
		if (timer > 0)
			timer -= 1;

		if (timer == 0 || outOfBounds()) {
			myBoard.getProjectiles().remove(this);
		}

		Enemy e = enemyCollide();
		if (e != null) {
			e.hit(this);
			if (damage != 0) {
				timer = 2;
			}
			damage = 0;

		}
	}

	public int getDamage() {
		return damage;
	}

}
