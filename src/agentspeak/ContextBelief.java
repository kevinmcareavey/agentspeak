package agentspeak;

import agentspeak.belief_goals.Belief;

public class ContextBelief {
	
	public static final String SYMBOL_DEFAULT_NEGATION = "not";
	
	private Belief belief;
	
	public Belief getBelief() {
		return belief;
	}
	
	public void setBelief(Belief b) {
		belief = b;
	}
	
}
