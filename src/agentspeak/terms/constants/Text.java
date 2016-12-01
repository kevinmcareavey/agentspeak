package agentspeak.terms.constants;

import agentspeak.terms.Constant;

public class Text extends Constant {
	
	private String value;
	
	public Text(String v) {
		value = v;
	}
	
	public static boolean isValid(String s) {
		return s.charAt(0) == '\'' && s.charAt(s.length()-1) == '\'';
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Text other = (Text) obj;
		if(value == null) {
			if(other.value != null) {
				return false;
			}
		} else if(!value.equals(other.value)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return value;
	}

}
