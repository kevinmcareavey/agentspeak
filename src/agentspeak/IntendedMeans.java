package agentspeak;

public class IntendedMeans {
	
	private Plan plan;
	private Unifier unifier;
	private int idx;
	
	public IntendedMeans(Plan p, Unifier u) {
		plan = p;
		unifier = u;
		idx = 0;
	}
	
	@Override
	public String toString() {
		return "<" + plan + ", " + unifier + ", " + idx + ">";
	}

	public Plan getPlan() {
		return plan;
	}

	public Unifier getUnifier() {
		return unifier;
	}
	
	public boolean actionsRemain() {
		return idx < plan.getActions().size();
	}
	
	public Action nextAction() {
		return plan.getActions().get(idx);
	}

	public void setUnifier(Unifier newUnifier) {
		unifier = newUnifier;
	}
	
	public Action previousAction() {
		return plan.getActions().get(idx - 1);
	}
	
	public boolean executeNextAction(Intention i, BeliefBase bb, EventSet es) {
		boolean subgoalCreated = false;
		if(this.actionsRemain()) {
			Action a = this.nextAction();
			subgoalCreated = a.executeAction(unifier, i, bb, es);
			idx++;
		}
		return subgoalCreated;
	}
	
}
