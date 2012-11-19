import java.util.ArrayList;
import java.awt.Graphics;

public class Engine implements Window.Painter {
	
	final int DEFAULT_WINDOW_WIDTH  = 800;
	final int DEFAULT_WINDOW_HEIGHT = 600;
	
	protected Window window;
	private ArrayList<GameObject> objects;
	
	public Engine(String name) {
		
		// Initialize the game window
		window = new Window(name, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT, this);
		
		// Initialize game object list
		objects = new ArrayList<GameObject>();
	}
    
    public void on_paint(Graphics g) {
		for (GameObject obj: objects) {
			obj.render(g);
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

        // Subscribe to the objects update signal
        object.onUpdateSignal.add_hook(new Signal.Hook() {
            public void on_signal() {
                window.repaint();
            }
        });

		return object;
	}
}
