package Objects.Items;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import GUI.Board;
import GUI.Map;
import Objects.Projectiles.BasicBullet;
import Objects.Projectiles.Projectile;

public class gun2 extends Gun {
	private String name = "Gun 2.0";
	private String description = "Double shooter.";
	
	public gun2() {
		reloadRate = 360;
		loaded = true;
		ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + "gun2.png")).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		image = ii.getImage();
		xOffset = 0;
		yOffset = 8;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isFireable() {
		return true;
	}

	@Override
	public void fire(int x, int y, int width, int height, int dir, Map m, Board b) {
		if (!loaded) {
			return;
		}
		int bullet_dx = 10 * dir;
		int bullet_dy = 0;
		int bullet_x, bullet_y;

		BasicBullet bullet = new BasicBullet(0, 0, bullet_dx, bullet_dy, m,
				b);
		int b_width = bullet.getWidth();
		int b_height = bullet.getHeight();
		if (dir == 1) {
			bullet_x = x + width;
			bullet_y = y + height / 2 - b_height / 2;
		} else {
			bullet_x = x - b_width;
			bullet_y = y + height / 2 - b_height / 2;
		}
		b.getProjectiles().add(
				new BasicBullet(bullet_x, bullet_y, bullet_dx, bullet_dy-2,
						m, b));
		b.getProjectiles().add(
				new BasicBullet(bullet_x, bullet_y, bullet_dx, bullet_dy+2,
						m, b));
		super.fire();
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public boolean isConsumable() {
		return false;
	}

	@Override
	public boolean isWearable() {
		return false;
	}

}
