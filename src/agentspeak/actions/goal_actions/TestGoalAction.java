package agentspeak.actions.goal_actions;

import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.actions.GoalAction;
import agentspeak.belief_goals.Belief;
import agentspeak.belief_goals.goals.TestGoal;
import agentspeak.event_triggers.AddEvent;
import agentspeak.events.InternalEvent;

public class TestGoalAction extends GoalAction {
	
	private TestGoal testGoal;
	
	public TestGoalAction(TestGoal tg) {
		testGoal = tg;
	}

	@Override
	public TestGoal getGoal() {
		return testGoal;
	}

	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) {
		boolean found = false;
		Term term = testGoal.getTerm();
		Unifier existingUnifier = u;
		for(Belief belief : bb) {
			if(testGoal.getBelief().isPositive() == belief.isPositive()) {
				Unifier newUnifier = term.unify(belief.getTerm(), existingUnifier);
				if(newUnifier != null) {
					found = true;
					u.putAll(newUnifier);
					break;
				}
			}
		}
		if(!found) {
			/*
			 * Should the goal (partially) instantiated when generating a new event?
			 */
			TestGoal ground = testGoal.substitute(u);
			es.add(new InternalEvent(new AddEvent(ground), i));
			return true;
		} else {
			return false;
		}
	}
	
}
