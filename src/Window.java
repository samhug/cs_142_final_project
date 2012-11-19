import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Window extends JFrame
{
	
	public Keyboard keyboard;
	
	private WindowPanel mainPanel; 
	
    public Window(String title, int width, int height, Painter painter)
    {
    	
    	setTitle(title);
    	
    	mainPanel = new WindowPanel(painter, width, height);
    	mainPanel.setBackground(new Color(0, 0, 0, 255));
    	
    	setVisible(true);
    	
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	keyboard = new Keyboard(this);
    	
    	this.add(mainPanel);

        pack();
    }
    
    protected class WindowPanel extends JPanel {

        private Painter painter;

        public WindowPanel(Painter painter, int width, int height) {
            this.painter = painter;

            setPreferredSize(new Dimension(width, height));
        }

        public void paint(Graphics g)
        {
            super.paint(g);

            g.setColor(getBackground());
            g.drawRect(0, 0, getWidth(), getHeight());

            painter.on_paint(g);
        }
    }
    
	public class Keyboard {
		
		private HashMap<Integer, ArrayList<KeyEventInterface>> keyTypeHooks;
		private HashMap<Integer, ArrayList<KeyEventInterface>> keyPressHooks;
		private HashMap<Integer, ArrayList<KeyEventInterface>> keyReleaseHooks;
		
		public Keyboard(JFrame window) {
			
			keyTypeHooks    = new HashMap<Integer, ArrayList<KeyEventInterface>>();
			keyPressHooks   = new HashMap<Integer, ArrayList<KeyEventInterface>>();
			keyReleaseHooks = new HashMap<Integer, ArrayList<KeyEventInterface>>();
			
			KeyListener listener = new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					executeHooks(keyTypeHooks, e);
				}

				@Override
				public void keyPressed(KeyEvent e) {
					executeHooks(keyPressHooks, e);
				}

				@Override
				public void keyReleased(KeyEvent e) {
					e.getKeyCode();
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
	
	public interface Painter {
		public void on_paint(Graphics g);
	}
}
