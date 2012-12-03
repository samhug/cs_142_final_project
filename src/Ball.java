import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import engine.GameObject;
import engine.ShapeSprite;

public class Ball extends GameObject {

	// The initial speed of the ball measured in `units/second`
	final static double INITIAL_SPEED = 1.5;

	// The size of the ball
	final static double WIDTH = 0.5;
	final static double HEIGHT = 0.5;

	public Ball(Point initial_position) {
		super();

		position = (Point) initial_position.clone();

		// Pick a random angle for the initial vector
		final double theta = (2 * Math.PI) / new Random().nextDouble();
		vector = new Vector(theta, INITIAL_SPEED);

		setSprite(new BallSprite(WIDTH, HEIGHT));
	}

	private class BallSprite extends ShapeSprite {

		private Ellipse2D shape;

		public BallSprite(double width, double height) {
			shape = new Ellipse2D.Double(0, 0, width, height);
		}

		@Override
		public Shape getShape() {
			return shape;
		}

		public void onRender(Graphics2D g) {
			g.translate(-shape.getWidth() / 2, -shape.getHeight() / 2);
			g.setColor(Color.CYAN);
			g.fill(getShape());
		}
	}
}
