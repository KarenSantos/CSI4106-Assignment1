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
		currentNode.setFunctionCost(currentNode.getCumulativeCost() + hcost(currentNode));
	}

	/**
	 * Returns the heuristic cost.
	 * 
	 * @param current
	 *            The current node.
	 * @return The heuristic cost.
	 */
	public int hcost(Node current) {
		int herutisticCost;
		Position robotPosition = current.getState().getRobotPos();
		// Orientation currentOrientation = current.getState().getOrientation();

		// deal with special 2 cases
		if (current.getState().getDirtPositions().isEmpty()) {
			herutisticCost = 0;
		} else if (current.getAction() == Action.SUCK) {
			herutisticCost = 0;
		}

		else {
			List<Position> dirts = current.getState().getDirtPositions();
			List<Position> distances = new ArrayList<>();

			for (Position dirt : dirts) {
				distances.add(new Position(dirt.getX() - robotPosition.getX(), dirt.getY() - robotPosition.getY()));
			}
			Collections.sort(distances, Position.positionComparator);
			Position dis = distances.get(0);
			herutisticCost = Math.max(Math.abs(dis.getX()), Math.abs(dis.getY()));
		}
		return herutisticCost;
	}
}
