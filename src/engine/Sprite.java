package engine;

import java.awt.Graphics2D;


public interface Sprite {
	
	/**
	 * Renders the sprite to the given graphics context.
	 * 
	 * @param g The graphics context to render to.
	 */
	public void renderTo(Graphics2D g);
}