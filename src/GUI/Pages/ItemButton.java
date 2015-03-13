package GUI.Pages;

import Objects.Items.Item;

public class ItemButton implements Button{
	private int x;
	private int y;
	private int destination;
	private Item item;
	private int size;
	
	public ItemButton(int x, int y, int dest, Item i, int size) {
		this.x = x;
		this.y = y;
		destination = dest;
		item = i;
		this.setSize(size);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public String getName() {
		return item.getName();
	}
	public Item getItem() {
		return item;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public void setName(String name) {
		
	}
	
}
