package agentspeak;

import java.util.LinkedList;

public class EventSet extends LinkedList<Event> {
	
	private static final long serialVersionUID = 2246224042350616096L;
	
	public Event selectEvent() {
		return this.poll();
	}
	
}
