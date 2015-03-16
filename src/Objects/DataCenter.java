package Objects;

import java.util.ArrayList;

import Objects.Items.Bag;
import Objects.Items.Gun;

public class DataCenter {
	
	private ArrayList<Gun> myGuns;
	private Bag myBag;
	private MainChar main;
	
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
	public Bag getMyBag() {
		return myBag;
	}
	public void setMyBag(Bag myBag) {
		this.myBag = myBag;
	}
	
	
}
