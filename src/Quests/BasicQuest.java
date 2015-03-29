package Quests;

import Objects.DataCenter;

public abstract class BasicQuest implements Quest{
	protected int target;
	protected int num;
	protected String myName;
	protected DataCenter data;
	
	@Override
	public void notifyComplete() {
		data.notifyQuestComplete(this);
	}
	
	@Override
	public boolean isComplete() {
		return num >= target;
	}
	
	public void increment(int n) {
		boolean wascomp = isComplete();
		num += n;
		if (!wascomp && num >= target) {
			notifyComplete();
		}
	}
}
