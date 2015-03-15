package GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import Objects.Enemies.Enemy;
import Objects.NPCs.NPC;
import Objects.NPCs.SimpleNPC;

public class ObjectReader {
	private String objectFile;
	private Map myMap;
	private Board myBoard;
	public static final String CLASS_PATH = "Objects.Enemies.";
	
	public ObjectReader(String objFile, Map M, Board B) {
		objectFile = objFile;
		myMap = M;
		myBoard = B;
	}
	
	public void setFile(String file) {
		objectFile = file;
	}
	
	public String getFile() {
		return objectFile;
	}
	
	public ArrayList<Enemy> makeEnemies() {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		BufferedReader BR;
		Class c;
		Constructor ctor;
		Object[] pars = new Object[4];
		pars[2] = myMap;
		pars[3] = myBoard;
		Class[] parClasses = new Class[4];
		parClasses[0] = Double.class;
		parClasses[1] = Double.class;
		parClasses[2] = Map.class;
		parClasses[3] = Board.class;
		
		try {
			BR = new BufferedReader(new FileReader(objectFile));
			String line = BR.readLine();
			String[] elems = new String[4];
			
			//Line format for enemy: Enemy <Name> <x> <y>
			while (line != null) {
				elems = line.split(" ");
								
				if (elems[0].equals("Enemy")) {
					c = Class.forName(CLASS_PATH + elems[1]);
					ctor = c.getDeclaredConstructor(parClasses);
					ctor.setAccessible(true);
					
					pars[0] = Double.parseDouble(elems[2]);
					pars[1] = Double.parseDouble(elems[3]);
					Enemy e = (Enemy)ctor.newInstance(pars);
					if (e == null) {
						System.out.println("enemy is null!");
					}
					enemies.add(e);
				}
				
				line = BR.readLine();
			}			
			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		return enemies;
	}
	
	public ArrayList<NPC> makeNPCs() {
		ArrayList<NPC> npcs = new ArrayList<NPC>();
		BufferedReader BR;
		
		try {
			BR = new BufferedReader(new FileReader(objectFile));
			String line = BR.readLine();
			String[] elems = new String[5];
			
			//line format for NPC: NPC <Type> <Name> <x> <y>
			while (line != null) {
				elems = line.split(" ");
				
				if (elems[0].equals("NPC")) {
					if (elems[1].equals("SimpleNPC")) {
						SimpleNPC n = new SimpleNPC(elems[2], myBoard, 
								Double.parseDouble(elems[3]), Double.parseDouble(elems[4]));
						npcs.add(n);
					}
				}
				
				line = BR.readLine();
			}
			
			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return npcs;
	}
}
