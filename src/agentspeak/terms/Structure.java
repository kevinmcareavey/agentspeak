package agentspeak.terms;

import java.util.ArrayList;
import java.util.List;

import data_structures.AdvancedSet;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.terms.constants.Atom;

public class Structure extends Term {
	
	private Atom functor;
	private List<Term> arguments;
	
	public Structure(Atom f, List<Term> a) {
		functor = f;
		arguments = a;
	}
	
	public int arity() {
		return arguments.size();
	}
	
	public Atom getFunctor() {
		return functor;
	}
	
	public Term getArgument(int i) {
		return arguments.get(i);
	}
	
	public List<Term> getArguments() {
		return arguments;
	}
	
	@Override
	public String toString() {
		String output = functor.toString() + "(";
		String delim = "";
		for(Term arg : arguments) {
			output += delim + arg.toString();
			delim = ",";
		}
		output += ")";
		return output;
	}

	@Override
	public boolean isGround() {
		for(Term arg : arguments) {
			if(!arg.isGround()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public AdvancedSet<Variable> getVariables() {
		AdvancedSet<Variable> variables = new AdvancedSet<Variable>();
		for(Term arg : arguments) {
			variables.addAll(arg.getVariables());
		}
		return variables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((arguments == null) ? 0 : arguments.hashCode());
		result = prime * result + ((functor == null) ? 0 : functor.hashCode());
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
		Structure other = (Structure) obj;
		if(arguments == null) {
			if(other.arguments != null) {
				return false;
			}
		} else if(!arguments.equals(other.arguments)) {
			return false;
		}
		if(functor == null) {
			if(other.functor != null) {
				return false;
			}
		} else if(!functor.equals(other.functor)) {
			return false;
		}
		return true;
	}
	
	@Override
	public Structure substitute(Unifier s) {
		List<Term> newArguments = new ArrayList<Term>();
		for(Term arg : arguments) {
			newArguments.add(arg.substitute(s));
		}
		return new Structure(functor, newArguments);
	}

	@Override
	public Unifier unify(Constant c) {
		return null;
	}

	@Override
	public Unifier unify(Structure s) {
		if(functor.equals(s.getFunctor())) {
			if(this.arity() == s.arity()) {
				Unifier assignments = new Unifier();
				for(int i = 0; i < this.arity(); i++) {
					Unifier argAssignments = this.getArgument(i).unify(s.getArgument(i), assignments);
					if(argAssignments != null) {
						assignments.putAll(argAssignments);
					} else {
						return null;
					}
				}
				return assignments;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public Unifier unify(Variable v) {
		Unifier assignments = new Unifier();
		assignments.put(v, this);
		return assignments;
	}
	
}
