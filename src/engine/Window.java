package engine;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.EventObject;
import java.util.HashMap;

import engine.events.EventHandler;

public class Window extends JFrame {

	public Keyboard keyboard;

	private EventHandler eh;
	private WindowPanel mainPanel;

	private AffineTransform transform;

	private double scale;

	public Window(EventHandler eh, String title, int width, int height,
			double scale) {
		this.eh = eh;

		this.scale = scale;

		setTitle(title);

		mainPanel = new WindowPanel(this, width, height);
		mainPanel.setBackground(new Color(0, 0, 0, 255));

		//updateCordinateSystem();
		transform = new AffineTransform();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		keyboard = new Keyboard(eh, this);

		add(mainPanel);

		pack();

		setVisible(true);
	}

	private void updateCordinateSystem() {
		Dimension size = this.mainPanel.getSize();

		//System.out.println(size.width/size.height);
		
		// Modify the coordinate system
		transform = new AffineTransform();

		// Moves the origin to the center
		transform.translate(size.width / 2., size.height / 2.);

		transform.scale(size.width / scale / 2., -size.height / scale / 2.);
	}

	public Graphics2D getPanelGraphics() {
		updateCordinateSystem();
		mainPanel.graphics.setTransform(transform);
		return mainPanel.graphics;
	}

	protected class WindowPanel extends JPanel {

		private Window parent;
		public Graphics2D graphics;

		public WindowPanel(Window parent, int width, int height) {

			this.parent = parent;
			setPreferredSize(new Dimension(width, height));
		}

		public void paint(Graphics g_) {
			Graphics2D g = (Graphics2D) g_;

			super.paint(g);

			g.setTransform(transform);

			g.setColor(getBackground());

			this.graphics = g;

			eh.dispatchEvent(new Window.PaintEvent(this.parent));
		}
	}

	public static class Keyboard {

		private final EventHandler eh;
		private HashMap<Integer, Boolean> keyStates;

		public Keyboard(EventHandler eh_, Window window) {

			this.eh = eh_;
			keyStates = new HashMap<Integer, Boolean>();

			KeyListener listener = new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
					eh.dispatchEvent(new KeyTypeEvent(this, e));
				}

				@Override
				public void keyPressed(KeyEvent e) {
					keyStates.put(e.getKeyCode(), true);
					eh.dispatchEvent(new KeyPressEvent(this, e));
				}

				@Override
				public void keyReleased(KeyEvent e) {
					keyStates.put(e.getKeyCode(), false);
					eh.dispatchEvent(new KeyReleaseEvent(this, e));
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

		public static abstract class KeyboardListener implements
				engine.events.Listener {

			private int keyCode;

			public KeyboardListener(int keyCode) {
				this.keyCode = keyCode;
			}

			@Override
			public boolean handle(EventObject eventObject) {
				KeyboardEvent e = (KeyboardEvent) eventObject;
				if (e.keyEvent.getKeyCode() == keyCode)
					return handle_key(e.keyEvent);
				else
					return false;
			}

			protected abstract boolean handle_key(KeyEvent e);
		}

		public class KeyboardEvent extends engine.events.Event {

			public KeyEvent keyEvent;

			public KeyboardEvent(Object source, KeyEvent keyEvent) {
				super(source);
				this.keyEvent = keyEvent;
			}
		}

		public class KeyTypeEvent extends KeyboardEvent {

			public KeyTypeEvent(Object source, KeyEvent keyEvent) {
				super(source, keyEvent);
			}
		}

		public class KeyPressEvent extends KeyboardEvent {

			public KeyPressEvent(Object source, KeyEvent keyEvent) {
				super(source, keyEvent);
			}
		}

		public class KeyReleaseEvent extends KeyboardEvent {

			public KeyReleaseEvent(Object source, KeyEvent keyEvent) {
				super(source, keyEvent);
			}
		}

	}

	public interface KeyEventInterface {
		public void on_key(KeyEvent e);
	}

	public class PaintEvent extends engine.events.Event {

		public PaintEvent(Window source) {
			super(source);
		}

		public Graphics2D getGraphics() {
			return ((Window) this.source).getPanelGraphics();
		}
	}

}
