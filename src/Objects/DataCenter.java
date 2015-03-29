package Objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Objects.Items.Bag;
import Objects.Items.Gun;
import Objects.Items.Item;
import Quests.FetchQuest;
import Quests.KillQuest;
import Quests.Quest;

public class DataCenter {
	public static int NOTIF_X = 560;
	public static int NOTIF_Y = 380;
	public static int NOTIF_W = 240;
	public static int NOTIF_H = 60;
	public static int NOTIF_TIME = 100;
	public static int BSIZE = 15;
	
	private ArrayList<Gun> myGuns;
	private Bag myBag;
	private MainChar main;
	private ArrayList<Quest> allQuests;
	private ArrayList<FetchQuest> fQuests;
	private ArrayList<KillQuest> kQuests;
	private String notifString;
	private int notifTimer;

	public DataCenter() {
		allQuests = new ArrayList<Quest>();
		fQuests = new ArrayList<FetchQuest>();
		kQuests = new ArrayList<KillQuest>();
		notifString = "";
		notifTimer = 0;
		addQuest(new FetchQuest("My First Questerino!", "Basic Gun", 3, this));
		addQuest(new KillQuest("My First Killerino!", "Green Slime", 2, this));
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
			fQuests.add((FetchQuest) q);
		}
		if (q.isKill()) {
			kQuests.add((KillQuest) q);
		}
	}

	public void notifyDeath(String name) {
		for (KillQuest kq : kQuests) {
			if (kq.getEnemy().equals(name)) {
				kq.increment(1);
			}
		}
	}

	public void notifyQuestComplete(Quest q) {
		notifTimer = NOTIF_TIME;
		notifString = "Quest complete: " + q.getName();
	}

	public void drawNotification(Graphics2D g2d) {
		if (notifTimer == 0 || notifString.equals("")) {
			return;
		} else {
			notifTimer -= 1;
			g2d.setColor(Color.YELLOW);
			g2d.fillRect(NOTIF_X - BSIZE, NOTIF_Y - BSIZE, NOTIF_W + BSIZE * 2,
					NOTIF_H + BSIZE * 2);
			g2d.setColor(Color.WHITE);
			g2d.fillRect(NOTIF_X, NOTIF_Y, NOTIF_W, NOTIF_H);
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("SERIF",0,14));
			g2d.drawString(notifString, NOTIF_X + BSIZE, NOTIF_Y+BSIZE);
			return;
		}
	}

}
