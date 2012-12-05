import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

import engine.GameObject;
import engine.ShapeSprite;
import engine.Vector2D;

public class Ball extends GameObject {

	// The initial speed of the ball measured in `units/second`
	final static double INITIAL_SPEED = 4;

	// The size of the ball
	final static double WIDTH = 0.5;
	final static double HEIGHT = 0.5;

	public Ball(Point2D initial_position) {
		super();

		position = (Point2D) initial_position.clone();

		// Pick a random angle for the initial vector
		final double theta = (2 * Math.PI) / new Random().nextDouble();
		vector = new Vector2D(theta, INITIAL_SPEED);

		setSprite(new BallSprite(WIDTH, HEIGHT));
	}
	
	public void stop() {
		vector.length = 0;
	}
	
	public Shape getShape() {
		return ((BallSprite)getSprite()).getShape();
	}
	
	public void onCollidePaddle(Paddle paddle) {
		vector.angle += (vector.angle - paddle.getAngle() - (Math.PI/2));
		
		//vector.angle += Math.PI;
		vector.angle %= 2*Math.PI;
		//vector.movePoint(position);
		
		System.out.println("Bump!");
	}
	
	public void onCollideGoal(GoalLine goal) {
		//vector.angle += (vector.angle - paddle.getAngle() - (Math.PI/2));
		
		//vector.angle += Math.PI;
		//vector.angle %= 2*Math.PI;
		//vector.movePoint(position);
		
		System.out.println("Oops!");
	}

	private class BallSprite extends ShapeSprite {

		private Ellipse2D shape;

		public BallSprite(double width, double height) {
			shape = new Ellipse2D.Double(-width/2, -height/2, width, height);
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
