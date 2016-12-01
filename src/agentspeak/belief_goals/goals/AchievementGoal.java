package agentspeak.belief_goals.goals;

import agentspeak.Unifier;
import agentspeak.belief_goals.Belief;
import agentspeak.belief_goals.Goal;

public class AchievementGoal extends Goal {
	
	public static final String SYMBOL_OPERATOR = "!";
	
	public AchievementGoal(Belief b) {
		super.setBelief(b);
	}
	
	@Override
	public AchievementGoal substitute(Unifier s) {
		return new AchievementGoal(this.getBelief().substitute(s));
	}
	
	@Override
	public String toString() {
		return SYMBOL_OPERATOR + super.getBelief().toString();
	}
	
}
