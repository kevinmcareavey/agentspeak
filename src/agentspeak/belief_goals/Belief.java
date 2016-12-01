package agentspeak.belief_goals;

import agentspeak.BeliefGoal;
import agentspeak.Term;
import agentspeak.Unifier;

public abstract class Belief extends BeliefGoal {
	
	public static final String SYMBOL_TRUE = "true";
	
	private Term term;
	
	@Override
	public Term getTerm() {
		return term;
	}
	
	public void setTerm(Term t) {
		term = t;
	}
	
	public abstract boolean isPositive();
	
	@Override
	public abstract Belief substitute(Unifier s);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		Belief other = (Belief) obj;
		if(term == null) {
			if(other.term != null) {
				return false;
			}
		} else if(!term.equals(other.term)) {
			return false;
		}
		return true;
	}

}
