import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.Point;

public class FinalProject extends Engine
{

    private final Point PLAYER_1_START_POSITION = new Point(10, 10);
    private final Point PLAYER_2_START_POSITION = new Point(750, 10);

	private Paddle paddle_1;
	private Paddle paddle_2;
	//private Ball ball;
	
    public FinalProject() throws IOException {
    	
    	// Call the GameEngine constructor 
		super("Pong");
		
		// Initialize Player 1 Paddle
		paddle_1 = (Paddle)addObject(new Paddle(PLAYER_1_START_POSITION));
		
		window.keyboard.onKeyPress(KeyEvent.VK_UP, new Window.KeyEventInterface() {
			@Override
			public void on_key(KeyEvent e) {
				paddle_1.moveUp(10);
			}
		});
		
		window.keyboard.onKeyPress(KeyEvent.VK_DOWN, new Window.KeyEventInterface() {
			@Override
			public void on_key(KeyEvent e) {
				paddle_1.moveDown(10);
			}
		});


		// Initialize Player 2 Paddle
		paddle_2 = (Paddle)addObject(new Paddle(PLAYER_2_START_POSITION));
		window.keyboard.onKeyPress(KeyEvent.VK_Q, new Window.KeyEventInterface() {
			@Override
			public void on_key(KeyEvent e) {
				paddle_2.moveUp(10);
			}
		});
		
		window.keyboard.onKeyPress(KeyEvent.VK_A, new Window.KeyEventInterface() {
			@Override
			public void on_key(KeyEvent e) {
				paddle_2.moveDown(10);
			}
		});
	}

	public static void main(String[] args) throws IOException
    {
		new FinalProject();
    }
}
