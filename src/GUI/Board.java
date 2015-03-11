package GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    public static final int CAM_HB = 192;
    public static final int CAM_VB = 128;
    public static final int TIMER = 15;
    private MainChar craft;
    private ArrayList<Tile> blocks;
    private ArrayList<Projectile> projectiles;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
    private Map M;
    private int camx;
    private int camy;
    private int camw;
    private int camh;
    public int mapwidth;
    public int mapheight;
    
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
        mapwidth = 0;
        mapheight = 0;
        
        for (Tile t : blocks) {
        	if (t.getPosition().first()+48 > mapwidth) {
        		mapwidth = t.getPosition().first()+32;
        	}
        	if (t.getPosition().second()+48 > mapheight) {
        		mapheight = t.getPosition().second()+60;
        	}
        }
        craft = new MainChar(M, this);
        timer = new Timer(TIMER, this);
        timer.start();
    }
    
    public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }
    
    public void initGame() {
    	MapReader MR = new MapReader("src/GUI/map1.txt");
    	blocks = MR.makeMap();
    	projectiles = new ArrayList<Projectile>();
    	System.out.println("initGame Board");
        M = new Map(blocks);
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        if (ingame) 
        {
        	if (craft.getX() < camx+CAM_HB) {
        		camx = Math.max(0, craft.getX() - CAM_HB);
        	}
        	if (craft.getX()+MainChar.SCALE > camx+camw-CAM_HB) {
        		camx = Math.min(mapwidth-camw, craft.getX() + CAM_HB - camw+MainChar.SCALE);
        	}
        	if (craft.getY() < camy+CAM_VB) {
        		camy = Math.max(0, craft.getY() - CAM_VB);
        	}
        	if (craft.getY()+MainChar.SCALE > camy+camh-CAM_VB) {
        		camy = Math.min(mapheight-camh, craft.getY() + CAM_VB - camh+MainChar.SCALE);
        	}
        	
            Graphics2D g2d = (Graphics2D)g;
            if (craft.isVisible())
                g2d.drawImage(craft.getImage(), craft.getX() - camx, craft.getY() - camy,
                              this);
            
            for (int i = 0; i < blocks.size(); i++) {
                Block a = (Block)blocks.get(i);
                if (a.isVisible())
                    g2d.drawImage(a.getImage(), a.getX() - camx, a.getY()- camy, this);
            }
            
            for (int i = 0; i < projectiles.size(); i++) {
            	Projectile p = (Projectile)projectiles.get(i);
            	if (p.getX()<-64 || p.getX() > mapwidth+64 || p.getY() < -64 || p.getY() > mapheight+64) {
            		projectiles.remove(p);
            		i-=1;
            	}
            	if (p.isVisible())
            		g2d.drawImage(p.getImage(), p.getX() - camx, p.getY()- camy, this);
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