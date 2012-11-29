import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Sprite {
	
	Image image;
	
	public Sprite(String image_name) {
		image = loadImage(image_name);
	}
	
	public Image getImage() {
		return image;
	}
	
	private static Image loadImage(String name) {
		File file = new File("data/images", name);
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
			
			return null;
		}
	}
}
