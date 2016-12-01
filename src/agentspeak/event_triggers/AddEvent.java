package agentspeak.event_triggers;

import agentspeak.BeliefGoal;
import agentspeak.EventTrigger;

public class AddEvent extends EventTrigger {
	
	public static final String SYMBOL_OPERATOR = "+";
	
	public AddEvent(BeliefGoal bg) {
		super.setBeliefGoal(bg);
	}
	
	@Override
	public String toString() {
		return SYMBOL_OPERATOR + super.getBeliefGoal().toString();
	}

}
