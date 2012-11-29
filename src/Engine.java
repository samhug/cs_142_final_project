import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.Timer;

public abstract class Engine extends events.EventHandler {
	
	final static int DEFAULT_WINDOW_WIDTH  = 800;
	final static int DEFAULT_WINDOW_HEIGHT = 600;
	final static int DEFAULT_FRAME_RATE	   = 60; // N_UPDATES / Second
	
	protected Window window;
	private ArrayList<GameObject> objects;
	
	public Engine(String name) {
		
		// Initialize the game window
		window = new Window(this, name, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		
		// Initialize game object list
		objects = new ArrayList<GameObject>();
		
		initTimer();

		// Call the sub-class' initialization method.
		initialize();
		
		// Trigger initial window paint
		window.invalidate();
	}
	
	private void initTimer() {
		// Number of milliseconds between frames
		int delay = 1000 / DEFAULT_FRAME_RATE; //milliseconds
		
		
		// !!!!!! Awful hack to break the scope of the "this" keyword .... !!!!!!
		final Engine _engine = this;
		
		ActionListener timerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispatchEvent(new UpdateEvent(_engine));
			}
		};
		new Timer(delay, timerListener).start();
	}
	
	/**
	 * Holds initialization for objects.
	 */
	protected abstract void initialize();
    
	/**
	 * Registers an object with the game engine
	 * 
	 * @param object The object to register
	 * @return The registered object
	 */
	protected GameObject addObject(GameObject object) {
		objects.add(object);

		object.register(this);
		
		return object;
	}
	
    public static class UpdateEvent extends events.Event {
		public UpdateEvent(Object source) { super(source); }
    }
}
