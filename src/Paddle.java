import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import engine.Engine;
import engine.GameObject;
import engine.ShapeSprite;
import engine.Vector2D;

public class Paddle extends GameObject {

	private final static Color PADDLE_COLOR = Color.BLUE;

	private final static double INITIAL_VELOCITY = 3;

	// The size of the game paddle
	final static double WIDTH = 2.5;
	final static double THICKNESS = 0.3;

	// The keys used to control the paddle
	private final int keyCodeA;
	private final int keyCodeB;

	// Boundaries for the paddle
	private final Point2D boundaryA;
	private final Point2D boundaryB;

	private final double trajectoryA;
	private final double trajectoryB;
	
	private AffineTransform rotateTransform;

	public Paddle(Point2D boundaryA, Point2D boundaryB, int keyCodeA, int keyCodeB) {
		super();

		this.keyCodeA = keyCodeA;
		this.keyCodeB = keyCodeB;

		this.boundaryA = boundaryA;
		this.boundaryB = boundaryB;

		// Calculate the angle for the paddle to travel on in each direction.
		this.trajectoryA = Math.atan2(this.boundaryA.getY() - this.boundaryB.getY(),
				this.boundaryA.getX() - this.boundaryB.getX());
		this.trajectoryB = (this.trajectoryA + Math.PI) % (2 * Math.PI);

		// The paddle's initial position is half-way between the two boundary
		// points.
		position = new Point2D.Double((this.boundaryA.getX() + this.boundaryB.getX()) / 2.,
				(this.boundaryA.getY() + this.boundaryB.getY()) / 2.);

		// Create a transform to rotate the paddle to be parallel with the goal line.
		rotateTransform = AffineTransform.getRotateInstance(trajectoryA - Math.PI / 2);
		
		// setSprite(new ImageSprite("paddle.png"));
		setSprite(new PaddleSprite(THICKNESS, WIDTH));
	}

	protected void onUpdate(Engine e, long tick) {

		if (e.window.keyboard.isKeyPressed(keyCodeA)) {
			vector.angle = trajectoryA;
			vector.length = INITIAL_VELOCITY;
		}
		else if (e.window.keyboard.isKeyPressed(keyCodeB)) {
			vector.angle = trajectoryB;
			vector.length = INITIAL_VELOCITY;
		} else {
			vector.angle = 0;
			vector.length = 0;
		}

		// Make sure the paddle dosn't go outside the boundaries.
		if (position.distance(boundaryA) < WIDTH / 2.) {
			position = (Point2D) boundaryA.clone();
			new Vector2D(trajectoryB, WIDTH / 2.).movePoint(position);
		}
		if (position.distance(boundaryB) < WIDTH / 2.) {
			position = (Point2D) boundaryB.clone();
			new Vector2D(trajectoryA, WIDTH / 2.).movePoint(position);
		}

		super.onUpdate(e, tick);
	}
	
	public Shape getShape() {
		return ((PaddleSprite)getSprite()).getShape();
	}
	
	public double getAngle() {
		return trajectoryA;
	}

	private class PaddleSprite extends ShapeSprite {

		private Shape shape;
		
		public PaddleSprite(double width, double height) {
			shape = new Rectangle2D.Double(-width/2, -height/2, width, height);
			shape = rotateTransform.createTransformedShape(shape);
		}

		@Override
		public Shape getShape() {
			return shape;
		}

		@Override
		public void renderTo(Graphics2D g) {
			g.setColor(PADDLE_COLOR);
			super.renderTo(g);
		}
	}
}
