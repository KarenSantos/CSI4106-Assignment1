package model;

import java.util.ArrayList;
import java.util.List;

public enum Action {

	RIGHT, LEFT, MOVE, SUCK;
	
	public static List<Action> getActions(){
		List<Action> actions = new ArrayList<>();
		actions.add(Action.LEFT);
		actions.add(Action.MOVE);
		actions.add(Action.RIGHT);
		actions.add(Action.SUCK);
		return actions;
	}
	
}
