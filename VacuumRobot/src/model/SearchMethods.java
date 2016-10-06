package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	private int branchFactor=0;
	private Grid grid;
	private Node startNode;
	

	private Fcost cost;

	/**
	 * Creates a search method with a specified grid.
	 * 
	 * @param grid
	 *            The grid to execute the search method.
	 */
	public SearchMethods(Grid grid,int method) {
		this.grid = grid;
		State startState = new State(Action.START, grid.getStartPosition(), grid.getStartOrientation(), grid.getDirt());
		startNode = new Node(null, startState);

		cost=new Fcost(grid,method);
	}

	/**
	 * Returns a search solution for the specified grid with the Depth-first
	 * search method.
	 * 
	 * @return A search solution using Depth-first search method.
	 */
	public SearchSolution DFS() {
		
		SearchSolution solution = new SearchSolution(DFS);
		List<Node> closed = new ArrayList<>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(startNode);
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.pop();
			branchFactor++;

			if (currentNode.getState().getDirtPositions().isEmpty()) {
				solution.setSolutionNode(currentNode);
				break;
			} else {
				List<Node> children = getSuccessorsDFS(currentNode);
				closed.add(currentNode);

				for (Node n : children) {
					if (!closed.contains(n) && !fringe.contains(n)) {
						fringe.add(n);
						
					}
				}
			}
		}
		solution.setBranchFactor(branchFactor);
		return solution;
	}

	/**
	 * Returns a search solution for the specified grid with the Breadth-first
	 * search method.
	 * 
	 * @return A search solution using Breadth-first search method.
	 */
	public SearchSolution BFS() {
		
		SearchSolution solution = new SearchSolution(BFS);
		Queue<Node> fringe = new LinkedList<Node>();
		List<Node> closed = new ArrayList<>();
		fringe.offer(startNode);

		while (!fringe.isEmpty()) {
			Node currentNode = fringe.poll();
			branchFactor++;
			if (currentNode.getState().getDirtPositions().isEmpty()) {
				solution.setSolutionNode(currentNode);
				break;
			} else {
				List<Node> children = getSuccessorsDFS(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if (!closed.contains(n) && !fringe.contains(n)) {
						fringe.offer(n);
					}
				}
			}
		}
		solution.setBranchFactor(branchFactor);
		return solution;
	}

	/**
	 * Returns a search solution for the specified grid with the A* search
	 * method.
	 * 
	 * @return A search solution using A* search method.
	 */
	public SearchSolution AStar() {
		
		SearchSolution solution = new SearchSolution(AStar);
		List<Node> closed = new ArrayList<>();
		Queue<Node> fringe = new PriorityQueue<Node>(10, new Comparator<Node>() {
			public int compare(Node n1, Node n2) {
				if (n1.getState().getEngery() <= n2.getState().getEngery())
					return -1;
				return 1;

			}
		});

		fringe.offer(startNode);
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.poll();
			branchFactor++;
//			System.out.println(currentNode.getState().toString() + "\t" + currentNode.getState().getEngery());

			if (currentNode.getState().getDirtPositions().isEmpty()) {
				solution.setSolutionNode(currentNode);
				break;
			} else {
				List<Node> children = getSuccessorsDFS(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if (!closed.contains(n))
						fringe.offer(n);

				}
			}
		}
		solution.setBranchFactor(branchFactor);
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
	private List<Node> getSuccessorsDFS(Node node) {
		List<Node> children = new ArrayList<>();
		State state = node.getState();
		
		cost.setParentCost(node.getState().getEngery());
		List<Action> actions = Action.getActions();
		for (Action a : actions) {
			State childState = new State();
			 
			
			if (a == Action.LEFT) {

				childState.setAction(a);
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.LEFT));
				childState.setDirtPositions(state.getDirtPositions());
				cost.setState(childState);
				childState.setEngery(cost.getCost());
				children.add(new Node(node, childState));
				// System.out.println("Action: " + a + "\tTotalCost: " +
				// childState.getEngery());

			} else if (a == Action.MOVE) {
				Position newPosition = state.getRobotPos().showPositionMoving(state.getOrientation());
				if (grid.isPositionAllowed(newPosition)) {
					childState.setAction(a);

					childState.setRobotPos(newPosition);
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(state.getDirtPositions());
					cost.setState(childState);
					childState.setEngery(cost.getCost());
					children.add(new Node(node, childState));
					// System.out.println("Action: " + a + "\tTotalCost: " +
					// childState.getEngery());
				}
			} else if (a == Action.RIGHT) {
				childState.setAction(a);

				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.RIGHT));
				childState.setDirtPositions(state.getDirtPositions());
				cost.setState(childState);
				childState.setEngery(cost.getCost());
				children.add(new Node(node, childState));
				// System.out.println("Action: " + a + "\tTotalCost: " +
				// childState.getEngery());

			} else if (a == Action.SUCK) {
				if (grid.getDirt().contains(state.getRobotPos())) {
					childState.setAction(a);
					childState.setRobotPos(state.getRobotPos());
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(new ArrayList<Position>(state.getDirtPositions()));
					childState.clean(state.getRobotPos());
					cost.setState(childState);
					childState.setEngery(cost.getCost());
					children.add(new Node(node, childState));
					// System.out.println("Action: " + a + "\tTotalCost: " +
					// childState.getEngery());
				}
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
