package Objects.Projectiles;

import GUI.Board;
import GUI.Map;
import Objects.Element;
import Objects.Enemies.Enemy;
import Objects.Tiles.Tile;

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
			if (timer == 0)
				death();
		}
		Tile t = myMap.getTileAt((int)(x+width/2), (int)(y+height/2));
		if (t!=null && t.isLava()) {
			myBoard.getProjectiles().remove(this);
		}
		Enemy e = enemyCollide();
		if (e != null) {
			e.hit(this);
			if (damage != 0) {
				timer = (int)(40/(Math.abs(dx)+dy));
			}
			damage = 0;

		}
	}

	public int getDamage() {
		return damage;
	}

	public void death() {

	}

}
