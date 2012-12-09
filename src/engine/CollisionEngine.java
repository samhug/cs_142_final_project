package engine;

import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
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
	
	public void addListener(final GameObject source, final GameObject victim, final Listener listener) {
		
		ArrayList<GameObject> victimObjects;
		if ((victimObjects = objectCollisionMap.get(source)) == null) {
			victimObjects = new ArrayList<GameObject>();
			objectCollisionMap.put(source, victimObjects);
		}

		victimObjects.add(victim);
		
		eh.addEventListener(CollisionEvent.class, new engine.events.Listener() {			
			
			@Override
			public boolean handle(EventObject e_) {
				
				final CollisionEvent e = (CollisionEvent)e_;
				final GameObject object1 = (GameObject)e.getSource();
				final GameObject object2 = e.getVictim();
				
				if (source == object1 && victim == object2 || source == object2 && victim == object1) {
					listener.handle(e_);
				}
				
				return false;
			}
		});
	}
	
	protected void onUpdate(long tick) {

		Iterator<Map.Entry<GameObject, ArrayList<GameObject>>> it = objectCollisionMap.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry<GameObject, ArrayList<GameObject>> pairs = it.next();
	        
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
		
		if (objectShapesIntersect(source, victim)) {
			eh.dispatchEvent(new CollisionEvent(source, victim));
		}
	}
	
	public boolean objectShapesIntersect(GameObject source, GameObject victim)
    {
		Shape shapeA = source.getTranslateTransform().createTransformedShape(source.getShape());
		Shape shapeB = victim.getTranslateTransform().createTransformedShape(victim.getShape());
		
		PathIterator piA = shapeA.getPathIterator(null);
		PathIterator piB = shapeB.getPathIterator(null);
		
		double[] coords = new double[6];
        Point2D aP1, aP2, bP1, bP2;
        Line2D l1, l2;
        
        piA.currentSegment(coords);
    	aP1 = new Point2D.Double(coords[0], coords[1]);
    	piA.next();
    	
        while (!piA.isDone()) {
        	
        	piA.currentSegment(coords);
        	aP2 = new Point2D.Double(coords[0], coords[1]);
        	piA.next();
        	l1 = new Line2D.Double(aP1, aP2);
        	
            piB.currentSegment(coords);
        	bP1 = new Point2D.Double(coords[0], coords[1]);
        	piB.next();
        	
            while (!piB.isDone()) {
            	
            	piB.currentSegment(coords);
            	bP2 = new Point2D.Double(coords[0], coords[1]);
            	piB.next();
            	l2 = new Line2D.Double(bP1, bP2);
            	
            	if (l1.intersectsLine(l2))
            		return true;
            	
            	if (shapeA.contains(bP1))
            		return true;
            	
            	bP1 = bP2;
            }
            
            aP1 = aP2;
        }
        
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
