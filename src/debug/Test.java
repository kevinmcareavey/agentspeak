package debug;

import java.util.LinkedList;
import java.util.Queue;

import agentspeak.ContextBelief;
import agentspeak.IntendedMeans;
import agentspeak.Interpreter;
import agentspeak.Parser;
import agentspeak.Plan;
import agentspeak.Term;
import agentspeak.Unifier;
import agentspeak.context_beliefs.KnownBelief;
import agentspeak.context_beliefs.UnknownBelief;
import agentspeak.event_triggers.AddEvent;
import agentspeak.events.ExternalEvent;
import agentspeak.terms.Variable;
import agentspeak.terms.constants.Atom;

public class Test {
	
	public static void parsingBeliefs() {
		
		try {
			Parser p = new Parser();
			
			// valid
			System.out.println(p.parseBelief("a"));
			System.out.println(p.parseBelief("belief(a)"));
			System.out.println(p.parseBelief("belief(a,b)"));
			System.out.println(p.parseBelief("belief(a,b,c)"));
			System.out.println(p.parseBelief("belief(X)"));
			System.out.println(p.parseBelief("belief(X,Y)"));
			System.out.println(p.parseBelief("belief(a,X)"));
			System.out.println(p.parseBelief("belief(X,a)"));
//			System.out.println(p.parseBelief("belief(1)"));
//			System.out.println(p.parseBelief("belief(1,2)"));
			System.out.println(p.parseBelief("be(lief(x))"));
			System.out.println(p.parseBelief("be(lief(X))"));
			System.out.println(p.parseBelief("be(lief(x,y),z)"));
			System.out.println(p.parseBelief("be(lief(X,Y),Z)"));
			System.out.println(p.parseBelief("Belief"));
			
			// invalid
//			System.out.println(p.parseBelief("a,b"));
//			System.out.println(p.parseBelief("belief()"));
//			System.out.println(p.parseBelief("belief("));
//			System.out.println(p.parseBelief("belief)"));
//			System.out.println(p.parseBelief("(belief)"));
//			System.out.println(p.parseBelief("belief(x)(x)"));
//			System.out.println(p.parseBelief("Belief(a)"));
//			System.out.println(p.parseBelief("Belief(X)"));
//			System.out.println(p.parseBelief("belief(X(a))"));
//			System.out.println(p.parseBelief("belief(X(Y))"));
////			System.out.println(p.parseBelief("1(a)"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void parsingPlans() {
		
		try {
			Parser p = new Parser();
//			System.out.println(p.parseBelief("human(bob)"));
//			System.out.println(new Event(EventOperator.ADD, p.parseBelief("human(bob)")));
//			System.out.println(new Event(EventOperator.DELETE, p.parseBelief("human(bob)")));
//			System.out.println(new Goal(GoalOperator.ACHIEVEMENT, p.parseBelief("kill(bob)")));
//			System.out.println(new Goal(GoalOperator.TEST, p.parseBelief("kill(bob)")));
//			System.out.println(new Action(p.parseBelief("kill(bob)")));
			
			System.out.println(p.parsePlan("+saveHuman:human(X)&human(Y)<-kill(X);+save(Y)"));
			System.out.println(p.parsePlan("-saveHuman:human(X)&human(Y)<-!kill(X);save(Y)"));
			System.out.println(p.parsePlan("+!saveHuman:human(X)&human(Y)<-kill(X);-save(Y)"));
			System.out.println(p.parsePlan("-!saveHuman:human(X)&human(Y)<-?kill(X);save(Y)"));
			System.out.println(p.parsePlan("+?saveHuman:human(X)&human(Y)<-kill(X);save(Y)"));
			System.out.println(p.parsePlan("-?saveHuman:human(X)&human(Y)<-kill(X);save(Y)"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void unification() {
		
		try {
			Parser p = new Parser();
			
			Term left;
			Term right;
			
			left = p.parseTerm("a");
			right = p.parseTerm("a");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("a");
			right = p.parseTerm("b");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("X");
			right = p.parseTerm("X");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("X");
			right = p.parseTerm("a");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("a");
			right = p.parseTerm("X");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("X");
			right = p.parseTerm("Y");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("f(a,X)");
			right = p.parseTerm("f(a,b)");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("f(a)");
			right = p.parseTerm("g(a)");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("f(X)");
			right = p.parseTerm("f(Y)");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("f(g(a,Y),c)");
			right = p.parseTerm("f(g(X,b),Z)");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			
			left = p.parseTerm("f(a,b)");
			right = p.parseTerm("f(X,X)");
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void intentions() {
		
		try {
			Parser p = new Parser();
			Plan p1 = p.parsePlan("+!saveHuman:attacking(X,Y)<-kill(X);?save(Y)");
			Unifier u1 = new Unifier();
			u1.put(new Variable("X"), p.parseTerm("alice"));
			u1.put(new Variable("Y"), p.parseTerm("bob"));
			IntendedMeans up = new IntendedMeans(p1, u1);
			System.out.println(up);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void interpreter() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			i.getBeliefBase().add(p.parseBelief("human(alice)"));
			i.getPlanLibrary().add(p.parsePlan("+saveHuman : human(X) <- !rescue(X)"));
			i.getPlanLibrary().add(p.parsePlan("+!rescue(X) : true <- +safe(X)"));
			i.getEventSet().add(new ExternalEvent(new AddEvent(p.parseBelief("saveHuman"))));
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void example() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			i.getBeliefBase().add(p.parseBelief("flight(b, c)"));
			i.getBeliefBase().add(p.parseBelief("closest(c, d)"));
			i.getBeliefBase().add(p.parseBelief("closest(a, b)"));
			i.getBeliefBase().add(p.parseBelief("location(a)"));
			
			i.getPlanLibrary().add(p.parsePlan("+!travel(a, d) : true <- !travel_taxi(a, X2); !travel_flight(X2, Y2); !travel_bus(Y2, d); print('arrived')"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_taxi(X3, Y3) : location(X3) <- ?airport(Y3); taxi(X3, Y3); -location(X3); +location(Y3)"));
			i.getPlanLibrary().add(p.parsePlan("+?airport(X4) : location(Y4) & closest(Y4, X4) <- true"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_flight(X5, Y5) : location(X5) & flight(X5, Y5) <- ?location(_); -location(_); +location(Y5)"));
			i.getPlanLibrary().add(p.parsePlan("+!travel_bus(Y6, d) : location(Y6) <- ?location(_); -location(_); +location(d)"));
			
			i.getEventSet().add(new ExternalEvent(new AddEvent(p.parseGoal("!travel(a, d)"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void unify() {
		
		try {
			Parser p = new Parser();
			
			Term left = p.parseBelief("a").getTerm();
			Term right = p.parseBelief("X").getTerm();
			
			Unifier constraint = new Unifier();
			constraint.put(new Variable("X"), new Atom("b"));
			
			System.out.println("unify(" + left + ", " + right + ") = " + left.unify(right));
			System.out.println("substitute(" + left + ", " + left.unify(right) + ") = " + left.substitute(left.unify(right)));
			System.out.println("substitute(" + right + ", " + left.unify(right) + ") = " + right.substitute(left.unify(right)));
			System.out.println();
			System.out.println("unify(" + right + ", " + left + ") = " + right.unify(left));
			System.out.println("substitute(" + left + ", " + right.unify(left) + ") = " + left.substitute(right.unify(left)));
			System.out.println("substitute(" + right + ", " + right.unify(left) + ") = " + right.substitute(right.unify(left)));
			System.out.println();
			System.out.println("unify(" + left + ", " + right + ", " + constraint + ") = " + left.unify(right, constraint));
			System.out.println("unify(" + right + ", " + left + ", " + constraint + ") = " + right.unify(left, constraint));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void unifyContext() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			i.getBeliefBase().add(p.parseBelief("flight(b,c)"));
			i.getBeliefBase().add(p.parseBelief("closest(c,d)"));
			i.getBeliefBase().add(p.parseBelief("closest(a,b)"));
			i.getBeliefBase().add(p.parseBelief("location(e)"));
			i.getBeliefBase().add(p.parseBelief("location(a)"));
			
			Queue<ContextBelief> condition = new LinkedList<ContextBelief>();
			condition.add(new KnownBelief(p.parseBelief("location(X)")));
			condition.add(new KnownBelief(p.parseBelief("closest(X,Y)")));
			condition.add(new KnownBelief(p.parseBelief("closest(Z,d)")));
			condition.add(new KnownBelief(p.parseBelief("flight(Y,Z)")));
			
			Unifier constraint = new Unifier();
//			constraint.put(new Variable("X"), new Atom("a"));
			System.out.print(i.unifyContext(condition, condition, constraint));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void negation() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			i.getPlanLibrary().add(p.parsePlan("+run : true <- +~belief(a); ?~belief(X); print(X)"));
			i.getEventSet().add(new ExternalEvent(new AddEvent(p.parseBelief("run"))));
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void lips() {
		try {
			Parser p = new Parser();
			Term t1 = p.parseTerm("p(X, a, f(Y))");
			Term t2 = p.parseTerm("p(f(b), Z, f(c))");
			int runs = 1000000;
			long startTime = System.nanoTime();    
			for(int i = 0; i < runs; i++) {
				t1.unify(t2);
			}
			long totalTime = System.nanoTime() - startTime;
			double milliseconds = (double)totalTime / (double)1000000;
			System.out.print("unify(" + t1 + ", " + t2 + ") = " + t1.unify(t2) + " times " + runs + " in " + milliseconds + " ms");
		} catch(Exception e) {
			
		}
	}
	
	public static void inconsistency() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			
			i.getBeliefBase().add(p.parseBelief("atLocation(a)"));
			i.getBeliefBase().add(p.parseBelief("~atLocation(b)"));
			
			i.getPlanLibrary().add(p.parsePlan("+!whereIAm : atLocation(X) <- print('at_',X)"));
			i.getPlanLibrary().add(p.parsePlan("+!whereIAmNot : ~atLocation(X) <- print('not_at_',X)"));
			
			i.getEventSet().add(new ExternalEvent(new AddEvent(p.parseGoal("!whereIAm"))));
			i.getEventSet().add(new ExternalEvent(new AddEvent(p.parseGoal("!whereIAmNot"))));
			
			i.run();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void epistemicState() {
		
		try {
			Parser p = new Parser();
			Interpreter i = new Interpreter();
			i.getBeliefBase().add(p.parseBelief("signal(a)"));
			i.getBeliefBase().add(p.parseBelief("signal(b)"));
			i.getBeliefBase().add(p.parseBelief("signal(c)"));
			i.getBeliefBase().add(p.parseBelief("signal(d)"));
			i.getBeliefBase().add(p.parseBelief("~signal(a)"));
			i.getBeliefBase().add(p.parseBelief("~signal(b)"));
			i.getBeliefBase().add(p.parseBelief("~signal(c)"));
			i.getBeliefBase().add(p.parseBelief("~signal(d)"));
			
			Queue<ContextBelief> condition = new LinkedList<ContextBelief>();
			condition.add(new UnknownBelief(p.parseBelief("signal(d)")));
			
			Unifier constraint = new Unifier();
//			constraint.put(new Variable("X"), new Atom("a"));
			System.out.print(i.unifyContext(condition, condition, constraint));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
//		parsingBeliefs();
//		parsingPlans();
//		unification();
//		intentions();
//		interpreter();
		example();
//		unify();
//		unifyContext();
//		negation();
//		lips();
//		inconsistency();
//		epistemicState();
		
	}

}
