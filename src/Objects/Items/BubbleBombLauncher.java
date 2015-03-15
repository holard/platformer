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
import Objects.Projectiles.BubbleBomb;
import Objects.Projectiles.BubbleProj;
import Objects.Projectiles.Projectile;

public class BubbleBombLauncher extends Gun {
	private String name = "BubbleBomber";
	private String description = "Blows big, bursting, bubble bombs";
	private double charge;
	private static final int MAX_CHARGE = 11;

	public BubbleBombLauncher() {
		reloadRate = 1500;
		loaded = true;
		ImageIcon left = null;
		ImageIcon right = null;
		try {
			left = new ImageIcon((new File(IMAGE_PATH + "bubbleBombLauncherLeft.png")).toURI().toURL());
			right = new ImageIcon((new File(IMAGE_PATH + "bubbleBombLauncherRight.png")).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		leftImage = left.getImage();
		rightImage = right.getImage();
		image = rightImage;
		
		xOffset = 0;
		yOffset = 8;
		charge = 0;
		init();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean charged() {
		return true;
	}
	@Override
	public void dud() {
		
		charge = 0;
	}
	@Override
	public void release(int x, int y, int width, int height, int dir, Map m, Board b) {
		if (!loaded || charge == 0)
			return;
		int bullet_dx = (int)(charge+5) * dir;
		int bullet_dy = -(int)(charge+4);
		int bullet_x, bullet_y;

		BubbleBomb bullet = new BubbleBomb(0, 0, bullet_dx, bullet_dy, m,
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
				new BubbleBomb(bullet_x, bullet_y-8, bullet_dx, bullet_dy,
						m, b));
		
		super.fire();
		charge = 0;

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
	public void fire(int x, int y, int width, int height, int dir, Map m,
			Board b) {
		if (!loaded) {
			return;
		}
		if (charge < MAX_CHARGE)
		charge += 0.15;
	}

	@Override
	public Image getImage() {
		return image;
	}
	
	@Override
	public double chargeRatio() {
		return charge/MAX_CHARGE;
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
