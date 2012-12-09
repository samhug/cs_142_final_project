package engine;

import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class ShapeSprite implements Sprite {

	/**
	 * Returns the shape if the given sprite.
	 */
	protected abstract Shape getShape();

	@Override
	public void renderTo(Graphics2D g) {
		final Shape shape = getShape();

		g.fill(shape);
	}
}