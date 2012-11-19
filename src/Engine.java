import java.util.ArrayList;
import java.awt.Graphics;

/**
 * This is the game engine base class.
 */
public class Engine implements Window.Painter {
	
    // Default game window dimensions
	final int DEFAULT_WINDOW_WIDTH  = 800;
	final int DEFAULT_WINDOW_HEIGHT = 600;
	
	protected Window window;

    // Holds objects registered with the engine
	private ArrayList<GameObject> objects;
	

    /**
     * Initialize the game window and the objects array
     *
     * @param name The title of our game.
     */
	public Engine(String name) {
		
		// Initialize the game window
		window = new Window(name, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, this);
		
		// Initialize game object list
		objects = new ArrayList<GameObject>();
	}
    
    /**
     * This method is called when the window needs to repaint
     * the objects.
     *
     * @param graphics The graphics context to use.
     */
    public void on_paint(Graphics graphics) {

        /**
         * Iterate through all the registered objects and render each of them
         * to the graphics context.
         */
		for (GameObject obj: objects) {
			obj.render(graphics);
		}
    }

	/**
	 * Registers an object with the game engine
	 * 
	 * @param object The object to register
	 * @return The registered object
	 */
	protected GameObject addObject(GameObject object) {
		objects.add(object);

        /**
         * Subscribe to the objects update signal and when the object updates,
         * trigger a window repaint.
         *
         * TODO: Maybe we could repaint only the area effected by the update.
         */
        object.onUpdateSignal.add_hook(new Signal.Hook() {
            public void on_signal() {
                window.repaint();
            }
        });

		return object;
	}
}
