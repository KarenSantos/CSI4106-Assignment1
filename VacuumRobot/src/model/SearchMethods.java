package model;

import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SearchMethods {

	private Grid grid;
	private Node startNode;

	public SearchMethods(Grid grid) {
		this.grid = grid;
		State startState = new State(Action.START, grid.getStartPosition(), grid.getStartOrientation(), grid.getDirt());
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
	Node result = null;
	Queue<Node> fringe = new LinkedList<Node>();
	List<Node> closed = new ArrayList<>();
	fringe.offer(startNode);

	while (!fringe.isEmpty()) {
		Node currentNode = fringe.poll();
		if (currentNode.getState().getDirtPositions().isEmpty()) {
			result = currentNode;
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
	return result;
	}

	public Node AStar() {
		// TODO Auto-generated method stub

		Node result = null;
		List<Node> closed = new ArrayList<>();
	    PriorityQueue<Node> fringe = new PriorityQueue<Node>(10,new Comparator<Node>() {
	        public int compare(Node n1, Node n2) {
	        		if(n1.getState().getEngery()<=n2.getState().getEngery()) 
	        			return -1;
	        		return 1;
	        		
	        	
	        }
	    });
	    

	    
	    fringe.offer(startNode);
	    while (!fringe.isEmpty())
	    {
	    		Node currentNode = fringe.poll();
	    		System.out.println(currentNode.getState().toString()+"\t"+currentNode.getState().getEngery());
	    	
			if (currentNode.getState().getDirtPositions().isEmpty()) {
				result = currentNode;
				break;
			} else {
				List<Node> children = getSuccessorsDFS(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if(!closed.contains(n))fringe.offer(n);
					
				}
			}
	    }
	    return result;
	}

	private List<Node> getSuccessorsDFS(Node node) {
		List<Node> children = new ArrayList<>();
		State state = node.getState();

		List<Action> actions = Action.getActions();
		for (Action a : actions) {
			State childState = new State();
			int engery=node.getState().getEngery();
			if (a == Action.LEFT) {
				
				childState.setEngery(engery+a.getEngery());
				
				childState.setAction(a);
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.LEFT));
				childState.setDirtPositions(state.getDirtPositions());
				children.add(new Node(node, childState));
			} else if (a == Action.MOVE) {
				Position newPosition = state.getRobotPos().showPositionMoving(state.getOrientation());
				if (grid.isPositionAllowed(newPosition)) {
					childState.setAction(a);
					childState.setEngery(engery+a.getEngery());
					childState.setRobotPos(newPosition);
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(state.getDirtPositions());
					children.add(new Node(node, childState));
				}
			} else if (a == Action.RIGHT) {
				childState.setAction(a);
				childState.setEngery(engery+a.getEngery());
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.RIGHT));
				childState.setDirtPositions(state.getDirtPositions());
				children.add(new Node(node, childState));
			} else if (a == Action.SUCK) {
				if (grid.getDirt().contains(state.getRobotPos())) {
					childState.setAction(a);
					childState.setEngery(engery+a.getEngery());
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
