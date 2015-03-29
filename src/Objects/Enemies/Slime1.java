package Objects.Enemies;

import GUI.Board;
import GUI.Map;
import Objects.Tiles.Tile;

public class Slime1 extends Enemy {
	
	
	public Slime1 (Double x, Double y, Map M, Board B) {
		SPEED = 3;
		MAX_VERTICAL_SPEED = 6;
		JUMP_SPEED = -3;
		System.out.println("Spawning slime at " + Double.toString(x) + " " + Double.toString(y));
		setImage(IMAGE_PATH + "slime1.png");
		health = 240;
		initEnemy(x,y,M,B);
		visible = true;
		dx = -SPEED;
		dy = 0;
		damage = 1;
		xKnockBack = 10;
		yKnockBack = 4;
		name = "Green Slime";
		myAI = new LeapAI(1000);
	}
	
}
