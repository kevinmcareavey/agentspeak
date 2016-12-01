package agentspeak.terms.constants;

import agentspeak.terms.Constant;

public class Number extends Constant {
	
	private int value;
	
	public Number(int v) {
		value = v;
	}
	
	public Number(String s) {
		this(Integer.parseInt(s));
	}
	
	public static boolean isValid(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    return true;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
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
		Number other = (Number) obj;
		if(value != other.value) {
			return false;
		}
		return true;
	}

}
