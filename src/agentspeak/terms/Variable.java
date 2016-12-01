package agentspeak.terms;

import data_structures.AdvancedSet;
import agentspeak.Term;
import agentspeak.Unifier;

public class Variable extends Term {
	
	private String label;
	
	public Variable(String l) {
		label = l;
	}
	
	public static boolean isValid(String s) {
		return Character.isUpperCase(s.charAt(0)) || s.equals("_");
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		Variable other = (Variable) obj;
		if(label == null) {
			if(other.label != null) {
				return false;
			}
		} else if(!label.equals(other.label)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
//		return "[Variable: " + label + "]";
		return label;
	}

	@Override
	public boolean isGround() {
		return false;
	}

	@Override
	public AdvancedSet<Variable> getVariables() {
		return new AdvancedSet<Variable>(this);
	}
	
	@Override
	public Term substitute(Unifier s) {
		if(s != null && s.containsKey(this)) {
			Term sub = s.get(this);
			return sub.substitute(s);
		} else {
			return this;
		}
	}

	@Override
	public Unifier unify(Constant c) {
		Unifier assignments = new Unifier();
		assignments.put(this, c);
		return assignments;
	}

	@Override
	public Unifier unify(Structure s) {
		Unifier assignments = new Unifier();
		assignments.put(this, s);
		return assignments;
	}

	@Override
	public Unifier unify(Variable v) {
		if(this.equals(v)) {
			return new Unifier();
		} else {
			Unifier assignments = new Unifier();
			assignments.put(this, v);
			return assignments;
		}
	}
	
}
