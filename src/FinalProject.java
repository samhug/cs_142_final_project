import java.awt.event.KeyEvent;

public class FinalProject extends Engine
{

	private static final GameObject.Point PLAYER_1_START_POSITION = new GameObject.Point(10, 10);
    private static final GameObject.Point PLAYER_2_START_POSITION = new GameObject.Point(750, 10);

	private Paddle paddle_1;
	private Paddle paddle_2;
	//private Ball ball;
	
	/**
	 * Constructor
	 */
    public FinalProject() {
		super("Pong");
	}
	
    
    public void initialize() {
    	
		// Initialize Player paddles
		paddle_1 = new Paddle(PLAYER_1_START_POSITION, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		paddle_2 = new Paddle(PLAYER_2_START_POSITION, KeyEvent.VK_Q,  KeyEvent.VK_A);

		// Register the paddles with the game engine
		addObject(paddle_1);
		addObject(paddle_2);
	}

	public static void main(String[] args) {
		new FinalProject();
    }
}
