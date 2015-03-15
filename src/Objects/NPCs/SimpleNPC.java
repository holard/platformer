package Objects.NPCs;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import GUI.Board;

public class SimpleNPC extends NPC{
	private boolean talk;
	
	public SimpleNPC(String name1, Board b, double x, double y) {
		myBoard = b;
		name = name1;
		this.x = x;
		this.y = y;
		setImage(IMAGE_PATH + name + ".png");
		text = new NPCText(TEXT_PATH + name + ".txt", this);
		talk = false;
		visible = true;
	}
	
	@Override
	public void interact(int key) {
		if (key == Board.F) {
			text.pressConfirm();
		}
		else if (key == Board.UP) {
			if (!text.getTextOrOption())
				text.changeOption((text.getOpIndex() - 1) % text.getOptions().length);
		}
		else if (key == Board.DOWN) {
			if (!text.getTextOrOption())
				text.changeOption((text.getOpIndex() + 1) % text.getOptions().length);
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		text.draw(g2d);
	}
	
	@Override
	public void closeBox() {
		myBoard.setNPC(null);
		talk = false;
	}

	@Override
	public boolean isShop() {
		return false;
	}

	@Override
	public boolean hasQuest() {
		return false;
	}

	

}
