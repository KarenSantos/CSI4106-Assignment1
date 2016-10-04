package model;

import java.util.ArrayList;
import java.util.List;

public enum Action {

	START(0),RIGHT (20), LEFT(20), MOVE(50), SUCK(10);
	int engery=0;
	Action(int engery){
		this.engery=engery;
	}
	public static List<Action> getActions(){
		List<Action> actions = new ArrayList<>();
		actions.add(Action.LEFT);
		actions.add(Action.MOVE);
		actions.add(Action.RIGHT);
		actions.add(Action.SUCK);
		return actions;
	}
	public int getEngery(){
		return engery;
	}
	
}
