package agentspeak;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import agentspeak.actions.EnvironmentAction;
import agentspeak.actions.belief_actions.AddBeliefAction;
import agentspeak.actions.belief_actions.DeleteBeliefAction;
import agentspeak.actions.goal_actions.AchievementGoalAction;
import agentspeak.actions.goal_actions.TestGoalAction;
import agentspeak.belief_goals.Belief;
import agentspeak.belief_goals.Goal;
import agentspeak.belief_goals.beliefs.NegativeBelief;
import agentspeak.belief_goals.beliefs.PositiveBelief;
import agentspeak.belief_goals.goals.AchievementGoal;
import agentspeak.belief_goals.goals.TestGoal;
import agentspeak.context_beliefs.KnownBelief;
import agentspeak.context_beliefs.UnknownBelief;
import agentspeak.event_triggers.AddEvent;
import agentspeak.event_triggers.DeleteEvent;
import agentspeak.events.ExternalEvent;
import agentspeak.terms.Structure;
import agentspeak.terms.Variable;
import agentspeak.terms.constants.Atom;
import agentspeak.terms.constants.Number;
import agentspeak.terms.constants.Text;

public class Parser {
	
	private Queue<String> input;
	
	private Queue<String> tokenize(String s) {
		Queue<String> input = new LinkedList<String>();
		
		s = s.replace(Plan.SYMBOL_CONTEXT, " * ");
		s = s.replace(Plan.SYMBOL_CONJUNCTION, " ** ");
		s = s.replace(Plan.SYMBOL_ACTIONS, " *** ");
		s = s.replace(Plan.SYMBOL_ACTION_SEPARATOR, " **** ");
		s = s.replace(AddEvent.SYMBOL_OPERATOR, " ***** ");
		s = s.replace(DeleteEvent.SYMBOL_OPERATOR, " ****** ");
		s = s.replace(AchievementGoal.SYMBOL_OPERATOR, " ******* ");
		s = s.replace(TestGoal.SYMBOL_OPERATOR, " ******** ");
		s = s.replace(NegativeBelief.SYMBOL_STRONG_NEGATION, " ********* ");
		
		s = s.replace(" ********* ", " " + NegativeBelief.SYMBOL_STRONG_NEGATION + " ");
		s = s.replace(" ******** ", " " + TestGoal.SYMBOL_OPERATOR + " ");
		s = s.replace(" ******* ", " " + AchievementGoal.SYMBOL_OPERATOR + " ");
		s = s.replace(" ****** ", " " + DeleteEvent.SYMBOL_OPERATOR + " ");
		s = s.replace(" ***** ", " " + AddEvent.SYMBOL_OPERATOR + " ");
		s = s.replace(" **** ", " " + Plan.SYMBOL_ACTION_SEPARATOR + " ");
		s = s.replace(" *** ", " " + Plan.SYMBOL_ACTIONS + " ");
		s = s.replace(" ** ", " " + Plan.SYMBOL_CONJUNCTION + " ");
		s = s.replace(" * ", " " + Plan.SYMBOL_CONTEXT + " ");
		
		s = s.replace("(", " ( ");
		s = s.replace(")", " ) ");
		s = s.replace(",", " , ");
		
		s = s.trim();
		
		String[] tokens = s.split("\\s+");
		for(String token : tokens) {
			input.add(token);
		}
		return input;
	}
	
	private String next() throws ParseException {	
		if(input.isEmpty()) {
			throw new ParseException("unexpected end of input", input.size());
		}
		
		while(input.peek().isEmpty()) {
			consume();
		}
		
		return input.peek();
	}
	
	private void consume() {
		input.poll();
	}
	
	private void expect(String s) throws ParseException {
		if(next().equals(s)) {
			consume();
		} else {
			throw new ParseException("expected symbol: " + s, input.size());
		}
	}
	
	public Term parseTerm(String s) throws ParseException {
		input = tokenize(s);
		
		Term t = term();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return t;
	}
	
	public Belief parseBelief(String s) throws ParseException {
		input = tokenize(s);
		
		Belief b = belief();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return b;
	}
	
	public Goal parseGoal(String s) throws ParseException {
		input = tokenize(s);
		
		Goal g = goal();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return g;
	}
	
	public Event parseEvent(String s) throws ParseException {
		input = tokenize(s);
		
		Event e = event();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return e;
	}
	
	public Plan parsePlan(String s) throws ParseException {
		input = tokenize(s);
		
		Plan p = plan();
		
		if(!input.isEmpty()) {
			throw new ParseException("incomplete parsing", input.size());
		}
		
		return p;
	}
	
