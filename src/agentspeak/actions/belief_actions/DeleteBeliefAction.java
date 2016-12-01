package agentspeak.actions.belief_actions;

import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Unifier;
import agentspeak.actions.BeliefAction;
import agentspeak.belief_goals.Belief;
import agentspeak.event_triggers.DeleteEvent;
import agentspeak.events.ExternalEvent;

public class DeleteBeliefAction extends BeliefAction {
	
	public DeleteBeliefAction(Belief b) {
		super.setBelief(b);
	}
	
	@Override
	public String toString() {
		return DeleteEvent.SYMBOL_OPERATOR + super.getBelief().toString();
	}
	
	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) {
		Belief b = this.getBelief().substitute(u);
		bb.remove(b);
		System.out.println("         belief deleted: " + b);
		es.add(new ExternalEvent(new DeleteEvent(b)));
		return false;
	}
	
}
