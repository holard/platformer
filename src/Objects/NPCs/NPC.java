package Objects.NPCs;

import java.util.ArrayList;

import GUI.Pages.Page;
import Objects.Element;


public abstract class NPC extends Element{
	ArrayList<Page> dialogue;
	Page curPage;
	abstract boolean isShop();
	abstract boolean hasQuest();
}
