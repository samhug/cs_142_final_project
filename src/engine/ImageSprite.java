package engine;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.GameObject.Point;

public class ImageSprite implements engine.Sprite {

	private Image image;

	public ImageSprite(String image_name) {
		image = loadImage(image_name);
	}

	private static Image loadImage(String name) {

		File file = new File("data/images", name);
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("Error: Unable to load sprite: "
					+ file.getAbsolutePath());
			System.out.println(e);
			System.exit(1);

			return null;
		}
	}

	@Override
	public void renderTo(Graphics2D g, Point point) {
		g.translate(point.getX(), point.getY());
		g.drawImage(image, g.getTransform(), null);
	}
}
