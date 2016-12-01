package agentspeak;

import data_structures.AdvancedSet;
import agentspeak.terms.Variable;

public abstract class Action {
	
	public abstract AdvancedSet<Variable> getVariables();
	
	public abstract boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es);
	
}
