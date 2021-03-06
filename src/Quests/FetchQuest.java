package Quests;

import Objects.DataCenter;
import Objects.Items.Item;

public class FetchQuest extends BasicQuest {
	private String item;
	
	public FetchQuest(String name, String toFetch, int quantity, DataCenter dc) {
		myName = name;
		target = quantity;
		num = 0;
		item = toFetch;
		data = dc;
	}
	
	public boolean isFetch() {
		return true;
	}
	public boolean isKill() {
		return false;
	}
	public boolean isTalk() {
		return false;
	}
	
	@Override
	public String getTask() {
		return "Collect " + item + "(s): " + num + "/" + target;
	}
	public String getName() {
		return myName;
	}
	public String getItem() {
		return item;
	}
	

	
}
