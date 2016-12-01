package agentspeak.actions.goal_actions;

import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Unifier;
import agentspeak.actions.GoalAction;
import agentspeak.belief_goals.goals.AchievementGoal;
import agentspeak.event_triggers.AddEvent;
import agentspeak.events.InternalEvent;

public class AchievementGoalAction extends GoalAction {
	
	private AchievementGoal achievementGoal;
	
	public AchievementGoalAction(AchievementGoal ag) {
		achievementGoal = ag;
	}

	@Override
	public AchievementGoal getGoal() {
		return achievementGoal;
	}
	
	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) {
		/*
		 * Should the goal (partially) instantiated when generating a new event?
		 */
		AchievementGoal ground = achievementGoal.substitute(u);
		es.add(new InternalEvent(new AddEvent(ground), i));
		System.out.println("        subgoal created: " + ground);
		return true;
	}
	
}
