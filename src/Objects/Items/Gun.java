package Objects.Items;

import java.awt.Image;

import GUI.Board;
import GUI.Map;

public abstract class Gun extends Item {

	protected int reloadRate;
	protected boolean loaded;
	protected int reload = 0;
	protected Image leftImage;
	protected Image rightImage;
	public static final String IMAGE_PATH = "Images/Items/Guns/";

	protected void init() {
		reload = reloadRate;
	}
	
	public abstract void fire(int x, int y, int width, int height, int dir,
			Map m, Board b);

	public void fire() {
		loaded = false;
		reload = 0;
	}
	
	public boolean charged() {
		return false;
	}
	
	public Image getLeftImage() {
		return leftImage;
	}
	
	public Image getRightImage() {
		return rightImage;
	}
	
	public void dud() {
		
	}
	
	public double chargeRatio() {
		return 0;
	}
	public double reloadRatio() {
		return ((double)reload)/((double)reloadRate);
	}
	
	public void release(int x, int y, int width, int height, int dir, Map m, Board b) {
		
	}

	public void loadMe(int time) {
		if (!loaded) {
			reload += time;
			if (reload > reloadRate) {
				loaded = true;
			}
		}
		if (reload > reloadRate) {
			reload = reloadRate;
		}
	}
}
