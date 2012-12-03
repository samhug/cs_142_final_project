package engine;

import java.awt.Graphics2D;
import java.awt.Shape;

import engine.GameObject.Point;

public abstract class ShapeSprite implements Sprite {

	public abstract Shape getShape();

	protected abstract void onRender(Graphics2D g);

	@Override
	public void renderTo(Graphics2D g, Point point) {
		g.translate(point.getX(), point.getY());
		onRender(g);
	}
}
