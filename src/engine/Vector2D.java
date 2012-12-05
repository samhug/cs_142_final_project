package engine;

import java.awt.geom.Point2D;

public class Vector2D {

	public double angle;
	public double length;

	public Vector2D(double angle, double length) {
		this.angle = angle;
		this.length = length;
	}

	public Vector2D(Vector2D v) {
		this.angle = v.angle;
		this.length = v.length;
	}
	
	public void movePoint(Point2D point) {
		point.setLocation(point.getX() + length * Math.cos(angle),
				point.getY() + length * Math.sin(angle));
	}
}
