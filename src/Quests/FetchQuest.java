package Quests;

import Objects.Items.Item;

public class FetchQuest implements Quest {
	private String item;
	private int target;
	private int num;
	private String myName;
	
	public FetchQuest(String name, String toFetch, int quantity) {
		myName = name;
		target = quantity;
		num = 0;
		item = toFetch;
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
	public boolean isComplete() {
		return num >= target;
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
	public void increment(int n) {
		num += n;
	}
}
