package Quests;

public interface Quest {
	boolean isComplete();
	String getTask();
	String getName();
	boolean isFetch();
	boolean isKill();
	boolean isTalk();
}
