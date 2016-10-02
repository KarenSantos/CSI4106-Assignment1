package model;

// put in the state things that can change in the problem. not the obstacles.
//if you have a consistent heuristic your f(n) will never decrease.
//heuristic could be going to all dirt without thinking about the obstacles.

/**
 * State class that has the action taken to arrive at this state, and the robot
 * position and orientation after that action.
 * 
 * @author karensaroc
 *
 */
public class State {

	private Position robotPos;
	private Orientation orientation;
	private Action action;
	private int amountOfDirt;

	/**
	 * Creates a state with the action taken to arrive at this state, and the
	 * robot position and orientation after that action.
	 * 
	 * @param action
	 *            The action taken to arrive at this state.
	 * @param robotPos
	 *            The position of the robot after the action.
	 * @param orientation
	 *            The orientation of the robot after the action.
	 * @param amountOfDirt
	 *            The amount of dirt left in the grid.
	 */
	public State(Action action, Position robotPos, Orientation orientation, int amountOfDirt) {
		this.action = action;
		this.robotPos = robotPos;
		this.orientation = orientation;
		this.amountOfDirt = amountOfDirt;
	}

	/**
	 * Returns the action taken to arrive at this state.
	 * 
	 * @return The action taken to arrive at this state.
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the action taken to arrive at this state.
	 * 
	 * @param action
	 *            The action taken to arrive at this state.
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * Returns the robot position at this state.
	 * 
	 * @return The robot position.
	 */
	public Position getRobotPos() {
		return robotPos;
	}

	/**
	 * Sets the robot position for this state.
	 * 
	 * @param robotPos
	 *            The new robot position for this state.
	 */
	public void setRobotPos(Position robotPos) {
		this.robotPos = robotPos;
	}

	/**
	 * Returns the robot orientation at this state.
	 * 
	 * @return The robot orientation at this state.
	 */
	public Orientation getOrientation() {
		return orientation;
	}

	/**
	 * Sets the robot orientation at this state.
	 * 
	 * @param orientation
	 *            The new robot orientation.
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Return the amount of dirt left in the grid.
	 * 
	 * @return The amount of dirt left in the grid.
	 */
	public int getAmountOfDirt() {
		return amountOfDirt;
	}

	/**
	 * Sets the amount of dirt left in the grid.
	 * 
	 * @param amountOfDirt
	 *            The new amount of dirt left in the grid.
	 */
	public void setAmountOfDirt(int amountOfDirt) {
		this.amountOfDirt = amountOfDirt;
	}

}
