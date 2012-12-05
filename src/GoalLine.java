import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import engine.GameObject;


public class GoalLine extends GameObject {

	private static final Color COLOR = Color.GREEN;
	private static final float WIDTH = 0.005f;
	
	private final Line2D line;
	
	public GoalLine(Point2D pointA, Point2D pointB) {
		line = new Line2D.Double(pointA, pointB);
	}
	
	protected void onRender(Graphics2D g) {
		g.setStroke(new BasicStroke(WIDTH));
		g.setColor(COLOR);
		g.draw(line);
	}
}
