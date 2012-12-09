package engine;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;

import javax.swing.Timer;

import engine.events.EventHandler;

public abstract class Engine {

	/**
	 * The width of the window in pixels.
	 */
	protected final static int WINDOW_WIDTH = 800;

	/**
	 * The height of the window in pixels.
	 */
	protected final static int WINDOW_HEIGHT = 800;

	/**
	 * The scale to use when transforming the coordinate system.
	 */
	protected final static double WINDOW_SCALE = 10.;

	/**
	 * The number of times per second the game engine should update.
	 */
	protected final static int FRAME_RATE = 300;

	protected final static int EXIT_KEY = KeyEvent.VK_ESCAPE;
	protected final static int PAUSE_KEY = KeyEvent.VK_SPACE;

	public Window window;
	public CollisionEngine collisionEngine;
	public SoundEngine soundEngine;
	public EventHandler eh;

	private ArrayList<GameObject> objects;

	protected Timer gameTimer;

	// The time of the last timer tick.
	private long lastTick;

	public Engine(String name) {

		eh = new engine.events.EventHandler();

		// Initialize the game window
		window = new Window(eh, name, WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_SCALE);

		collisionEngine = new CollisionEngine(eh);
		soundEngine = new SoundEngine();

		// Initialize game object list
		objects = new ArrayList<GameObject>();
	}

	protected void initialize() {

		initTimer();

		// When the Escape key is pressed exit the game
		eh.addEventListener(Window.Keyboard.KeyPressEvent.class,
				new Window.Keyboard.KeyboardListener(EXIT_KEY) {

					@Override
					protected boolean handle_key(KeyEvent e) {
						stop();
						return false;
					}
				});

		// When the Space key is pressed pause/resume the game
		eh.addEventListener(Window.Keyboard.KeyPressEvent.class,
				new Window.Keyboard.KeyboardListener(PAUSE_KEY) {

					@Override
					protected boolean handle_key(KeyEvent e) {
						toggle();
						return false;
					}
				});

		eh.addEventListener(Engine.UpdateEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e) {
						onUpdate(((UpdateEvent) e).tick);
						return false;
					}
				});

		eh.addEventListener(Window.PaintEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e) {
						onRender(((Window.PaintEvent) e).getGraphics());
						return false;
					}
				});

		// Trigger initial window paint
		window.repaint();
	}

	private void initTimer() {
		// Number of milliseconds between frames
		int delay = 1000 / FRAME_RATE; // milliseconds

		// !!!!!! Awful hack to break the scope of the "this" keyword ....
		// !!!!!!
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
		gameTimer = new Timer(delay, timerListener);
	}

	protected abstract void onUpdate(long tick);

	protected void onRender(Graphics2D g) {

	}

	/**
	 * Starts the game
	 */
	public void start() {
		lastTick = new Date().getTime();

		gameTimer.start();
	}

	/**
	 * Toggles between the play and pause state.
	 */
	public void toggle() {
		if (gameTimer.isRunning()) {
			gameTimer.stop();
		} else {
			lastTick = new Date().getTime();
			gameTimer.start();
		}
	}

	/**
	 * Stops the game
	 */
	public void stop() {
		gameTimer.stop();
		window.setVisible(false);
		System.exit(0);
	}

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

	/**
	 * Unregisters an object with the game engine
	 * 
	 * @param object
	 *            The object to register
	 */
	protected void removeObject(GameObject object) {
		objects.remove(object);
	}

	public static class UpdateEvent extends engine.events.Event {

		final long tick;

		public UpdateEvent(Object source, long tick) {
			super(source);
			this.tick = tick;
		}
	}
}
