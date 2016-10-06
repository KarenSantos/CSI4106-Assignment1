package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates an action that can be the Start, Right, Left, Move and Suck.
 * 
 * @author karensaroc
 *
 */
public enum Action {

	START(0), RIGHT(20), LEFT(20), MOVE(50), SUCK(10);

	private int engery = 0;

	private Action(int engery) {
		this.engery = engery;
	}

	/**
	 * Returns a list of actions that are executable. That is, all actions
	 * except the Start.
	 * 
	 * @return A list of executable actions.
	 */
	public static List<Action> getActions() {
		List<Action> actions = new ArrayList<>();
		actions.add(Action.SUCK);
		actions.add(Action.MOVE);
		actions.add(Action.LEFT);
		actions.add(Action.RIGHT);
		return actions;
	}

	/**
	 * Returns the energy cost for this action.
	 * 
	 * @return The energy cost for this action.
	 */
	public int getEngery() {
		return engery;
	}
	
	public int Normalize(){
		return SUCK.engery+MOVE.engery+LEFT.engery+RIGHT.engery;
	}
}
