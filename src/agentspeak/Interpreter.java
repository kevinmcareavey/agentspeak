package agentspeak;

import java.util.LinkedList;
import java.util.Queue;

import agentspeak.belief_goals.Belief;
import agentspeak.context_beliefs.KnownBelief;
import agentspeak.context_beliefs.UnknownBelief;

public class Interpreter {
	
	private BeliefBase beliefBase;
	private PlanLibrary planLibrary;
	private EventSet eventSet;
	private IntentionSet intentionSet;
	
	public Interpreter() {
		beliefBase = new BeliefBase();
		planLibrary = new PlanLibrary();
		eventSet = new EventSet();
		intentionSet = new IntentionSet();
	}
	
	public BeliefBase getBeliefBase() {
		return beliefBase;
	}
	
	public PlanLibrary getPlanLibrary() {
		return planLibrary;
	}
	
	public EventSet getEventSet() {
		return eventSet;
	}
	
	public void run() throws Exception {
		System.out.println("                     P = " + planLibrary);
		
		while(!(eventSet.isEmpty() && intentionSet.isEmpty())) {
			try {
			    Thread.sleep(500);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			System.out.println("                     B = " + beliefBase);
			System.out.println("                     E = " + eventSet);
			
			if(!eventSet.isEmpty()) {
				Event e = eventSet.selectEvent();
				if(e != null) {
					System.out.println("         event selected: " + e);
					IntendedMeans im = selectPlan(e);
					if(im != null) {
						System.out.println("intended means selected: " + im);
						intentionSet.adoptIntention(e, im);
					}
				}
			}
			
			System.out.println("                     I = " + intentionSet);
			if(!intentionSet.isEmpty()) {
				Intention i = intentionSet.selectIntention();
				if(i != null) {
					System.out.println("     intention selected: " + i);
					i.executeIntention(beliefBase, eventSet, intentionSet);
				}
			}
		}
	}
	
	public IntendedMeans selectPlan(Event e) throws Exception {
		Queue<IntendedMeans> relevant = selectRelevantPlans(e);
		if(!relevant.isEmpty()) {
			Queue<IntendedMeans> applicable = selectApplicablePlans(relevant);
			if(!applicable.isEmpty()) {
				return selectPlan(applicable);
			}
		}
		return null;
	}
	
	public Queue<IntendedMeans> selectRelevantPlans(Event e) throws Exception {
		Queue<IntendedMeans> relevant = new LinkedList<IntendedMeans>();
		for(Plan p : planLibrary) {
			if(e.getEventTrigger().getBeliefGoal().getBelief().isPositive() == p.getTrigger().getEventTrigger().getBeliefGoal().getBelief().isPositive()) {
				Unifier u = e.getTerm().unify(p.getTrigger().getTerm());
				if(u != null) {
					relevant.add(new IntendedMeans(p, u));
				}
			}
		}
		return relevant;
	}
	
	public Queue<IntendedMeans> selectApplicablePlans(Queue<IntendedMeans> r) throws Exception {
		Queue<IntendedMeans> copy = new LinkedList<IntendedMeans>(r);
		Queue<IntendedMeans> applicable = new LinkedList<IntendedMeans>();
		while(!copy.isEmpty()) {
			IntendedMeans up = copy.poll();
			Queue<ContextBelief> context = up.getPlan().getContext();
			if(!context.isEmpty()) {
				Unifier u = unifyContext(context, context, up.getUnifier());
				if(u != null) {
					applicable.add(new IntendedMeans(up.getPlan(), u));
				}
			} else {
				applicable.add(up);
			}
		}
		return applicable;
	}
	
	public IntendedMeans selectPlan(Queue<IntendedMeans> a) {
		return a.peek();
	}
	
	/*
	 * Return Unifier, if context is a logical consequence of the belief base;
	 * Return null, otherwise.
	 */
	public Unifier unifyContext(Queue<ContextBelief> original, Queue<ContextBelief> c, Unifier u) throws Exception {
		// this method is called with the Unifier of the triggering event
		if(u == null) {
			throw new Exception("context must not be pre-blocked, i.e., event unifier should not be null");
		}
		
		// copying c to remaining
		Queue<ContextBelief> remaining = new LinkedList<ContextBelief>(c);
		
		// copy u to existing
		Unifier originalUnifier = u.copy();
		
		// if there are no remaining context beliefs then just return the input Unifier
		if(remaining.isEmpty()) {
			return originalUnifier;
		} else {
			
			// dequeue the first context belief
			ContextBelief head = remaining.poll();
			
			// record if head unifies with any belief in the belief base
			boolean unifies = false;
			
			// for each each belief in the belief base
			for(Belief belief : beliefBase) {
				
				// check if both are positive or both are negative, no need to unify terms if they are not
				if(head.getBelief().isPositive() == belief.isPositive()) {
					
					// check if it unifies with the context belief given the original Unifier
					Unifier possibleUnifier = head.getBelief().getTerm().unify(belief.getTerm(), originalUnifier);
					
					// if head unifies with belief
					if(possibleUnifier != null) {
						
						// record that head unifies with a belief
						unifies = true;
						
						// if head is a known belief then it should unify with some belief in the belief base
						if(head instanceof KnownBelief) {
							
							// if this is true then try to unify the rest of the context given this new Unifier
							possibleUnifier = unifyContext(original, remaining, possibleUnifier);
							
							// if a Unifier is found then return this Unifier, otherwise continue to next belief
							if(possibleUnifier != null) {
								return possibleUnifier;
							}
						}
					}
				}
				
				// if head is an unknown belief then it should not unify with any belief in the belief base
				if(!unifies && head instanceof UnknownBelief) {
					
					// if this is true then just continue with the search using the original Unifier
					return unifyContext(original, remaining, originalUnifier);
				}
			}
		}
		
		return null;
	}
	
}
