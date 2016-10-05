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

	private Grid grid;
	private Node startNode;
	private boolean activeAStar;

	/**
	 * Creates a search method with a specified grid.
	 * 
	 * @param grid
	 *            The grid to execute the search method.
	 */
	public SearchMethods(Grid grid) {
		this.grid = grid;
		State startState = new State(Action.START, grid.getStartPosition(), grid.getStartOrientation(), grid.getDirt());
		startNode = new Node(null, startState);
		this.activeAStar = false;
	}

	/**
	 * Returns a search solution for the specified grid with the Depth-first
	 * search method.
	 * 
	 * @return A search solution using Depth-first search method.
	 */
	public SearchSolution DFS() {
		activeAStar = false;
		SearchSolution solution = new SearchSolution(DFS);
		List<Node> closed = new ArrayList<>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(startNode);
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.pop();
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
		return solution;
	}

	/**
	 * Returns a search solution for the specified grid with the Breadth-first
	 * search method.
	 * 
	 * @return A search solution using Breadth-first search method.
	 */
	public SearchSolution BFS() {
		activeAStar = false;
		SearchSolution solution = new SearchSolution(BFS);
		Queue<Node> fringe = new LinkedList<Node>();
		List<Node> closed = new ArrayList<>();
		fringe.offer(startNode);

		while (!fringe.isEmpty()) {
			Node currentNode = fringe.poll();
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
		return solution;
	}

	/**
	 * Returns a search solution for the specified grid with the A* search
	 * method.
	 * 
	 * @return A search solution using A* search method.
	 */
	public SearchSolution AStar() {
		activeAStar = true;
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
			System.out.println(currentNode.getState().toString() + "\t" + currentNode.getState().getEngery());

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

		List<Action> actions = Action.getActions();
		for (Action a : actions) {
			State childState = new State();
			int engery = node.getState().getEngery();
			if (a == Action.LEFT) {

				childState.setAction(a);
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.LEFT));
				childState.setDirtPositions(state.getDirtPositions());
				childState.setEngery(engery + heuristic(childState) + a.getEngery());
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
					childState.setEngery(engery + heuristic(childState) + a.getEngery());
					children.add(new Node(node, childState));
					// System.out.println("Action: " + a + "\tTotalCost: " +
					// childState.getEngery());
				}
			} else if (a == Action.RIGHT) {
				childState.setAction(a);

				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.RIGHT));
				childState.setDirtPositions(state.getDirtPositions());
				childState.setEngery(engery + heuristic(childState) + a.getEngery());
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
					childState.setEngery(engery + heuristic(childState) + a.getEngery());
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

	/**
	 * Calculates the heuristic to be used for the A* search method in a current
	 * state if this has been used or 0 otherwise.
	 * 
	 * @param currentState
	 *            The current state.
	 * @return The integer value of the heuristic.
	 */
	private int heuristic(State currentState) {
		int heuristicValue = 0;
		if (activeAStar) {

			List<Position> dirts = currentState.getDirtPositions();

			if (dirts.isEmpty()) {
				heuristicValue = 0;
			} else {
				List<Position> distances = new ArrayList<>();
				Position robotPosition = currentState.getRobotPos();
				Orientation currentOrientation = currentState.getOrientation();
				Position shortest;
				int index = -1;
				int numOfObs = 0;
				// calculating the distances between dirt and robot without
				// considering obstacles.
				for (Position dirt : dirts) {
					distances.add(new Position(dirt.getX() - robotPosition.getX(), dirt.getY() - robotPosition.getY()));
				}
				Collections.sort(distances, Position.positionComparator);
				// getting the nearest dirt according to the orientation.
				for (Position dis : distances) {
					if (currentOrientation == Orientation.NORTH) {
						if (dis.getY() >= 0) {
							index = distances.indexOf(dis);
							break;
						}
					} else if (currentOrientation == Orientation.SOUTH) {
						if (dis.getY() <= 0) {
							index = distances.indexOf(dis);
							break;
						}
					} else if (currentOrientation == Orientation.WEST) {
						if (dis.getX() <= 0) {
							index = distances.indexOf(dis);
							break;
						}
					} else {
						if (dis.getX() >= 0) {
							index = distances.indexOf(dis);
							break;
						}
					}
				}

				// Could not find a nearest dirt in that orientation then we use
				// the longest distance
				if (index == -1) {
					shortest = distances.get(distances.size() - 1);
				} else {
					shortest = distances.get(index);
				}

				// finding the obstacles in that path to increase the cost
				for (int x = shortest.getX(); x != 0;) {
					if (shortest.getX() > 0) {
						if (!grid.isPositionAllowed(new Position(robotPosition.getX() - 1, robotPosition.getY())))
							numOfObs++;
						x--;
					} else {
						if (!grid.isPositionAllowed(new Position(robotPosition.getX() + 1, robotPosition.getY())))
							numOfObs++;
						x++;
					}
				}
				for (int y = shortest.getY(); y != 0;) {
					if (shortest.getY() > 0) {
						if (!grid.isPositionAllowed(new Position(robotPosition.getX(), robotPosition.getY() - 1)))
							numOfObs++;
						y--;
					} else {
						if (!grid.isPositionAllowed(new Position(robotPosition.getX(), robotPosition.getY() + 1)))
							numOfObs++;
						y++;
					}
				}

				// returning the final cost, increasing each obstacle times the
				// move cost.
				if (index == -1) {
					System.out.print(
							"  -1numOBj : " + numOfObs + "\t" + Action.MOVE.getEngery() + "\t" + shortest.toString()
									+ "\t" + ((Math.abs(shortest.getX()) + Math.abs(shortest.getY()) + numOfObs) // Match.abs
																													// is
																													// absolute
																													// value
																													// of
																													// distance
											* Action.MOVE.getEngery() + 5)
									+ "\t");
					heuristicValue = ((Math.abs(shortest.getX()) + Math.abs(shortest.getY()) + numOfObs)
							* Action.MOVE.getEngery() + 5);
				} else {
					System.out.print(
							"  not-1numOBj : " + numOfObs + "\t" + Action.MOVE.getEngery() + "\t" + shortest.toString()
									+ "\t" + ((Math.abs(shortest.getX()) + Math.abs(shortest.getY()) + numOfObs)
											* Action.MOVE.getEngery())
									+ "\t");
					heuristicValue = ((Math.abs(shortest.getX()) + Math.abs(shortest.getY()) + numOfObs)
							* Action.MOVE.getEngery());
				}
			}
		}
		return heuristicValue;
	}
}
