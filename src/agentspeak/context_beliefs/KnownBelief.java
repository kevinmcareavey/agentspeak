package agentspeak.context_beliefs;

import agentspeak.ContextBelief;
import agentspeak.belief_goals.Belief;

public class KnownBelief extends ContextBelief {
	
	public KnownBelief(Belief b) {
		super.setBelief(b);
	}
	
	@Override
	public String toString() {
		return super.getBelief().toString();
	}
	
}
