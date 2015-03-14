package Objects.Enemies;

import GUI.Board;
import GUI.Map;
import Objects.Tiles.Tile;

public class Slime2 extends Enemy {
	
	public Slime2 (Double x, Double y, Map M, Board B) {
		SPEED = 3;
		MAX_VERTICAL_SPEED = 6;
		JUMP_SPEED = -1;
		System.out.println("Spawning slime at " + Double.toString(x) + " " + Double.toString(y));
		setImage(IMAGE_PATH + "slime2.png");
		initEnemy(x,y,M,B);
		health = 150;
		visible = true;
		dx = -SPEED;
		dy = 0;
		damage = 1;
		xKnockBack = 10;
		yKnockBack = 4;
		
		myAI = new platformGuardAI();
	}
}
