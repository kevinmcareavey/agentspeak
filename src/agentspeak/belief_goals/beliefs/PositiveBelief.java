package agentspeak.belief_goals.beliefs;

import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.belief_goals.Belief;

public class PositiveBelief extends Belief {
	
	public PositiveBelief(Term t) {
		super.setTerm(t);
	}
	
	@Override
	public PositiveBelief substitute(Unifier s) {
		return new PositiveBelief(this.getTerm().substitute(s));
	}
	
	@Override
	public String toString() {
		return this.getTerm().toString();
	}

	@Override
	public boolean isPositive() {
		return true;
	}

	@Override
	public PositiveBelief getBelief() {
		return this;
	}
	
}
