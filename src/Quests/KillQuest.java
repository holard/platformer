package Quests;

import Objects.DataCenter;
import Objects.Items.Item;

public class KillQuest extends BasicQuest {
	private String enemy;
	
	
	public KillQuest(String name, String toKill, int quantity, DataCenter dc) {
		myName = name;
		target = quantity;
		num = 0;
		enemy = toKill;
		data = dc;
	}
	
	public boolean isFetch() {
		return false;
	}
	public boolean isKill() {
		return true;
	}
	public boolean isTalk() {
		return false;
	}
	@Override
	public String getTask() {
		return "Kill " + enemy + "(s): " + num + "/" + target;
	}
	public String getName() {
		return myName;
	}
	public String getEnemy() {
		return enemy;
	}
}
