package engine;

import java.awt.Graphics2D;

import engine.GameObject.Point;

public interface Sprite {

	public void renderTo(Graphics2D g, Point point);

}
