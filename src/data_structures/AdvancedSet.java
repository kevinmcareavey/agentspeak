package data_structures;

import java.util.Collection;
import java.util.HashSet;

public class AdvancedSet<T> extends HashSet<T> {
	
	private static final long serialVersionUID = -8301196773045563033L;

	public AdvancedSet() {
		super();
	}
	
	@SafeVarargs
	public AdvancedSet(T... inputs) {
		super();
		for(int i = 0; i < inputs.length; i++) {
			this.add(inputs[i]);
		}
	}
	
	public AdvancedSet(Collection<T> c) {
		super(c);
	}
	
	public boolean subsetOf(AdvancedSet<T> other) {
		return other.containsAll(this);
	}
	
	public boolean supersetOf(AdvancedSet<T> other) {
		return this.containsAll(other);
	}
	
	public boolean intersects(AdvancedSet<T> other) {
		if(!this.isEmpty() && !other.isEmpty()) {
			for(T t : other) {
				if(this.contains(t)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public AdvancedSet<T> setminus(AdvancedSet<T> other) {
		AdvancedSet<T> result = new AdvancedSet<T>();
		for(T element : this) {
			if(!other.contains(element)) {
				result.add(element);
			}
		}
		return result;
	}
	
	public AdvancedSet<T> copy() {
		AdvancedSet<T> copy = new AdvancedSet<T>(this);
		return copy;
	}
	
	@Override
	public String toString() {
		return super.toString().replace("[", "{").replace("]", "}");
	}

}
