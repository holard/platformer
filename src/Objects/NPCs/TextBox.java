package Objects.NPCs;

public class TextBox {
	private String text;
	private String[] options;
	
	public TextBox(String t, String[] ops) {
		text = t;
		options = ops;
	}
	
	public String getText() {
		return text;
	}
	
	public String[] getOptions() {
		return options;
	}
}
