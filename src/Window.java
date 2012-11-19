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
        
        // Add the drawing panel to the window
        mainPanel = new WindowPanel(painter, width, height);
        mainPanel.setBackground(new Color(0, 0, 0, 255));
        
        // Make our window visible
        setVisible(true);
        
        // When the window exists so does the program
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        keyboard = new Keyboard(this);
        
        this.add(mainPanel);

        pack();
    }
    
    /**
     * This is the main panel for the window where everything gets drawn.
     */
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
        
        /**
         * These variables hold all the registered event hooks.
         */
        private HashMap<Integer, ArrayList<KeyEventInterface>> keyTypeHooks;
        private HashMap<Integer, ArrayList<KeyEventInterface>> keyPressHooks;
        private HashMap<Integer, ArrayList<KeyEventInterface>> keyReleaseHooks;
        
        /**
         * Initializes the hook arrays and registers our callbacks with the given
         * window's keyboard events.
         *
         * @param window The window to listen for keyboard events in.
         */
        public Keyboard(JFrame window) {
            
            keyTypeHooks    = new HashMap<Integer, ArrayList<KeyEventInterface>>();
            keyPressHooks   = new HashMap<Integer, ArrayList<KeyEventInterface>>();
            keyReleaseHooks = new HashMap<Integer, ArrayList<KeyEventInterface>>();
            
            /**
             * Initialize the keyboard listener and register our event callbacks.
             */
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
                
                /**
                 * Given a list of event hooks and a key event this method iterates
                 * through the hooks calling its `on_key` callback with the key event
                 * as a parameter.
                 *
                 * @param hookMap   The hooks to execute
                 * @param e         The keyboard event
                 */
                private void executeHooks(HashMap<Integer, ArrayList<KeyEventInterface>> hookMap, KeyEvent e) {
                    if (hookMap.containsKey(e.getKeyCode())) {
                        
                        for (KeyEventInterface hook: hookMap.get(e.getKeyCode())) {
                            hook.on_key(e);
                        }
                    }
                }
            };
            
            // Adds our listener to the window
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
    
    /**
     * This iterface is used by objects interested in a keyboard event.
     */
    public interface KeyEventInterface {
        public void on_key(KeyEvent e);
    }
    
    public interface Painter {
        public void on_paint(Graphics g);
    }
}
