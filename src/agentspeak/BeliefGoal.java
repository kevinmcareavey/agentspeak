package agentspeak;

import agentspeak.belief_goals.Belief;

public abstract class BeliefGoal {
	
	public abstract Term getTerm();
	
	public abstract Belief getBelief();
	
	public abstract BeliefGoal substitute(Unifier s);
	
}
