package Objects.Items;

import GUI.Board;
import GUI.Map;

public abstract class Gun extends Item {

	protected int reloadRate;
	protected boolean loaded;
	protected int reload = 0;

	public abstract void fire(int x, int y, int width, int height, int dir,
			Map m, Board b);

	public void fire() {
		loaded = false;
		reload = 0;
	}

	public void loadMe(int time) {
		if (!loaded) {
			reload += time;
			if (reload > reloadRate) {
				reload = 0;
				loaded = true;
			}
		}
	}
}
