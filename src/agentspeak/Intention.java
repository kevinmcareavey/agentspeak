package agentspeak;

import java.util.Stack;

public class Intention {
	
	private Stack<IntendedMeans> unifiedPlans;
	
	public Intention() {
		unifiedPlans = new Stack<IntendedMeans>();
	}
	
	public void push(IntendedMeans p) {
		unifiedPlans.push(p);
	}
	
	public IntendedMeans top() {
		return unifiedPlans.peek();
	}
	
	public int size() {
		return unifiedPlans.size();
	}
	
	public IntendedMeans pop() {
		return unifiedPlans.pop();
	}
	
	public boolean isTrueIntention() {
		return unifiedPlans.isEmpty();
	}

	@Override
	public String toString() {
		Stack<IntendedMeans> copy = new Stack<IntendedMeans>();
		copy.addAll(unifiedPlans);
		Stack<IntendedMeans> reverse = new Stack<IntendedMeans>();
		while(!copy.empty()) {
			reverse.push(copy.pop());
		}
		return reverse.toString();
	}
	
	public void executeIntention(BeliefBase bb, EventSet es, IntentionSet is) throws Exception {
		boolean subgoalCreated = this.top().executeNextAction(this, bb, es);
		if(!subgoalCreated) {
			boolean intentionComplete = this.propagateAssignments(is);
			if(!intentionComplete) {
				is.add(this);
			}
		}
	}
	
	public boolean propagateAssignments(IntentionSet is) throws Exception {
		while(!this.top().actionsRemain()) {
			if(this.size() == 1) {
				return true;
			} else {
				/*
				 * How should assignments be propagated through the intention?
				 */
				IntendedMeans popped = this.pop();
//				Action previous = up.previousAction();
//				AdvancedSet<Variable> variables = previous.getVariables();
//				for(Variable var : variables) {
//					if(popped.getUnifier().containsKey(var)) {
//						Unifier assignments = up.getUnifier();
//						assignments.put(var, popped.getUnifier().get(var));
//						up.setUnifier(assignments);
//					}
//				}
				Unifier assignments = this.top().getUnifier();
				assignments.putAll(popped.getUnifier());
				this.top().setUnifier(assignments);
			}
		}
		return false;
	}
	
}
