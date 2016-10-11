package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that receives a grid and can calculate the evaluation function cost for
 * a specified node and a search method. In this case the A* method.
 * 
 * @author karensaroc
 *
 */
public class CostFunction {

	private Grid grid;

	/**
	 * Creates a cost function with a specified grid.
	 * 
	 * @param grid
	 *            The grid of the cost function.
	 */
	public CostFunction(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Sets the function cost for the current node according to the cumulative
	 * cost of the node and the heuristic from this node to the goal node.
	 * 
	 * @param currentNode
	 */
	public void setFunctionCost(Node currentNode) {
		int functionCost = currentNode.getCumulativeCost() + hcost(currentNode);
		currentNode.setFunctionCost(functionCost);
		System.out.println("Action: " + currentNode.getAction() + ", f(n): " + functionCost);
	}

	/**
	 * Returns the heuristic cost.
	 * 
	 * @param current
	 *            The current node.
	 * @return The heuristic cost.
	 */
	public int hcost(Node current) {
		int heuristicCost;
		Position robotPosition = current.getState().getRobotPos();
		// Orientation currentOrientation = current.getState().getOrientation();

		// deal with special 2 cases
		if (current.getState().getDirtPositions().isEmpty()) {
			heuristicCost = 0;
		} else if (current.getAction() == Action.SUCK) {
			heuristicCost = 0;
		}

		else {
			List<Position> dirts = current.getState().getDirtPositions();
			List<Position> distances = new ArrayList<>();

			for (Position dirt : dirts) {
				distances.add(new Position(dirt.getX() - robotPosition.getX(), dirt.getY() - robotPosition.getY()));
			}
			Collections.sort(distances, Position.positionComparator);
			Position dis = distances.get(0);
			heuristicCost = Math.max(Math.abs(dis.getX()), Math.abs(dis.getY()));
		}
		return heuristicCost;
	}

	/**
	 * This is a flawed heuristic to use for the A* method.
	 * 
	 * @return The heuristic cost.
	 */
	public int heuristicCost(Node current) {

		int heuristicCost = 0;

		if (current.getAction() != Action.SUCK) {
			int distanceFactor = 0;
			int obstaclesFactor = 0;
			int orientationFactor = 0;

			Position robotPosition = current.getState().getRobotPos();
			int totalDistances = 0;
			
			for (Position dirtPosition : current.getState().getDirtPositions()) {
				// calculates the sums of Manhattan distances from dirts to robot.
				totalDistances += robotPosition.getDistanceAsInteger(dirtPosition);
				
				// if orientation is not turning to one of the dirts increase cost
				if (!isInRightDirection(current.getState().getOrientation(), robotPosition, dirtPosition)){
					orientationFactor = Action.LEFT.getEngery();
				}

				// for each obstacle checks if they are in between the robot and a dirt and increase cost
				for (Position obsPosition : grid.getObstacles()) {
					if (hasObstacleInBetween(robotPosition, dirtPosition, obsPosition)) {
						obstaclesFactor += Action.MOVE.getEngery() * 4;
						break;
					}
				}
			}
			// takes the total distances and multiplies by the energy to move.
			distanceFactor = totalDistances * Action.MOVE.getEngery();

			heuristicCost = distanceFactor + obstaclesFactor + orientationFactor;
		}
		return heuristicCost;
	}

	private boolean hasObstacleInBetween(Position robot, Position dirt, Position obs) {
		boolean result = false;
		// if they are in the same column
		if (robot.getX() == dirt.getX() && robot.getX() == obs.getX()) {
			// if the obstacle is in between
			if (robot.getY() < obs.getY() && obs.getY() < dirt.getY()) {
				result = true;
			} else if (dirt.getY() < obs.getY() && obs.getY() < robot.getY()) {
				result = true;
			}
			// if they are in the same line
		} else if (robot.getY() == dirt.getY() && robot.getY() == obs.getY()) {
			// if the obstacle is in between
			if (robot.getX() < obs.getX() && obs.getX() < dirt.getX()) {
				result = true;
			} else if (dirt.getX() < obs.getX() && obs.getX() < robot.getX()) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean isInRightDirection(Orientation orientation, Position robot, Position dirt){
		boolean result = false;
		if (orientation.equals(Orientation.NORTH) && robot.getY() > dirt.getY()){
			result = true;
		} else if (orientation.equals(Orientation.EAST) && robot.getX() < dirt.getX()){
			result = true;
		} else if (orientation.equals(Orientation.SOUTH) && robot.getY() < dirt.getY()){
			result = true;
		} else if (orientation.equals(Orientation.WEST) && robot.getX() > dirt.getX()){
			result = true;
		}
		
		return result;
	}
}
