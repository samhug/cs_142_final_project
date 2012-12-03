package engine;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.EventObject;

import engine.Engine.UpdateEvent;

public abstract class GameObject {

	protected Point position;
	protected Vector vector;

	private Sprite sprite;

	public GameObject() {
		position = new Point(0, 0);
		vector = new Vector(0, 0);
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
						onRender(((Window.PaintEvent) e).getGraphics());
						return false;
					}
				});
	}

	protected void onRender(Graphics2D g) {
		sprite.renderTo(g, position);
	}

	protected void onUpdate(Engine e, long tick) {

		Vector updateVector = new Vector(vector);

		updateVector.magnitude *= tick / 1000.;

		// Update the object's position
		this.position.move(updateVector);

		e.window.repaint();
	}

	protected Sprite getSprite() {
		return sprite;
	}

	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public class Vector {

		public double angle;
		public double magnitude;

		public Vector(double angle, double magnitude) {
			this.angle = angle;
			this.magnitude = magnitude;
		}

		public Vector(Vector v) {
			this.angle = v.angle;
			this.magnitude = v.magnitude;
		}

	}

	public static class Point extends Point2D.Double {

		public Point(double x, double y) {
			super(x, y);
		}

		public Point(Point p) {
			super(p.x, p.y);
		}

		public Point move(Vector v) {
			x += v.magnitude * Math.cos(v.angle);
			y += v.magnitude * Math.sin(v.angle);
			return this;
		}
	}
}
