package Objects.Items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import Objects.Element;

public class Bag {
	
	private int capacity;
	private ArrayList<Item> contents;

	public Bag(int c) {
		capacity = c;
		contents = new ArrayList<Item>();
	}

	public int getCapacity() {
		return capacity;
	}

	public ArrayList<Item> getContents() {
		return contents;
	}

	public boolean isFull() {
		return contents.size() >= capacity;
	}

	public boolean put(Item i) {
		if (isFull())
			return false;
		contents.add(i);
		return true;
	}

	public void draw(Graphics2D g2d, int x, int y, int perRow) {
		g2d.setColor(Color.WHITE);
		for (int i = 0; i <= capacity / perRow; i++) {
			for (int j = 0; j < perRow && j + i * perRow < capacity; j++) {

				g2d.setColor(Color.LIGHT_GRAY);
				
				g2d.fillRect(x + j * (Element.SCALE + 16), y + i
						* (Element.SCALE + 16), Element.SCALE, Element.SCALE);

				g2d.setColor(Color.WHITE);
				
				g2d.drawRect(x + j * (Element.SCALE + 16), y + i
						* (Element.SCALE + 16), Element.SCALE, Element.SCALE);
				
				
				if (j + i * perRow < contents.size()) {
					Item it = contents.get(j + i * perRow);
					int w = it.getImage().getWidth(null);
					int h = it.getImage().getHeight(null);
					g2d.drawImage(it.getImage(), x + j * (Element.SCALE + 16)
							- (w - Element.SCALE)/2, y + i * (Element.SCALE + 16)
							- (h - Element.SCALE)/2, null);
				}
			}
		}
	}

}
