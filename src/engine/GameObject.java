package engine;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.EventObject;

import engine.Engine.UpdateEvent;

public abstract class GameObject {

	/**
	 * The position of the object in the game world.
	 */
	protected Point2D position;

	/**
	 * The direction and speed of the object.
	 */
	protected Vector2D vector;

	/**
	 * The sprite used for rendering the object on the screen.
	 */
	private Sprite sprite;

	/**
	 * The shape of the object. (Used for collision detection)
	 */
	private Shape shape;

	public GameObject() {
		position = new Point2D.Double(0, 0);
		vector = new Vector2D(0, 0);
	}

	/**
	 * Registers the object with the game engine.
	 * 
	 * @param e
	 *            The game engine.
	 */
	public void register(Engine e) {

		e.eh.addEventListener(Engine.UpdateEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e_) {
						final UpdateEvent e = (UpdateEvent) e_;

						onUpdate(((Engine) e.getSource()), e.tick);
						return false;
					}
				});

		e.eh.addEventListener(Window.PaintEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e) {
						renderTo(((Window.PaintEvent) e).getGraphics());
						return false;
					}
				});
	}

	/**
	 * Translates the coordinate space to this objects position and calls
	 * onRender.
	 * 
	 * @param g
	 *            The graphics context to render to.
	 */
	private void renderTo(Graphics2D g) {
		g.translate(position.getX(), position.getY());

		onRender(g);
	}

	/**
	 * Renders the objects sprite to the given graphics context.
	 * 
	 * @param g
	 *            The graphics context to render to.
	 */
	protected void onRender(Graphics2D g) {
		sprite.renderTo(g);
	}

	/**
	 * This method is called by the game engine every 1/FRAME_RATE seconds.
	 * 
	 * @param e
	 *            The game engine.
	 * @param tick
	 *            The number of seconds since the last update.
	 */
	protected void onUpdate(Engine e, long tick) {

		Vector2D updateVector = new Vector2D(vector);

		updateVector.length *= tick / 1000.;

		// Update the object's position
		updateVector.movePoint(position);

		e.window.repaint();
	}

	/**
	 * Returns a transform object to translate a coordinate space to this
	 * objects position.
	 * 
	 * @return The transform object.
	 */
	public AffineTransform getTranslateTransform() {
		return AffineTransform.getTranslateInstance(position.getX(),
				position.getY());
	}

	/**
	 * Returns this objects sprite.
	 * 
	 * @return A sprite object.
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Sets this objects sprite.
	 * 
	 * @param sprite The sprite to use.
	 */
	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	/**
	 * Returns the shape if this object.
	 * 
	 * @return A shape
	 */
	public Shape getShape() {
		return shape;
	}

	/**
	 * Sets the shape of this object.
	 * 
	 * @param shape The shape to use
	 */
	protected void setShape(Shape shape) {
		this.shape = shape;
	}

}
