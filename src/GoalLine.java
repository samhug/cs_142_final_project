import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import engine.GameObject;


public class GoalLine extends GameObject {

	private static final Color INITIAL_COLOR = Color.GREEN;
	private static final Color LOSING_COLOR  = Color.RED;
	
	private static final float WIDTH = 0.05f;
	
	private final Line2D line;
	private final int maxScore;
	
	public final int playerNum;
	
	private int score = 0;
	private Color color = INITIAL_COLOR;
	
	public GoalLine(int playerNum, int maxScore, Point2D pointA, Point2D pointB) {
		this.playerNum = playerNum;
		this.maxScore = maxScore;
		line = new Line2D.Double(pointA, pointB);
	}
	
	protected void onRender(Graphics2D g) {
		g.setStroke(new BasicStroke(WIDTH));
		g.setColor(color);
		g.draw(line);
	}
	
	public Shape getShape() {
		return line;
	}
	
	public void incrementScore() {
		score++;
	}
	
	public void updateColor() {
		color = getColorMix(INITIAL_COLOR, LOSING_COLOR, (double)(score+1)/maxScore);
	}
	
	public int getScore() {
		return score;
	}
	
	private static Color getColorMix(Color start, Color end, double mixRatio) {
		return new Color(
				getIntMix(start.getRed(),   end.getRed(),   mixRatio),
				getIntMix(start.getGreen(), end.getGreen(), mixRatio),
				getIntMix(start.getBlue(),  end.getBlue(),  mixRatio)
			);
	}
	
	private static int getIntMix(int start, int end, double mixRatio) {
		int a =  (int)Math.round(start + (end - start)*mixRatio);
		return a;
	}
}
