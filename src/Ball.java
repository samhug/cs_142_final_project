import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.EventObject;
import java.util.Random;

import engine.Engine;
import engine.GameObject;
import engine.ShapeSprite;
import engine.Vector2D;

public class Ball extends GameObject {

	// The initial speed of the ball measured in `units/second`
	final static double INITIAL_SPEED = 4;
	final static double SPEED_INCREMENT = 1.1;

	/*
	 * The level of randomness to use in calculating collision responses. This
	 * helps minimize situations where the ball bounces in a repeating pattern.
	 */
	final static double COLLISON_NOISE_FACTOR = 0.05;

	// The size of the ball
	final static double RADIUS = 0.5;

	private final Point2D initialPosition;

	private Random random;

	public Ball(Point2D initialPosition) {
		super();

		this.initialPosition = initialPosition;
		random = new Random();

		reset();

		setSprite(new BallSprite(RADIUS));
	}

	public void register(Engine e) {
		super.register(e);

		e.eh.addEventListener(PlethoraOfPong.GameStartEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e_) {

						// Sets the ball vector to a random angle and the
						// initial speed
						vector = new Vector2D((2 * Math.PI)
								* random.nextDouble(), INITIAL_SPEED);

						return false;
					}
				});
	}

	public void reset() {
		position = (Point2D) initialPosition.clone();
		vector.length = 0;
	}

	public Shape getShape() {
		return ((BallSprite) getSprite()).getShape();
	}

	public void onCollidePaddle(Paddle paddle) {

		Shape shape = paddle.getTranslateTransform().createTransformedShape(
				paddle.getShape());

		double p_angle = paddle.getAngle();
		// System.out.println(vector.angle + ", " + p_angle);

		// The angle of line to reflect on.
		double ref_angle = p_angle + Math.PI / 2;
		// System.out.println(vector.angle + ", " + p_angle + ", " +
		// perp_angle);

		// The angle between the collision vector and the perpendicular line.
		double d_angle = (vector.angle + Math.PI) % (2 * Math.PI) - ref_angle;
		// System.out.println(vector.angle + ", " + p_angle + ", " + perp_angle
		// + ", " + d_angle);

		d_angle *= COLLISON_NOISE_FACTOR * random.nextDouble();

		// Back the circle out of the paddle
		double d = ptShapeClosest(shape, position) - RADIUS;
		if (d < 0) {
			new Vector2D(vector.angle, d).movePoint(position);
		}
		// System.out.println(d);

		vector.angle = ref_angle - d_angle;

		// System.out.println("Bump!");

		vector.length = vector.length * SPEED_INCREMENT; // speeds up ball after
															// hitting paddles
	}

	

	private double ptShapeClosest(Shape shape, Point2D point) {
		PathIterator p_it = shape.getPathIterator(null);

		double[] coords = new double[6];
		Point2D p1, p2;
		Line2D l;

		p_it.currentSegment(coords);
		p1 = new Point2D.Double(coords[0], coords[0]);
		double minD = point.distance(p1.getX(), p1.getY());
		p_it.next();

		while (!p_it.isDone()) {
			p_it.currentSegment(coords);

			p2 = new Point2D.Double(coords[0], coords[1]);

			l = new Line2D.Double(p1, p2);

			double d = l.ptSegDist(point);
			if (d < minD)
				minD = d;

			p_it.next();
			p1 = p2;
		}

		return minD;
	}

	private class BallSprite extends ShapeSprite {

		private Ellipse2D shape;

		public BallSprite(double radius) {
			shape = new Ellipse2D.Double(-radius, -radius, 2 * radius,
					2 * radius);
		}

		@Override
		public Shape getShape() {
			return shape;
		}

		@Override
		public void renderTo(Graphics2D g) {
			g.setColor(Color.CYAN);
			super.renderTo(g);
		}
	}
}
