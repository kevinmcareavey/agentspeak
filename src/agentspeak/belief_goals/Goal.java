package agentspeak.belief_goals;

import agentspeak.BeliefGoal;
import agentspeak.Term;
import agentspeak.Unifier;

public abstract class Goal extends BeliefGoal {
	
	private Belief belief;
	
	public Belief getBelief() {
		return belief;
	}
	
	public void setBelief(Belief b) {
		belief = b;
	}
	
	@Override
	public Term getTerm() {
		return belief.getTerm();
	}
	
	@Override
	public abstract Goal substitute(Unifier s);
	
}
