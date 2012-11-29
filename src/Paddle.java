import java.awt.event.KeyEvent;


public class Paddle extends GameObject {
	
	private static double ANGLE_A  = 3*Math.PI/2; // 90° (Up)
	private static double ANGLE_B  = Math.PI/2;   // 270° (Down)
	private static double INITIAL_VELOCITY = 2;
	
	private int keyCodeA;
	private int keyCodeB;
	
	public Paddle(Point initial_position, int keyCodeA, int keyCodeB) {
		// Call super constructor
		super();
				
		this.keyCodeA = keyCodeA;
		this.keyCodeB = keyCodeB;

        position = (Point)initial_position.clone();
        vector = new Vector(0, 0);

		setSprite(new Sprite("paddle.png"));
	}
	
	/*
	public void register(Engine e) {
		super.register(e);
		
		// Initialize Keyboard Event Handlers
		e.window.keyboard.onKeyPress(keyCodeA, new Window.KeyEventInterface() {
			@Override
			public void on_key(KeyEvent e) {
				moveUp(10);
				position.move(vector);
				
			}
		});
		
		e.window.keyboard.onKeyPress(keyCodeB, new Window.KeyEventInterface() {
			@Override
			public void on_key(KeyEvent e) {
				moveDown(10);
			}
		});
	}
	*/
	
	
	protected void onUpdate(Engine e) {
        
		if (e.window.keyboard.isKeyPressed(keyCodeA)) {
			vector.angle = ANGLE_A;
			vector.magnitude = INITIAL_VELOCITY;
		}
		if (e.window.keyboard.isKeyPressed(keyCodeB)) {
			vector.angle = ANGLE_B;
			vector.magnitude = INITIAL_VELOCITY;
		}
		
        // Update the object's position
		this.position.move(vector);
        
        e.window.invalidate();
    }
	
	
	public void moveUp(int amount) {
		position.y -= amount;
	}
	
	public void moveDown(int amount) {
		position.y += amount;
	}
	
}
