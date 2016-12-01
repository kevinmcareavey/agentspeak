package agentspeak.actions;

import data_structures.AdvancedSet;
import agentspeak.Action;
import agentspeak.belief_goals.Belief;
import agentspeak.terms.Variable;

public abstract class BeliefAction extends Action {
	
	private Belief belief;
	
	public Belief getBelief() {
		return belief;
	}
	
	public void setBelief(Belief b) {
		belief = b;
	}

	@Override
	public AdvancedSet<Variable> getVariables() {
		return belief.getTerm().getVariables();
	}
	
}
