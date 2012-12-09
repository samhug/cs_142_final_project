package engine;

import java.awt.geom.Point2D;

public class Vector2D {

	/**
	 * The angle of the vector in radians.
	 */
	public double angle;
	
	/**
	 * The length of the vector.
	 */
	public double length;

	
	/**
	 * Constructs a vector
	 * 
	 * @param angle
	 *            The angle of the vector in radians.
	 * @param length
	 *            The length of the vector.
	 */
	public Vector2D(double angle, double length) {
		this.angle = angle;
		this.length = length;
	}

	/**
	 * Copies the given vector.
	 * 
	 * @param v
	 *            The vector to copy
	 */
	public Vector2D(Vector2D v) {
		this.angle = v.angle;
		this.length = v.length;
	}

	/**
	 * Moves the given point by a distance of `length` in the direction of
	 * `angle`.
	 * 
	 * @param point
	 *            The point to move
	 */
	public void movePoint(Point2D point) {
		point.setLocation(point.getX() + length * Math.cos(angle), point.getY()
				+ length * Math.sin(angle));
	}
}
