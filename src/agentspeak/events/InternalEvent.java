package agentspeak.events;

import agentspeak.Event;
import agentspeak.EventTrigger;
import agentspeak.Intention;

public class InternalEvent extends Event {
	
	public InternalEvent(EventTrigger e, Intention i) {
		super.setEvent(e);
		super.setIntention(i);
	}
	
}
