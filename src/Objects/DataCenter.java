package Objects;

import java.util.ArrayList;

import Objects.Items.Bag;
import Objects.Items.Gun;
import Objects.Items.Item;
import Quests.FetchQuest;
import Quests.Quest;

public class DataCenter {
	
	private ArrayList<Gun> myGuns;
	private Bag myBag;
	private MainChar main;
	private ArrayList<Quest> allQuests;
	private ArrayList<FetchQuest> fQuests;
	
	public DataCenter() {
		allQuests = new ArrayList<Quest>();
		fQuests = new ArrayList<FetchQuest>();
		addQuest(new FetchQuest("My First Questerino!", "Basic Gun", 3));
	}
	
	public ArrayList<Gun> getMyGuns() {
		return myGuns;
	}
	public void setMyGuns(ArrayList<Gun> myGuns) {
		this.myGuns = myGuns;
	}
	
	public MainChar getMain() {
		return main;
	}
	public void setMain(MainChar main) {
		this.main = main;
	}
	public void addToBag(Item i) {
		myBag.put(i);
		for (FetchQuest fq : fQuests) {
			if (fq.getItem().equals(i.getName())) {
				fq.increment(1);
			}
		}
	}
	public Bag getMyBag() {
		return myBag;
	}
	public void setMyBag(Bag myBag) {
		this.myBag = myBag;
	}
	public ArrayList<Quest> getQuests() {
		return allQuests;
	}
	public void addQuest(Quest q) {
		allQuests.add(q);
		if (q.isFetch()) {
			fQuests.add((FetchQuest)q);
		}
	}
	
}
