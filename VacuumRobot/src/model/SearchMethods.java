package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 * Class that executes a specified search method algorithm in a given grid.
 * 
 * @author karensaroc
 *
 */
public class SearchMethods {

	private final String DFS = "Depth-first Search";
	private final String BFS = "Breadth-first Search";
	private final String AStar = "A* Search";
	private Grid grid;
	private Node startNode;
	private CostFunction costFunction;
	private boolean isAStar;

	/**
	 * Creates a search method with a specified grid, a start state, a start
	 * node and a cost function.
	 * 
	 * @param grid
	 *            The grid to execute the search method.
	 */
	public SearchMethods(Grid grid) {
		this.grid = grid;
		State startState = new State(grid.getStartPosition(), grid.getStartOrientation(), grid.getDirt());
		this.startNode = new Node(null, startState, Action.START);
		this.costFunction = new CostFunction(grid);
		this.isAStar = false;
	}

	/**
	 * Returns a search solution for the specified grid with the Depth-first
	 * search method.
	 * 
	 * @return A search solution using Depth-first search method.
	 */
	public SearchSolution DFS() {
		this.isAStar = false;
		SearchSolution solution = new SearchSolution(DFS);
		List<Node> closed = new ArrayList<>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(startNode);
		solution.setMaxDepth(startNode.getDepth());
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.pop();
			solution.setMaxDepth(currentNode.getDepth());
			if (currentNode.getState().getDirtPositions().isEmpty()) {
				solution.setSolutionNode(currentNode);
				break;
			} else {
				List<Node> children = getSuccessors(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if (!closed.contains(n) && !fringe.contains(n)) {
						fringe.add(n);
					}
				}
			}
		}
		return solution;
	}

	/**
	 * Returns a search solution for the specified grid with the Breadth-first
	 * search method.
	 * 
	 * @return A search solution using Breadth-first search method.
	 */
	public SearchSolution BFS() {
		this.isAStar = false;
		SearchSolution solution = new SearchSolution(BFS);
		Queue<Node> fringe = new LinkedList<Node>();
		List<Node> closed = new ArrayList<>();
		fringe.offer(startNode);
		solution.setMaxDepth(startNode.getDepth());
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.poll();
			solution.setMaxDepth(currentNode.getDepth());
			if (currentNode.getState().getDirtPositions().isEmpty()) {
				solution.setSolutionNode(currentNode);
				break;
			} else {
				List<Node> children = getSuccessors(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if (!closed.contains(n) && !fringe.contains(n)) {
						fringe.offer(n);
					}
				}
			}
		}
		return solution;
	}

	/**
	 * Returns a search solution for the specified grid with the A* search
	 * method.
	 * 
	 * @return A search solution using A* search method.
	 */
	public SearchSolution AStar() {
		this.isAStar = true;
		SearchSolution solution = new SearchSolution(AStar);
		Queue<Node> fringe = new PriorityQueue<Node>();
		List<Node> closed = new ArrayList<>();
		fringe.offer(startNode);
		solution.setMaxDepth(startNode.getDepth());
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.poll();
			System.out.println("chose node: " + currentNode.getAction() + ", " + currentNode.getFunctionCost());
			solution.setMaxDepth(currentNode.getDepth());
			if (currentNode.getState().getDirtPositions().isEmpty()) {
				solution.setSolutionNode(currentNode);
				break;
			} else {
				List<Node> children = getSuccessors(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if (!closed.contains(n) && !fringe.contains(n))
						fringe.offer(n);
				}
			}
		}
		return solution;
	}

	/**
	 * Finds the successor nodes for a specific node according to actions
	 * available and the type of search.
	 * 
	 * @param node
	 *            The node from where to find the successor nodes.
	 * @return A list of successor nodes for the specified node.
	 */
	private List<Node> getSuccessors(Node node) {
		System.out.println();
		List<Node> children = new ArrayList<>();
		State state = node.getState();

		List<Action> actions = Action.getActions();
		for (Action a : actions) {
			State childState = new State();

			if (a == Action.SUCK) {
				if (state.isDirty()) {
					childState.setRobotPos(state.getRobotPos());
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(new ArrayList<Position>(state.getDirtPositions()));
					childState.clean(state.getRobotPos());
					Node childNode = new Node(node, childState, a);
					if (isAStar)
						costFunction.setFunctionCost(childNode);
					children.add(childNode);
				}
			} else if (a == Action.MOVE) {
				Position newPosition = state.getRobotPos().showPositionMoving(state.getOrientation());
				if (grid.isPositionAllowed(newPosition)) {
					childState.setRobotPos(newPosition);
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(state.getDirtPositions());
					Node childNode = new Node(node, childState, a);
					if (isAStar)
						costFunction.setFunctionCost(childNode);
					children.add(childNode);
				}
			} else if (a == Action.LEFT) {
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.LEFT));
				childState.setDirtPositions(state.getDirtPositions());
				Node childNode = new Node(node, childState, a);
				if (isAStar)
					costFunction.setFunctionCost(childNode);
				children.add(childNode);
				
			} else if (a == Action.RIGHT) {
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.RIGHT));
				childState.setDirtPositions(state.getDirtPositions());
				Node childNode = new Node(node, childState, a);
				if (isAStar)
					costFunction.setFunctionCost(childNode);
				children.add(childNode);
			}
		}
		return children;
	}

	/**
	 * Returns the new orientation according the current orientation and the
	 * action specified.
	 * 
	 * @param currentOrientation
	 *            The current orientation.
	 * @param turn
	 *            The action.
	 * @return The new orientation.
	 */
	private Orientation getNewOrientation(Orientation currentOrientation, Action turn) {
		Orientation next = null;
		if (turn == Action.LEFT) {
			if (currentOrientation == Orientation.NORTH) {
				next = Orientation.WEST;
			} else if (currentOrientation == Orientation.WEST) {
				next = Orientation.SOUTH;
			} else if (currentOrientation == Orientation.SOUTH) {
				next = Orientation.EAST;
			} else if (currentOrientation == Orientation.EAST) {
				next = Orientation.NORTH;
			}
		} else if (turn == Action.RIGHT) {
			if (currentOrientation == Orientation.NORTH) {
				next = Orientation.EAST;
			} else if (currentOrientation == Orientation.EAST) {
				next = Orientation.SOUTH;
			} else if (currentOrientation == Orientation.SOUTH) {
				next = Orientation.WEST;
			} else if (currentOrientation == Orientation.WEST) {
				next = Orientation.NORTH;
			}
		}
		return next;
	}
}
