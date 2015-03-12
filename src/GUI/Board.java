package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    public static final int CAM_HB = 192;
    public static final int CAM_VB = 128;
    public static final int TIMER = 15;
    public static final int GLOBALX_START = 0;
    public static final int GLOBALY_START = 0;
    public static final String START_MAPFILE = "src/GUI/map1.txt";
    private MainChar craft;
    private ArrayList<Tile> blocks;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Pair<int[], String>> xychart;
    private boolean ingame;
    private Map M;
    private int camx;
    private int camy;
    private int camw;
    private int camh;
    private int globalx; 
    private int globaly;
    
    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;
        setSize(400, 300);
        
        camx = 0;
        camy = 0;
        camw = MainMethod.WIDTH;
        camh = MainMethod.HEIGHT;
        initGame();
        
        
        timer = new Timer(TIMER, this);
        timer.start();
    }
    
    
    public void initGame() {
    	craft = new MainChar(this);
    	setMap(START_MAPFILE);
        globalx = GLOBALX_START;
        globaly = GLOBALY_START;
        readXYChart();
    }
    
    /*
	 * Format for xychart.txt:
	 * sample line:
	 * <x> <y> <width> <height> <map filename>
	 * x and y are the map's global position.
	 */
	public void readXYChart() {
		ArrayList<Pair<int[], String>> chart = new ArrayList<Pair<int[], String>>();
		try {
			BufferedReader BR = new BufferedReader(new FileReader("src/GUI/xychart.txt"));
			String line = BR.readLine();
			String[] elems;
			int[] xywh;
			while (line != null) {
				elems = line.split(" ");
				xywh = new int[4];
				xywh[0] = Integer.parseInt(elems[0]);
				xywh[1] = Integer.parseInt(elems[1]);
				xywh[2] = Integer.parseInt(elems[2]);
				xywh[3] = Integer.parseInt(elems[3]);
				chart.add(new Pair<int[],String>(xywh,elems[4]));
				line = BR.readLine();
			}
			
			BR.close();
			xychart = chart;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Change map depending on what code is
	 * 0: change because of main char
	 */
	public void changeMap(int code) {
		String newfile = "no file!";
		
		if (code == 0) {
			int char_globalx = globalx + craft.getX() + craft.getWidth() / 2;
			int char_globaly = globaly + craft.getY() + craft.getHeight() / 2;
			
			//Find the map containing the out of bounds character
			Pair<int[],String> newMap;
			int[] new_xywh = new int[4];
			for (int i = 0; i < xychart.size(); i++) {
				newMap = xychart.get(i);
				new_xywh = newMap.first();
				if (char_globalx >= new_xywh[0] && 
					char_globaly >= new_xywh[1] &&
					char_globalx < new_xywh[0] + new_xywh[2] &&
					char_globaly < new_xywh[2] + new_xywh[3]) {
					newfile = newMap.second();
					break;
				}
			}
			
			if (newfile == "no file!")
				return;

			// Set the character's new relative x and y
			craft.setX(craft.getX() + (globalx - new_xywh[0]));
			craft.setY(craft.getY() + (globaly - new_xywh[1]));

			// Fix globalx, globaly
			globalx = new_xywh[0];
			globaly = new_xywh[1];			
		}
		
		setMap(newfile);
	}
	
	public void setMap(String file) {
		MapReader MR = new MapReader(file);
		blocks = MR.makeMap();
		projectiles = new ArrayList<Projectile>();
		M = new Map(blocks);
		craft.myMap = M;
	}
    
    public void paint(Graphics g) {
        super.paint(g);
        if (ingame) 
        {
        	if (craft.getX() < camx + CAM_HB) {
        		camx = Math.max(0, craft.getX() - CAM_HB);
        	}
        	if (craft.getX() + craft.getWidth() > camx + camw - CAM_HB) {
        		camx = Math.min(M.getWidth() - camw, craft.getX() + CAM_HB - camw + craft.getWidth());
        	}
        	if (craft.getY() < camy+CAM_VB) {
        		camy = Math.max(0, craft.getY() - CAM_VB);
        	}
        	if (craft.getY() + craft.getHeight() > camy + camh - CAM_VB) {
        		camy = Math.min(M.getHeight() - camh, craft.getY() + CAM_VB - camh + craft.getHeight());
        	}
        	
            Graphics2D g2d = (Graphics2D)g;
            if (craft.isVisible())
                g2d.drawImage(craft.getImage(), craft.getX() - camx, craft.getY() - camy,
                              this);
            
            for (int i = 0; i < blocks.size(); i++) {
                Block a = (Block)blocks.get(i);
                if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX() - camx, a.getY() - camy, this);
            }
            
            for (int i = 0; i < projectiles.size(); i++) {
            	Projectile p = (Projectile)projectiles.get(i);
            	if (p.isVisible())
            		g2d.drawImage(p.getImage(), p.getX() - camx, p.getY() - camy, this);
            }
            
            g2d.setColor(Color.WHITE);
        } 
        else 
        {
            //Do not ingame stuff
        }
        
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }
    
    public void actionPerformed(ActionEvent e) {    	
        craft.move();
        for (int i = 0; i < projectiles.size(); i++) {
        	Projectile p = projectiles.get(i);
        	p.move();
        }
        repaint();  
    }
    
    public ArrayList<Projectile> getProjectiles() {
    	return projectiles;
    }
    
    private class TAdapter extends KeyAdapter {
        public void keyReleased(KeyEvent e) {
            craft.keyReleased(e);
        }
        public void keyPressed(KeyEvent e) {
            craft.keyPressed(e);
        }
    }
}