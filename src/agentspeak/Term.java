package agentspeak;

import data_structures.AdvancedSet;
import agentspeak.terms.Constant;
import agentspeak.terms.Structure;
import agentspeak.terms.Variable;

public abstract class Term {
	
	public abstract boolean isGround();
	
	public abstract AdvancedSet<Variable> getVariables();
	
	public Unifier unify(Term t) {
		if(t instanceof Constant) {
			return this.unify((Constant) t);
		} else if(t instanceof Structure) {
			return this.unify((Structure) t);
		} else if(t instanceof Variable) {
			return this.unify((Variable) t);
		} else {
			return null;
		}
	}
	public abstract Unifier unify(Constant c);
	public abstract Unifier unify(Structure s);
	public abstract Unifier unify(Variable v);
	
	public Unifier unify(Term t, Unifier s) {
		if(s != null) {
			Unifier copy = s.copy();
			Unifier assignments = this.substitute(copy).unify(t.substitute(copy));
			if(assignments != null) {
				copy.putAll(assignments);
				return copy;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public abstract Term substitute(Unifier s);
	
}