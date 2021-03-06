package GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Objects.Tiles.BackToCP;
import Objects.Tiles.BasicBlock;
import Objects.Tiles.Block;
import Objects.Tiles.Lava;
import Objects.Tiles.Tile;

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
					else if (c == 'L')
						tiles.add(new Lava(32 * j, 32 * i));
					else if (c == 'l')
						tiles.add(new Lava(32 * j, 32 * i, 0)); //Not surface lava
					else if (c == 'B')
						tiles.add(new BackToCP(32 * j, 32 * i));

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