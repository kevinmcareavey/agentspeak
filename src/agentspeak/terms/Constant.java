package agentspeak.terms;

import data_structures.AdvancedSet;
import agentspeak.Term;
import agentspeak.Unifier;

public abstract class Constant extends Term {
	
	@Override
	public boolean isGround() {
		return true;
	}

	@Override
	public AdvancedSet<Variable> getVariables() {
		return new AdvancedSet<Variable>();
	}
	
	@Override
	public Constant substitute(Unifier s) {
		return this;
	}
	
	@Override
	public Unifier unify(Constant c) {
		if(this.equals(c)) {
			return new Unifier();
		} else {
			return null;
		}
	}

	@Override
	public Unifier unify(Structure s) {
		return null;
	}
	
	@Override
	public Unifier unify(Variable v) {
		Unifier assignments = new Unifier();
		assignments.put(v, this);
		return assignments;
	}
	
}
