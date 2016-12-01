package agentspeak.actions.belief_actions;

import agentspeak.BeliefBase;
import agentspeak.EventSet;
import agentspeak.Intention;
import agentspeak.Unifier;
import agentspeak.actions.BeliefAction;
import agentspeak.belief_goals.Belief;
import agentspeak.event_triggers.AddEvent;
import agentspeak.events.ExternalEvent;

public class AddBeliefAction extends BeliefAction {
	
	public AddBeliefAction(Belief b) {
		super.setBelief(b);
	}

	@Override
	public String toString() {
		return AddEvent.SYMBOL_OPERATOR + super.getBelief().toString();
	}
	
	@Override
	public boolean executeAction(Unifier u, Intention i, BeliefBase bb, EventSet es) {
		Belief b = this.getBelief().substitute(u);
		bb.add(b);
		System.out.println("           belief added: " + b);
		es.add(new ExternalEvent(new AddEvent(b)));
		return false;
	}
	
}
