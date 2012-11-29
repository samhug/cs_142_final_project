package events;

import java.util.EventObject;

public interface Listener {

	public boolean handle(EventObject e);
	
}
