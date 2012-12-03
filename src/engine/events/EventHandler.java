package engine.events;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

public class EventHandler {

	protected HashMap<Class<EventObject>, ArrayList<Listener>> eventListenerMap;

	public EventHandler() {
		eventListenerMap = new HashMap<Class<EventObject>, ArrayList<Listener>>();
	}

	public <T extends EventObject> void addEventListener(Class<T> eventType,
			Listener listener) {

		ArrayList<Listener> eventListeners;
		if ((eventListeners = eventListenerMap.get(eventType)) == null) {
			eventListeners = new ArrayList<Listener>();
			eventListenerMap
					.put((Class<EventObject>) eventType, eventListeners);
		}

		eventListeners.add(listener);
	}

	public <T extends EventObject> void removeEventListener(Class<T> eventType,
			Listener listener) {
		ArrayList<Listener> eventListeners = eventListenerMap.get(eventType);

		eventListeners.remove(listener);
	}

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
