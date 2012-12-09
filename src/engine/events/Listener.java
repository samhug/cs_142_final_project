package engine.events;

import java.util.EventObject;

public interface Listener {

	/**
	 * This method is called when the event being listened for is dispatched.
	 * 
	 * @param e
	 *            The event
	 * 
	 * @return If this method returns true. The event handler will not call any
	 *         other listeners for this event.
	 */
	public boolean handle(EventObject e);

}
