import java.io.IOException;
import java.awt.Point;


public class Paddle extends GameObject {
    
    public Paddle(Point initial_position) {
        
        // Call super constructor
        super();

        position = initial_position;

        try {
            setSprite(new Sprite("paddle.png"));
        } catch (IOException e) {
            System.out.println("Error: Unable to load paddle sprite.");
            System.exit(1);
        }
    }
    
    public void moveUp(int amount) {
        position.y -= amount;
        onUpdateSignal.signal();
    }
    
    public void moveDown(int amount) {
        position.y += amount;
        onUpdateSignal.signal();
    }
}
