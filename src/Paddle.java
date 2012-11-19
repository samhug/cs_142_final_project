import java.io.IOException;
import java.awt.Point;


public class Paddle extends GameObject {
	
	public Paddle(Point initial_position) throws IOException {
		
		// Call super constructor
		super();

        position = initial_position;

		setSprite(new Sprite("paddle.png"));
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
