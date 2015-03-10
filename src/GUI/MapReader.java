package GUI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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
			if (line1.contains(",")) {
				String[] dimensions = line1.split(",");
				int width = Integer.parseInt(dimensions[0]);
				int height = Integer.parseInt(dimensions[1]);
				for (int i = 0; i < height; i++) {
					String line = BR.readLine();
					// System.out.println(line);
					for (int j = 0; j < width; j++) {
						char c = line.charAt(j);
						if (c == 'W')
							tiles.add(new Block(32 * j, 32 * i));
					}
				}
			} else {
				int i = 0;
				while (line1 != null) {
					for (int j = 0; j < line1.length(); j++) {
						char c = line1.charAt(j);
						if (c == 'W')
							tiles.add(new Block(32*j,32*i));
					}
					line1 = BR.readLine();
					i += 1;
				}
			}
			BR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return tiles;
	}
}