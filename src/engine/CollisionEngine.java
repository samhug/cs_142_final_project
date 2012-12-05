package engine;

import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import engine.Engine.UpdateEvent;
import engine.events.EventHandler;
import engine.events.Event;
import engine.events.Listener;

public class CollisionEngine {
	
	private EventHandler eh;
	
	private HashMap<GameObject, ArrayList<GameObject>> objectCollisionMap;

	public CollisionEngine(EventHandler eh) {
		this.eh = eh;
		
		objectCollisionMap = new HashMap<GameObject, ArrayList<GameObject>>();
		
		eh.addEventListener(Engine.UpdateEvent.class,
				new engine.events.Listener() {

					@Override
					public boolean handle(EventObject e_) {
						final UpdateEvent e = (UpdateEvent) e_;

						onUpdate(e.tick);
						return false;
					}
				});
	}
	
	public void addListener(GameObject source, GameObject victim, Listener listener) {
		
		ArrayList<GameObject> victimObjects;
		if ((victimObjects = objectCollisionMap.get(source)) == null) {
			victimObjects = new ArrayList<GameObject>();
			objectCollisionMap.put(source, victimObjects);
		}

		victimObjects.add(victim);
		
		eh.addEventListener(CollisionEvent.class, listener);
	}
	
	protected void onUpdate(long tick) {

		Iterator it = objectCollisionMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        
	        GameObject source             = (GameObject) pairs.getKey();
	        ArrayList<GameObject> victims = (ArrayList<GameObject>) pairs.getValue();
	        
	        for (GameObject victim : victims) {
	        	checkCollision(source, victim);
			}
	    }
	}
	
	protected void checkCollision(GameObject source, GameObject victim) {
		
		
		
		AffineTransform.getTranslateInstance(source.position.getX(), source.position.getY());
		AffineTransform.getTranslateInstance(victim.position.getX(), victim.position.getY());
		
		//Rectangle2D sourceBounds = source.getBounds();
		//Rectangle2D victimBounds = victim.getBounds();
		
		if (objectShapesIntersect(source, victim)) {
			eh.dispatchEvent(new CollisionEvent(source, victim));
		}
	}
	
	public boolean objectShapesIntersect(GameObject source, GameObject victim)
    {
		
		Shape shapeA = AffineTransform.getTranslateInstance(source.position.getX(), source.position.getY()).createTransformedShape(source.getShape());
		Shape shapeB = AffineTransform.getTranslateInstance(victim.position.getX(), victim.position.getY()).createTransformedShape(victim.getShape());
		
		PathIterator piA = shapeA.getPathIterator(null);
		PathIterator piB = shapeB.getPathIterator(null);
		
		double[] coords = new double[6];
        Point2D p;
        while (!piA.isDone()) {
        	int type = piA.currentSegment(coords);
        	
        	p = new Point2D.Double(coords[0], coords[1]);
            //System.out.print("["+p.getX() + "," + p.getY()+"], ");
        	/*if (type == PathIterator.SEG_LINETO) {
            	p = new Point2D.Double(coords[0], coords[1]);
            }
            else if (type == PathIterator.SEG_CLOSE) {
            	p = new Point2D.Double(0, 0);
            }
            else {
                System.out.println("Unsported shape.");
                System.exit(1);
                return false;
            }*/
            
            if(shapeB.contains(p))
                return true;
            
            piA.next();
        }
        //System.out.println("");

        while (!piB.isDone()) {
        	int type = piB.currentSegment(coords);
        	
        	p = new Point2D.Double(coords[0], coords[1]);
        	//System.out.print("["+p.getX() + "," + p.getY()+"], ");
            /*if (type == PathIterator.SEG_LINETO) {
            	p = new Point2D.Double(coords[0], coords[1]);
            }
            else if (type == PathIterator.SEG_CLOSE) {
            	p = new Point2D.Double(0, 0);
            }
            else {
            	System.out.println("Unsported shape.");
                System.exit(1);
                return false;
            }*/
            
            if(shapeA.contains(p))
                return true;
            
            piB.next();
        }
        //System.out.println("");
        
        return false;
    }
	
	public static class CollisionEvent extends Event {

		final GameObject victim;

		public CollisionEvent(Object source, GameObject victim) {
			super(source);
			this.victim = victim;
		}
		
		public GameObject getVictim() {
			return victim;
		}
	}
}
