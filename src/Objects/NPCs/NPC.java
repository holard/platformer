package Objects.NPCs;

import java.awt.Graphics;
import java.awt.Graphics2D;

import GUI.Board;
import Objects.Element;

public abstract class NPC extends Element{
	public static final String IMAGE_PATH = "Images/NPCS/";
	public static final String TEXT_PATH = "NPCTexts/";
	
	protected Board myBoard;
	protected String name;
	protected NPCText text;
	abstract void closeBox();
	public abstract void interact(int key);
	public abstract void draw(Graphics2D g2d);
	public abstract boolean isShop();
	public abstract boolean hasQuest();
	
	public NPCText getText() {
		return text;
	}
	
	public String getName() {
		return name;
	}
}
