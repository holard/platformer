package GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Objects.BasicBlock;
import Objects.Block;
import Objects.Tile;

public class MapReader {
	private String mapFile;

	public MapReader(String file) {
		mapFile = file;
	}

	public void setMap(String file) {
		mapFile = file;
	}

	public String accessMap() {
		return mapFile;
	}

	public ArrayList<Tile> makeMap() {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		BufferedReader BR;
		try {
			BR = new BufferedReader(new FileReader(mapFile));
			String line1 = BR.readLine();
			
			int i = 0;
			while (line1 != null) {
				for (int j = 0; j < line1.length(); j++) {
					char c = line1.charAt(j);
					if (c == 'W')
						tiles.add(new BasicBlock(32 * j, 32 * i));
				}
				line1 = BR.readLine();
				i += 1;				
			}

			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tiles;
	}
}