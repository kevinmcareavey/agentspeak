package agentspeak.context_beliefs;

import agentspeak.ContextBelief;
import agentspeak.belief_goals.Belief;

public class UnknownBelief extends ContextBelief {
	
	public UnknownBelief(Belief b) {
		super.setBelief(b);
	}
	
	@Override
	public String toString() {
		return UnknownBelief.SYMBOL_DEFAULT_NEGATION + " " + super.getBelief().toString();
	}
	
}
