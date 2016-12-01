package agentspeak.belief_goals.goals;

import agentspeak.Unifier;
import agentspeak.belief_goals.Belief;
import agentspeak.belief_goals.Goal;

public class TestGoal extends Goal {
	
	public static final String SYMBOL_OPERATOR = "?";
	
	public TestGoal(Belief b) {
		super.setBelief(b);
	}
	
	@Override
	public TestGoal substitute(Unifier s) {
		return new TestGoal(getBelief().substitute(s));
	}
	
	@Override
	public String toString() {
		return SYMBOL_OPERATOR + super.getBelief().toString();
	}
	
}
