package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;

import javax.swing.Timer;

public abstract class Engine {

	protected final static int WINDOW_WIDTH = 800;
	protected final static int WINDOW_HEIGHT = 800;

	protected final static double WINDOW_SCALE = 10.;

	protected final static int FRAME_RATE = 300; // updates/second

	public Window window;
	public CollisionEngine collisionEngine;
	
	private ArrayList<GameObject> objects;

	public engine.events.EventHandler eh;

	// The time of the last click.
	private long lastTick;

	public Engine(String name) {

		eh = new engine.events.EventHandler();
		lastTick = new Date().getTime();

		// Initialize the game window
		window = new Window(eh, name, WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_SCALE);

		collisionEngine = new CollisionEngine(eh);
		
		// Initialize game object list
		objects = new ArrayList<GameObject>();

		initTimer();

		// Call the sub-class' initialization method.
		initialize();

		// When the Escape key is pressed exit the game
		eh.addEventListener(Window.Keyboard.KeyPressEvent.class,
				new Window.Keyboard.KeyboardListener(KeyEvent.VK_ESCAPE) {

					@Override
					protected boolean handle_key(KeyEvent e) {
						window.setVisible(false);
						System.exit(0);
						return false;
					}
				});

		eh.addEventListener(Engine.UpdateEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e) {
						onUpdate(((UpdateEvent)e).tick);
						return false;
					}
				});
		
		// Trigger initial window paint
		window.repaint();
	}

	private void initTimer() {
		// Number of milliseconds between frames
		int delay = 1000 / FRAME_RATE; // milliseconds

		// !!!!!! Awful hack to break the scope of the "this" keyword .... !!!!!!
		final Engine this_ = this;

		ActionListener timerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				final long currentTime = new Date().getTime();

				final long tick = currentTime - lastTick;

				lastTick = currentTime;

				eh.dispatchEvent(new UpdateEvent(this_, tick));
			}
		};
		new Timer(delay, timerListener).start();
	}

	/**
	 * Holds initialization for objects.
	 */
	protected abstract void initialize();

	protected abstract void onUpdate(long tick);
	
	/**
	 * Registers an object with the game engine
	 * 
	 * @param object
	 *            The object to register
	 * @return The registered object
	 */
	protected GameObject addObject(GameObject object) {
		objects.add(object);

		object.register(this);

		return object;
	}

	public static class UpdateEvent extends engine.events.Event {

		final long tick;

		public UpdateEvent(Object source, long tick) {
			super(source);
			this.tick = tick;
		}
	}
}
