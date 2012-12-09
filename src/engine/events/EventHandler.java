package engine.events;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

public class EventHandler {

	protected HashMap<Class<EventObject>, ArrayList<Listener>> eventListenerMap;

	/**
	 * Initializes the event handler.
	 */
	public EventHandler() {
		eventListenerMap = new HashMap<Class<EventObject>, ArrayList<Listener>>();
	}

	/**
	 * Registers a listener with the event handler.
	 * 
	 * @param eventType
	 *            The event type to listen for.
	 * @param listener
	 *            The listener interested in the event.
	 */
	public synchronized <T extends EventObject> void addEventListener(
			Class<T> eventType, Listener listener) {

		ArrayList<Listener> eventListeners;
		if ((eventListeners = eventListenerMap.get(eventType)) == null) {
			eventListeners = new ArrayList<Listener>();
			eventListenerMap
					.put((Class<EventObject>) eventType, eventListeners);
		}

		eventListeners.add(listener);
	}

	/**
	 * Unregisters an event listener.
	 * 
	 * @param eventType
	 *            The type of event
	 * @param listener
	 *            The listener to remove.
	 */
	public synchronized <T extends EventObject> void removeEventListener(
			Class<T> eventType, Listener listener) {
		ArrayList<Listener> eventListeners = eventListenerMap.get(eventType);

		eventListeners.remove(listener);
	}

	/**
	 * Dispatches an event, calling all listeners registered for the appropriate
	 * event type.
	 * 
	 * @param event
	 *            The event to dispatch
	 */
	public synchronized void dispatchEvent(EventObject event) {
		ArrayList<Listener> listeners;
		if ((listeners = eventListenerMap.get(event.getClass())) == null) {
			return;
		}

		for (Listener l : listeners) {
			if (l.handle(event) == true)
				return;
		}
	}

}