	private Plan plan() throws ParseException {
		Event e = event();
		expect(Plan.SYMBOL_CONTEXT);
		Queue<ContextBelief> context = new LinkedList<ContextBelief>();
		if(!next().equals(Belief.SYMBOL_TRUE)) {
			context.add(contextBelief());
		} else {
			consume();
		}
		while(next().equals(Plan.SYMBOL_CONJUNCTION)) {
			consume();
			if(!next().equals(Belief.SYMBOL_TRUE)) {
				context.add(contextBelief());
			} else {
				consume();
			}
		}
		expect(Plan.SYMBOL_ACTIONS);
		List<Action> actions = new ArrayList<Action>();
		if(!next().equals(Belief.SYMBOL_TRUE)) {
			actions.add(action());
		} else {
			consume();
		}
		while(!input.isEmpty() && next().equals(Plan.SYMBOL_ACTION_SEPARATOR)) {
			consume();
			if(!next().equals(Belief.SYMBOL_TRUE)) {
				actions.add(action());
			} else {
				consume();
			}
		}
		return new Plan(e, context, actions);
	}
	
	private Action action() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_OPERATOR)) {
			return new AchievementGoalAction(achievementGoal());
		} else if(next().equals(TestGoal.SYMBOL_OPERATOR)) {
			return new TestGoalAction(testGoal());
		} else if(next().equals(AddEvent.SYMBOL_OPERATOR)) {
			consume();
			return new AddBeliefAction(belief());
		} else if(next().equals(DeleteEvent.SYMBOL_OPERATOR)) {
			consume();
			return new DeleteBeliefAction(belief());
		} else {
			return new EnvironmentAction(term());
		}
	}
	
	private Event event() throws ParseException {
		if(next().equals(AddEvent.SYMBOL_OPERATOR)) {
			consume();
			return new ExternalEvent(new AddEvent(beliefGoal()));
		} else if(next().equals(DeleteEvent.SYMBOL_OPERATOR)) {
			consume();
			return new ExternalEvent(new DeleteEvent(beliefGoal()));
		} else {
			throw new ParseException("event operator expected at: " + next(), input.size());
		}
	}
	
	private BeliefGoal beliefGoal() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_OPERATOR) || next().equals(TestGoal.SYMBOL_OPERATOR)) {
			return goal();
		} else {
			return belief();
		}
	}
	
	private Goal goal() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_OPERATOR)) {
			return achievementGoal();
		} else if(next().equals(TestGoal.SYMBOL_OPERATOR)) {
			return testGoal();
		} else {
			throw new ParseException("goal operator expected at: " + next(), input.size());
		}
	}
	
	private TestGoal testGoal() throws ParseException {
		if(next().equals(TestGoal.SYMBOL_OPERATOR)) {
			consume();
			return new TestGoal(belief());
		} else {
			throw new ParseException("goal operator expected at: " + next(), input.size());
		}
	}
	
	private AchievementGoal achievementGoal() throws ParseException {
		if(next().equals(AchievementGoal.SYMBOL_OPERATOR)) {
			consume();
			return new AchievementGoal(belief());
		} else {
			throw new ParseException("goal operator expected at: " + next(), input.size());
		}
	}
	
	private ContextBelief contextBelief() throws ParseException {
		if(next().equals(ContextBelief.SYMBOL_DEFAULT_NEGATION)) {
			consume();
			return new UnknownBelief(belief());
		} else {
			return new KnownBelief(belief());
		}
	}
	
	private Belief belief() throws ParseException {
		if(next().equals(NegativeBelief.SYMBOL_STRONG_NEGATION)) {
			consume();
			return new NegativeBelief(term());
		} else {
			return new PositiveBelief(term());
		}
	}
	
	private Term term() throws ParseException {
		if(Variable.isValid(next())) {
			return variable();
		} else if(Atom.isValid(next())) {
			Atom a = atom();
			
			if(!input.isEmpty() && next().equals("(")) {
				List<Term> arguments = new ArrayList<Term>();
				consume();
				Term t1 = term();
				arguments.add(t1);
				while(!next().equals(")")) {
					expect(",");
					Term t2 = term();
					arguments.add(t2);
				}
				consume();
				return new Structure(a, arguments);
			} else {
				return a;
			}
		} else if(Number.isValid(next())) {
			return number();
		} else if(Text.isValid(next())) {
			return text();
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Text text() throws ParseException {
		if(Text.isValid(next())) {
			Text t = new Text(next());
			consume();
			return t;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Number number() throws ParseException {
		if(Number.isValid(next())) {
			Number n = new Number(next());
			consume();
			return n;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Atom atom() throws ParseException {
		if(Atom.isValid(next())) {
			Atom a = new Atom(next());
			consume();
			return a;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
	private Term variable() throws ParseException {
		if(Variable.isValid(next())) {
			Variable v = new Variable(next());
			consume();
			return v;
		} else {
			throw new ParseException("parsing error at: " + next(), input.size());
		}
	}
	
}
