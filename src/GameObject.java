import java.awt.Graphics;
import java.util.EventObject;

public abstract class GameObject {
	
	protected Point position;
	protected Vector vector;

	private Sprite sprite;
	
	public GameObject() {
		position = new Point(0, 0);
		vector = new Vector(0, 0);
	}
	
	public void register(Engine e) {
		
		e.addEventListener(Engine.UpdateEvent.class, new events.Listener() {

			@Override
			public boolean handle(EventObject e) {
				onUpdate(((Engine)e.getSource()));
				return false;
			}}
		);
		
		e.addEventListener(Window.PaintEvent.class, new events.Listener() {

			@Override
			public boolean handle(EventObject e) {
				onRender(((Window.PaintEvent)e).getGraphics());
				return false;
			}}
		);
		
		
	}
	
    protected void onRender(Graphics g) {
    	
        // Round the decimal cordinates to integer window cordinates
        int x = (int) Math.round(position.getX());
        int y = (int) Math.round(position.getY());
        
		g.drawImage(sprite.getImage(), x, y, null);
    }

    protected void onUpdate(Engine e) {
        
        // Update the object's position
        this.position.move(vector);
        
        e.window.invalidate();
    }
	
	protected Sprite getSprite() {
		return sprite;
	}
	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Mark object for re-rendering
	 */
	/*protected void invalidate() {
		this.dispatchEvent(new UpdateEvent(this));
	}*/

    public class Vector {

        public double angle;
        public double magnitude;

        public Vector(double angle, double magnitude) {
            this.angle = angle;
            this.magnitude = magnitude;
        }

    }

    public static class Point extends java.awt.Point{

        public Point(int x, int y) {
			super(x, y);
		}

		public void move(Vector v) {
            x += v.magnitude * Math.cos(v.angle);
            y += v.magnitude * Math.sin(v.angle);
        }
    }
}
