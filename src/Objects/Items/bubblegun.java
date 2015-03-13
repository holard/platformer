package Objects.Items;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import GUI.Board;
import GUI.Map;
import Objects.Projectiles.BasicBullet;
import Objects.Projectiles.BubbleProj;
import Objects.Projectiles.Projectile;

public class bubblegun extends Gun {
	private String name = "BubbleGun";
	private String description = "Shoots bubbles. What did you expect?";
	
	public bubblegun() {
		reloadRate = 1;
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
		int bullet_dx = 3 * dir;
		int bullet_dy = 0;
		int bullet_x, bullet_y;

		BubbleProj bullet = new BubbleProj(0, 0, bullet_dx, bullet_dy, m,
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
		Random r = new Random();
		for (int i = 0; i < 10; i++)
		b.getProjectiles().add(
				new BubbleProj(bullet_x, bullet_y, bullet_dx, bullet_dy-(int)(5*r.nextDouble()),
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
