package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SearchMethods {

	private Grid grid;
	private Node startNode;

	public SearchMethods(Grid grid) {
		this.grid = grid;
		State startState = new State(null, grid.getStartPosition(), grid.getStartOrientation(), grid.getDirt());
		startNode = new Node(null, startState);
	}

	public Node DFS() {
		Node result = null;
		List<Node> closed = new ArrayList<>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(startNode);
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.pop();
			if (currentNode.getState().getDirtPositions().isEmpty()) {
				result = currentNode;
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
		return result;
	}

	public Node BFS() {
		// TODO Auto-generated method stub
		return null;
	}

	public Node AStar() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Node> getSuccessorsDFS(Node node) {
		List<Node> children = new ArrayList<>();
		State state = node.getState();

		List<Action> actions = Action.getActions();
		for (Action a : actions) {
			State childState = new State();

			if (a == Action.LEFT) {
				childState.setAction(a);
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.LEFT));
				childState.setDirtPositions(state.getDirtPositions());
				children.add(new Node(node, childState));
			} else if (a == Action.MOVE) {
				Position newPosition = state.getRobotPos().showPositionMoving(state.getOrientation());
				if (grid.isPositionAllowed(newPosition)) {
					childState.setAction(a);
					childState.setRobotPos(newPosition);
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(state.getDirtPositions());
					children.add(new Node(node, childState));
				}
			} else if (a == Action.RIGHT) {
				childState.setAction(a);
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.RIGHT));
				childState.setDirtPositions(state.getDirtPositions());
				children.add(new Node(node, childState));
			} else if (a == Action.SUCK) {
				if (grid.getDirt().contains(state.getRobotPos())) {
					childState.setAction(a);
					childState.setRobotPos(state.getRobotPos());
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(new ArrayList<>(state.getDirtPositions()));
					childState.clean(state.getRobotPos());
					children.add(new Node(node, childState));
				}
			}
		}
		return children;
	}

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
