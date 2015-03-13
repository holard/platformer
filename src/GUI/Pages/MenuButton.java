package GUI.Pages;

public class MenuButton implements Button{
	private int x;
	private int y;
	private int destination;
	private String name;
	private int size;
	
	public MenuButton(int x, int y, int dest, String n, int size) {
		this.x = x;
		this.y = y;
		destination = dest;
		name = n;
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
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
