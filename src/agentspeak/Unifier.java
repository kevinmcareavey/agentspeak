package agentspeak;

import java.util.HashMap;

import agentspeak.terms.Variable;

public class Unifier extends HashMap<Variable, Term> {
	
	private static final long serialVersionUID = -2311891166425596012L;
	
	public Unifier copy() {
		Unifier copy = new Unifier();
		copy.putAll(this);
		return copy;
	}
	
}
