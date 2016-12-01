package agentspeak;

import java.util.List;
import java.util.Queue;

public class Plan {
	
	public static final String SYMBOL_CONTEXT = ":";
	public static final String SYMBOL_CONJUNCTION = "&";
	public static final String SYMBOL_ACTIONS = "<-";
	public static final String SYMBOL_ACTION_SEPARATOR = ";";
	
	private Event trigger;
	private Queue<ContextBelief> context;
	private List<Action> actions;
	
	public Plan(Event e, Queue<ContextBelief> c, List<Action> a) {
		trigger = e;
		context = c;
		actions = a;
	}
	
	public Event getTrigger() {
		return trigger;
	}
	
	public Queue<ContextBelief> getContext() {
		return context;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	@Override
	public String toString() {
		String output = trigger.toString() + " " + SYMBOL_CONTEXT + " ";
		String delim;
		if(context.isEmpty()) {
			output += "true";
		} else {
			delim = "";
			for(ContextBelief b : context) {
				output += delim + b.toString();
				delim = " " + SYMBOL_CONJUNCTION + " ";
			}
		}
		output += " " + SYMBOL_ACTIONS + " ";
		if(actions.isEmpty()) {
			output += "true";
		} else {
			delim = "";
			for(Action a : actions) {
				output += delim + a.toString();
				delim = SYMBOL_ACTION_SEPARATOR + " ";
			}
		}
		return output;
	}
	
}
