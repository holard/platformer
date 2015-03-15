package Objects.Enemies;

import GUI.Board;
import GUI.Map;
import Objects.Tiles.Tile;

public class Slime2 extends Enemy {
	
	public Slime2 (Double x, Double y, Map M, Board B) {
		SPEED = 5;
		MAX_VERTICAL_SPEED = 8;
		JUMP_SPEED = -6;
		System.out.println("Spawning slime at " + Double.toString(x) + " " + Double.toString(y));
		setImage(IMAGE_PATH + "slime2.png");
		health = 300;
		initEnemy(x,y,M,B);
		visible = true;
		dx = -SPEED;
		dy = 0;
		damage = 1;
		xKnockBack = 10;
		yKnockBack = 4;
		
		myAI = new AggressiveLeapAI(1000,200, SCALE*5);
	}
}
