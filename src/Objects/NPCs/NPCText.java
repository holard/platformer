package Objects.NPCs;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import GUI.Board;
import GUI.MainMethod;

public class NPCText {
	public static final int NUM_LINES = 4;
	public static final int LINE_HEIGHT = 32;
	public static final int H_SPACE = 128; //horizontal space between edge of window and box
	public static final int H_BUFFER = 20; //space between text and edge of text box
	public static final int V_BUFFER = 20; //space between bottom and text
	public static final int WIDTH = MainMethod.WIDTH - 2 * H_SPACE;
	public static final int TEXT_WIDTH = MainMethod.WIDTH - 2 * H_SPACE - 2 * H_BUFFER;
	public static final int HEIGHT = LINE_HEIGHT * NUM_LINES + V_BUFFER;
	public static final int X = H_SPACE;
	public static final int Y = MainMethod.HEIGHT - LINE_HEIGHT*NUM_LINES - V_BUFFER - LINE_HEIGHT;
	
	private String text;
	private String[] words;
	private int wordId;
	private int nextWordId;
	private String[] options;
	private String textFile;
	private int opIndex; //selected option
	private boolean textOrOption; //true if need to draw text, false if draw options
	private boolean close;
	private NPC myNPC;
	
	private ArrayList<TextBox> textBoxes;
	private HashMap<String, Integer> optionToBoxId;
	
	public NPCText(String file, NPC npc) {
		textFile = file;
		myNPC = npc;
		readText();
		resetText();
		close = false;
	}
	
	private void readText() {
		textBoxes = new ArrayList<TextBox>();
		optionToBoxId = new HashMap<String, Integer>();
		BufferedReader BR;
		
		try {
			BR = new BufferedReader(new FileReader(textFile));
			String line = BR.readLine();
			int numOps;
			
			/* 
			 * format of text line
			 * <marker (inc order from top to bottom in the file)> <text> <# of options>
			 * 
			 * format of option line
			 * <option> <marker of next text if option is selected>
			 * 
			 * dummy line at end of each option list
			 */
			while (line != null) {
				numOps = Integer.parseInt(line.substring(line.trim().length() - 1));
				String lineText = line.substring(2, line.trim().length() - 2);
				
				if (numOps == 0) { //No options = end of dialogue
					textBoxes.add(new TextBox(lineText, null));
				}
				else {
					String[] ops = new String[numOps];
					String opline, opText;
					int opNum;
					
					for (int i = 0; i <= numOps; i++) {
						opline = BR.readLine();
						if (i != numOps) { //ignore dummy line
							opText = opline.substring(0, opline.trim().length() - 2);
							opNum = Integer.parseInt(opline.substring(opline.trim().length() - 1));
							ops[i] = opText;
							optionToBoxId.put(opText, opNum);
						}
					}
					
					System.out.println("linetext: " + lineText);
					textBoxes.add(new TextBox(lineText,ops));
				}
								
				line = BR.readLine();
			}
			
			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void resetText() {
		text = myNPC.getName() + ": " + textBoxes.get(0).getText();
		options = textBoxes.get(0).getOptions();
		opIndex = 0;		
		words = text.split(" ");
		wordId = 0;
		textOrOption = true;
		close = false;
	}
	
	public void pressConfirm() {
		if (close) {
			resetText();
			myNPC.closeBox();
		}
		else if (textOrOption)
			advanceText();
		else
			pickOption();
	}
	
	public void changeOption(int index){
		opIndex = index;
	}
	
	public void advanceText() {
		wordId = nextWordId;
	}
	
	public void pickOption() {
		TextBox newBox = textBoxes.get(optionToBoxId.get(options[opIndex]));
		text = myNPC.getName() + ": " + newBox.getText();
		options = newBox.getOptions();
		opIndex = 0;
		words = text.split(" ");
		wordId = 0;
		textOrOption = true;
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect(X, Y, WIDTH, HEIGHT);
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("SERIF", Font.BOLD, 14));
		
		if (textOrOption) {
			FontMetrics fm = g2d.getFontMetrics();
			String line;
			int oldWordId = wordId;

			if (words.length == 0 || wordId >= words.length) {
				textOrOption = false;
				return;
			}

			for (int i = 0; i < NUM_LINES; i++) {
				line = "";
				while (fm.stringWidth(line + words[wordId] + " ") < TEXT_WIDTH) {
					line += words[wordId] + " ";
					wordId++;	
					
					if (wordId >= words.length) {
						line = line.trim();
						break;
					}
				}
				
				g2d.drawString(line, X + H_BUFFER, Y + LINE_HEIGHT + i * LINE_HEIGHT);
				if (wordId >= words.length) {
					break;
				}
			}
			nextWordId = wordId;
			wordId = oldWordId;
			
		} else {
			
			if (options == null || options.length == 0) {
				close = true;
				resetText();
				myNPC.closeBox();
			}
			else {
				for (int i = 0; i < options.length; i++) {
					if (i == opIndex) {
						g2d.setColor(Color.CYAN);
						g2d.drawString("- " + options[i], X + H_BUFFER, Y + LINE_HEIGHT + i * LINE_HEIGHT);
						g2d.setColor(Color.WHITE);
					}
					else
						g2d.drawString("- " + options[i], X + H_BUFFER, Y + LINE_HEIGHT + i * LINE_HEIGHT);
				}
			}
		}
	}
	
	public String getText() {
		return text;
	}
	
	public String[] getOptions() {
		return options;
	}
	
	public int getOpIndex() {
		return opIndex;
	}
	
	public boolean getTextOrOption() {
		return textOrOption;
	}
	
	//true if close, false otherwise.
	public boolean getClose() {
		return close;
	}
}
