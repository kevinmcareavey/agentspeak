package agentspeak.actions;

import data_structures.AdvancedSet;
import agentspeak.Action;
import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.terms.Variable;

public class EnvironmentAction extends Action {
	
	private Term term;
	
	public EnvironmentAction(Term t) {
		term = t;
	}

	@Override
	public String toString() {
//		return "[Action: " + term.toString() + "]";
		return term.toString();
	}

	@Override
	public AdvancedSet<Variable> getVariables() {
		return term.getVariables();
	}
	
	public EnvironmentAction substitute(Unifier s) {
		return new EnvironmentAction(term.substitute(s));
	}

	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) {
		System.out.println("        action executed: " + this.substitute(u));
		return false;
	}
	
}
