package model;

import java.util.List;

// put in the state things that can change in the problem. not the obstacles.
//if you have a consistent heuristic your f(n) will never decrease.

/**
 * State class that has the action taken to arrive at this state, the robot
 * position and orientation after that action, and the dirt positions left to be
 * cleaned.
 * 
 * @author karensaroc
 *
 */
public class State {

	private Position robotPos;
	private Orientation orientation;
	private List<Position> dirtPositions;

	/**
	 * Creates a state with null values;
	 */
	public State() {
	}

	/**
	 * Creates a state with the action taken to arrive at this state, and the
	 * robot position and orientation after that action.
	 * 
	 * @param robotPos
	 *            The position of the robot after the action.
	 * @param orientation
	 *            The orientation of the robot after the action.
	 * @param dirtPositions
	 *            The list with the positions of dirt left to clean.
	 */
	public State(Position robotPos, Orientation orientation, List<Position> dirtPositions) {
		this.robotPos = robotPos;
		this.orientation = orientation;
		this.dirtPositions = dirtPositions;
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
	 * Return the dirt positions left to be cleaned.
	 * 
	 * @return The dirt positions left to be cleaned.
	 */
	public List<Position> getDirtPositions() {
		return dirtPositions;
	}

	/**
	 * Sets the dirt positions left to be cleaned.
	 * 
	 * @param dirtPositions
	 *            The new dirt left in the grid.
	 */
	public void setDirtPositions(List<Position> dirtPositions) {
		this.dirtPositions = dirtPositions;
	}

	/**
	 * Removes a dirt position from the list of dirt positions left to be
	 * cleaned.
	 * 
	 * @param dirtPosition
	 */
	public void clean(Position dirtPosition) {
		this.dirtPositions.remove(dirtPosition);
	}

	@Override
	public String toString() {
		return robotPos + ", " + orientation;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof State) {
			State s = (State) obj;
			if (s.getRobotPos().equals(this.getRobotPos()) && s.getOrientation() == this.getOrientation()
					&& s.getDirtPositions().size() == this.getDirtPositions().size()) {
				result = true;
				for (Position p : s.getDirtPositions()) {
					if (!this.getDirtPositions().contains(p)) {
						result = false;
					}
				}
			}
		}
		return result;
	}

}
