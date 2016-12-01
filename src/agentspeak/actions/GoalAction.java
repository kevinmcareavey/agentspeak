package agentspeak.actions;

import data_structures.AdvancedSet;
import agentspeak.Action;
import agentspeak.belief_goals.Goal;
import agentspeak.terms.Variable;

public abstract class GoalAction extends Action {
	
	public abstract Goal getGoal();
	
	@Override
	public AdvancedSet<Variable> getVariables() {
		return this.getGoal().getTerm().getVariables();
	}
	
	@Override
	public String toString() {
		return this.getGoal().toString();
	}
	
}
