package Objects;

import java.io.File;
import java.net.MalformedURLException;

import javax.swing.ImageIcon;

public class BasicBlock extends Block {
	public BasicBlock(int x, int y) {
		
    	ImageIcon ii = null;
		try {
			ii = new ImageIcon((new File(IMAGE_PATH + "block.png")).toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
        image = ii.getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        visible = true;
        this.x = x;
        this.y = y;
    }
}
