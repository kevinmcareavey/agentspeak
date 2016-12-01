package agentspeak.belief_goals.beliefs;

import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.belief_goals.Belief;

public class NegativeBelief extends Belief {
	
	public static final String SYMBOL_STRONG_NEGATION = "~";
	public static final String SYMBOL_DEFAULT_NEGATION = "not";
	
	public NegativeBelief(Term t) {
		super.setTerm(t);
	}
	
	@Override
	public NegativeBelief substitute(Unifier s) {
		return new NegativeBelief(this.getTerm().substitute(s));
	}
	
	@Override
	public String toString() {
		return SYMBOL_STRONG_NEGATION + this.getTerm().toString();
	}

	@Override
	public boolean isPositive() {
		return false;
	}

	@Override
	public NegativeBelief getBelief() {
		return this;
	}
	
}
