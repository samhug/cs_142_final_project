package engine;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public abstract class PolySprite implements Sprite {
	
	protected abstract Shape getShape();

	@Override
	public void renderTo(Graphics2D g) {
		final Shape shape = getShape();
		
		final Rectangle2D bound = shape.getBounds2D();
		
		g.translate(-bound.getCenterX(), -bound.getCenterY());
		g.fill(shape);
	}
}
