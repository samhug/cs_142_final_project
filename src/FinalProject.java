import java.awt.event.KeyEvent;
import java.io.IOException;
import java.awt.Point;

/**
 * TCC CS 141 Final Project
 */
public class FinalProject extends Engine
{

    // The initial positions for the paddles
    private final Point PLAYER_1_START_POSITION = new Point(10, 10);
    private final Point PLAYER_2_START_POSITION = new Point(750, 10);

	private Paddle paddle_1;
	private Paddle paddle_2;
	//private Ball ball;
	
    public FinalProject() throws IOException {
    	
    	// Call the engine's super constructor 
		super("Pong");
		
		// Initialize player1's paddle
		paddle_1 = (Paddle)addObject(new Paddle(PLAYER_1_START_POSITION));
		
        // Setup player1's keyboard controls
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


		// Initialize player2's paddle
		paddle_2 = (Paddle)addObject(new Paddle(PLAYER_2_START_POSITION));

        // Setup player2's keyboard controls
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
