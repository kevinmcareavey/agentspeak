package agentspeak;

public abstract class Event {
	
	public EventTrigger trigger;
	public Intention intention;
	
	public EventTrigger getEventTrigger() {
		return trigger;
	}
	
	public Intention getIntention() {
		return intention;
	}
	
	public void setEvent(EventTrigger e) {
		trigger = e;
	}
	
	public void setIntention(Intention i) {
		intention = i;
	}
	
	public Term getTerm() {
		return trigger.getBeliefGoal().getTerm();
	}

	@Override
	public String toString() {
		return trigger.toString();
	}
	
}
