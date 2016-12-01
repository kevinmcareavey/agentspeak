package agentspeak;

import java.util.LinkedList;

public class IntentionSet extends LinkedList<Intention> {
	
	private static final long serialVersionUID = 7851948538071990695L;
	
	public Intention selectIntention() {
		return this.poll();
	}
	
	public void adoptIntention(Event e, IntendedMeans up) {
		Intention i = e.getIntention();
		i.push(up);
		this.add(i);
		System.out.println("      intention adopted: " + i);
	}
	
}
