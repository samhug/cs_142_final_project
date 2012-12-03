import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import engine.Engine;
import engine.GameObject;
import engine.ShapeSprite;

public class Paddle extends GameObject {

	private final static Color PADDLE_COLOR = Color.BLUE;
	private final static Color GOAL_LINE_COLOR = Color.GREEN;

	private final static double INITIAL_VELOCITY = 2;

	// The size of the game paddle
	final static double WIDTH = 2.5;
	final static double THICKNESS = 0.3;

	// The keys used to control the paddle
	private final int keyCodeA;
	private final int keyCodeB;

	// Boundaries for the paddle
	private final Point boundaryA;
	private final Point boundaryB;

	private final Line2D goalLine;

	private final double trajectoryA;
	private final double trajectoryB;

	public Paddle(Point boundaryA, Point boundaryB, int keyCodeA, int keyCodeB) {
		super();

		this.keyCodeA = keyCodeA;
		this.keyCodeB = keyCodeB;

		this.boundaryA = boundaryA;
		this.boundaryB = boundaryB;

		this.goalLine = new Line2D.Double(this.boundaryA, this.boundaryB);

		// Calculate the angle for the paddle to travel on in each direction.
		this.trajectoryA = Math.atan2(this.boundaryA.y - this.boundaryB.y,
				this.boundaryA.x - this.boundaryB.x);
		this.trajectoryB = (this.trajectoryA + Math.PI) % (2 * Math.PI);

		// The paddle's initial position is half-way between the two boundary
		// points.
		position = new Point((this.boundaryA.x + this.boundaryB.x) / 2.,
				(this.boundaryA.y + this.boundaryB.y) / 2.);

		// setSprite(new ImageSprite("paddle.png"));
		setSprite(new PaddleSprite(THICKNESS, WIDTH));
	}

	protected void onRender(Graphics2D g) {

		// Draw the goal line
		g.setStroke(new BasicStroke(0.005f));
		g.setColor(GOAL_LINE_COLOR);
		g.draw(goalLine);

		super.onRender(g);
	}

	protected void onUpdate(Engine e, long tick) {

		if (e.window.keyboard.isKeyPressed(keyCodeA)) {
			vector.angle = trajectoryA;
			vector.magnitude = INITIAL_VELOCITY;
		}
		if (e.window.keyboard.isKeyPressed(keyCodeB)) {
			vector.angle = trajectoryB;
			vector.magnitude = INITIAL_VELOCITY;
		}

		// Make sure the paddle dosn't go outside the boundaries.
		if (position.distance(boundaryA) < WIDTH / 2.) {
			position = new Point(boundaryA).move(new Vector(trajectoryB,
					WIDTH / 2.));
		}
		if (position.distance(boundaryB) < WIDTH / 2.) {
			position = new Point(boundaryB).move(new Vector(trajectoryA,
					WIDTH / 2.));
		}

		super.onUpdate(e, tick);
	}

	private class PaddleSprite extends ShapeSprite {

		private Rectangle2D shape;

		public PaddleSprite(double width, double height) {
			shape = new Rectangle2D.Double(0, 0, width, height);
		}

		@Override
		public Shape getShape() {
			return shape;
		}

		public void onRender(Graphics2D g) {

			// Rotate paddle to be parallel with the goal line
			g.rotate(trajectoryA - Math.PI / 2);

			g.translate(-shape.getWidth() / 2, -shape.getHeight() / 2);

			g.setColor(PADDLE_COLOR);
			g.fill(getShape());
		}
	}

}
