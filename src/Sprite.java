import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Sprite {
    
    Image image;
    
    public Sprite(String image_name) throws IOException {
        image = loadImage(image_name);
    }
    
    public Image getImage() {
        return image;
    }
    
    private static Image loadImage(String name) throws IOException {
        File file = new File("data/images", name);
        return ImageIO.read(file);
        //return Toolkit.getDefaultToolkit().createImage("/home/samuel/workspace/FinalProject/data/images/paddle.png");//file.getAbsolutePath());
    }
}
