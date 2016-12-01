package agentspeak.terms.constants;

import agentspeak.terms.Constant;

public class Atom extends Constant {
	
	private String label;
	
	public Atom(String l) {
		label = l;
	}
	
	public static boolean isValid(String s) {
		return Character.isLowerCase(s.charAt(0));
	}
	
	@Override
	public String toString() {
		return label;
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
		Atom other = (Atom) obj;
		if(label == null) {
			if(other.label != null) {
				return false;
			}
		} else if(!label.equals(other.label)) {
			return false;
		}
		return true;
	}
	
}
