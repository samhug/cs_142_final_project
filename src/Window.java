import javax.swing.*;

import events.EventHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Window
{
	
	public Keyboard keyboard;
	
	private Engine engine;
	private JFrame window;
	private WindowPanel mainPanel; 
	
    public Window(Engine engine, String title, int width, int height)
    {
    	super();
    	this.engine = engine;
    	
    	window = new JFrame();
    	
    	window.setTitle(title);
    	
    	mainPanel = new WindowPanel(this, width, height);
    	mainPanel.setBackground(new Color(0, 0, 0, 255));
    	
    	window.setVisible(true);
    	
    	window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	keyboard = new Keyboard(window);
    	
    	window.add(mainPanel);

    	window.pack();
    }
    
    public Graphics getPanelGraphics() {
    	return mainPanel.graphics;
    }
    
    /**
     * Trigger a re-rendering of the window.
     */
    public void invalidate() {
    	window.repaint();
    }
    
    protected class WindowPanel extends JPanel {

    	private Window parent;
    	public Graphics graphics;
    	
        public WindowPanel(Window parent, int width, int height) {

        	this.parent = parent;
            setPreferredSize(new Dimension(width, height));
        }

        public void paint(Graphics g)
        {
            super.paint(g);

            g.setColor(getBackground());
            g.drawRect(0, 0, getWidth(), getHeight());

            this.graphics = g;
            
            engine.dispatchEvent(new Window.PaintEvent(this.parent));
        }
    }
    
	public class Keyboard {
		
		private HashMap<Integer, ArrayList<KeyEventInterface>> keyTypeHooks;
		private HashMap<Integer, ArrayList<KeyEventInterface>> keyPressHooks;
		private HashMap<Integer, ArrayList<KeyEventInterface>> keyReleaseHooks;
		
		private HashMap<Integer, Boolean> keyStates;
		
		public Keyboard(JFrame window) {
			
			keyTypeHooks    = new HashMap<Integer, ArrayList<KeyEventInterface>>();
			keyPressHooks   = new HashMap<Integer, ArrayList<KeyEventInterface>>();
			keyReleaseHooks = new HashMap<Integer, ArrayList<KeyEventInterface>>();
			
			keyStates		= new HashMap<Integer, Boolean>();
			
			KeyListener listener = new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					executeHooks(keyTypeHooks, e);
				}

				@Override
				public void keyPressed(KeyEvent e) {
					keyStates.put(e.getKeyCode(), true);
					
					executeHooks(keyPressHooks, e);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					keyStates.put(e.getKeyCode(), false);
					
					executeHooks(keyReleaseHooks, e);
				}
				
				private void executeHooks(HashMap<Integer, ArrayList<KeyEventInterface>> hookMap, KeyEvent e) {
					if (hookMap.containsKey(e.getKeyCode())) {
						
						for (KeyEventInterface hook: hookMap.get(e.getKeyCode())) {
							hook.on_key(e);
						}
					}
				}
			};
			
			window.addKeyListener(listener);
		}
		
		public boolean isKeyPressed(Integer key) {
			Boolean state;
			if ((state = keyStates.get(key)) != null) {
				return state;
			}
			
			return false;
		}
		
		public void onKeyType(Integer key, KeyEventInterface callback) {
			addHook(keyTypeHooks, key, callback);
		}
		
		public void onKeyPress(Integer key, KeyEventInterface callback) {
			addHook(keyPressHooks, key, callback);
		}
		
		public void onKeyReleased(Integer key, KeyEventInterface callback) {
			addHook(keyReleaseHooks, key, callback);
		}
		
		private void addHook(HashMap<Integer, ArrayList<KeyEventInterface>> hookMap, Integer key, KeyEventInterface callback) {
			if (!hookMap.containsKey(key)) {
				hookMap.put(key, new ArrayList<KeyEventInterface>());
			}
			
			hookMap.get(key).add(callback);
		}
		
	}
	
	public interface KeyEventInterface {
		public void on_key(KeyEvent e);
	}
	
	public class PaintEvent extends events.Event {

		public PaintEvent(Window source) {
			super(source);
		}
		
		public Graphics getGraphics() {
			return ((Window)this.source).getPanelGraphics();
		}
	}
	
}
