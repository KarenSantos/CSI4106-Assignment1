package model;

import java.util.ArrayList;
import java.util.Collections;
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
				
				
				
				childState.setAction(a);
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.LEFT));
				childState.setDirtPositions(state.getDirtPositions());
				childState.setEngery(engery+heuristic(childState)+a.getEngery());
				children.add(new Node(node, childState));
				System.out.println("Action: "+a+"\tTotalCost: "+childState.getEngery());
				
			} else if (a == Action.MOVE) {
				Position newPosition = state.getRobotPos().showPositionMoving(state.getOrientation());
				if (grid.isPositionAllowed(newPosition)) {
					childState.setAction(a);
					
					childState.setRobotPos(newPosition);
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(state.getDirtPositions());
					childState.setEngery(engery+heuristic(childState)+a.getEngery());
					children.add(new Node(node, childState));
					System.out.println("Action: "+a+"\tTotalCost: "+childState.getEngery());
				}
			} else if (a == Action.RIGHT) {
				childState.setAction(a);
				
				childState.setRobotPos(state.getRobotPos());
				childState.setOrientation(getNewOrientation(state.getOrientation(), Action.RIGHT));
				childState.setDirtPositions(state.getDirtPositions());
				childState.setEngery(engery+heuristic(childState)+a.getEngery());
				children.add(new Node(node, childState));
				System.out.println("Action: "+a+"\tTotalCost: "+childState.getEngery());
			} else if (a == Action.SUCK) {
				if (grid.getDirt().contains(state.getRobotPos())) {
					childState.setAction(a);
//					childState.setEngery(engery+a.getEngery());
					childState.setRobotPos(state.getRobotPos());
					childState.setOrientation(state.getOrientation());
					childState.setDirtPositions(new ArrayList<>(state.getDirtPositions()));
					childState.clean(state.getRobotPos());
					childState.setEngery(engery+heuristic(childState)+a.getEngery());
					children.add(new Node(node, childState));
					System.out.println("Action: "+a+"\tTotalCost: "+childState.getEngery());
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
	
	private int heuristic(State current){
		List<Position>dirts=current.getDirtPositions();
		List<Position> distances = new ArrayList<>();
		Position robotPosition=current.getRobotPos();
		Orientation currentOrientation=current.getOrientation();
		Position shortest;
		int index=-1;
		int numOfObs=0;
		for(Position dirt : dirts){
			distances.add(new Position(dirt.getX()-robotPosition.getX(),dirt.getY()-robotPosition.getY()));
		}
		Collections.sort(distances, Position.positionComparator);
		for(Position dis : distances){
			if(currentOrientation==Orientation.NORTH){
				if(dis.getX()>=0){
					index=distances.indexOf(dis);
					break;
				}
			}
			else if(currentOrientation==Orientation.SOUTH){
				if(dis.getX()<=0){
					index=distances.indexOf(dis);
					break;
				}
			}
			else if(currentOrientation==Orientation.WEST){
				if(dis.getY()<=0){
					index=distances.indexOf(dis);
					break;
				}
			}
			else {
				if(dis.getY()>=0){
					index=distances.indexOf(dis);
					break;
				}
			}
		}
		if(dirts.isEmpty()) return 0;
		if(index==-1){
			shortest=distances.get(distances.size()-1);
			
		}
		else{
			shortest=distances.get(index);
		}
		
		
			
			int i=1;
			
			for(int x=shortest.getX();x!=0;){
				if(shortest.getX()>0){
					if(!grid.isPositionAllowed(new Position(robotPosition.getX()-i,robotPosition.getY())))
						numOfObs++;
					x--;
				}
				else{
					if(!grid.isPositionAllowed(new Position(robotPosition.getX()+i,robotPosition.getY())))
						numOfObs++;
					x++;
				}
				
				
			}
			
			for(int y=shortest.getY();y!=0;){
				if(shortest.getY()>0){
					if(!grid.isPositionAllowed(new Position(robotPosition.getX(),robotPosition.getY()-i)))
						numOfObs++;
					y--;
				}
				else{
					if(!grid.isPositionAllowed(new Position(robotPosition.getX(),robotPosition.getY()+i)))
						numOfObs++;
					y++;
				}
				
				
			}
			if(index==-1){
				System.out.print("  -1numOBj : "+numOfObs+"\t"+Action.MOVE.getEngery()+"\t"+shortest.toString()+"\t"+((Math.abs(shortest.getX())+Math.abs(shortest.getY())+numOfObs)*Action.MOVE.getEngery()+5)+"\t");
				return ((Math.abs(shortest.getX())+Math.abs(shortest.getY())+numOfObs)*Action.MOVE.getEngery()+5);
			}
			else{
				System.out.print("  not-1numOBj : "+numOfObs+"\t"+Action.MOVE.getEngery()+"\t"+shortest.toString()+"\t"+((Math.abs(shortest.getX())+Math.abs(shortest.getY())+numOfObs)*Action.MOVE.getEngery())+"\t");
			return ((Math.abs(shortest.getX())+Math.abs(shortest.getY())+numOfObs)*Action.MOVE.getEngery());
			}
		
			
	}

}
