package engine;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.EventObject;

import engine.Engine.UpdateEvent;

public abstract class GameObject {

	protected Point2D position;
	protected Vector2D vector;

	private Sprite sprite;
	private Shape shape;
	
	public GameObject() {
		position = new Point2D.Double(0, 0);
		vector = new Vector2D(0, 0);
	}

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
	
	private void renderTo(Graphics2D g) {
		g.translate(position.getX(), position.getY());
		
		onRender(g);
	}

	protected void onRender(Graphics2D g) {
		sprite.renderTo(g);
	}

	protected void onUpdate(Engine e, long tick) {

		Vector2D updateVector = new Vector2D(vector);

		updateVector.length *= tick / 1000.;

		// Update the object's position
		updateVector.movePoint(position);

		e.window.repaint();
	}
	
	public AffineTransform getTranslateTransform() {
		return AffineTransform.getTranslateInstance(position.getX(), position.getY());
	}

	public Sprite getSprite() {
		return sprite;
	}

	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Shape getShape() {
		return shape;
	}

	protected void setShape(Shape shape) {
		this.shape = shape;
	}
	
}
