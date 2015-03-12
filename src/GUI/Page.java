package GUI;

import java.util.ArrayList;

public interface Page {

	public ArrayList<Button> getButtons();

	public String getTitle();

	public void setTitle(String title);
	
	public int gettSize();
	
	public void settSize(int tSize);
	
}
