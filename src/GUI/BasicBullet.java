package GUI;

import javax.swing.ImageIcon;

public class BasicBullet extends Projectile {
	public BasicBullet(int x, int y, int dx, int dy) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("missile.png"));
		image = ii.getImage();
		visible = true;
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public void move() {
		super.move();
		
	}
}
