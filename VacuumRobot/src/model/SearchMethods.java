package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SearchMethods {

	private Node startNode;
	private final int goalStateDirt = 0;

	public SearchMethods(Grid grid) {
		State startState = new State(null, grid.getStartPosition(), grid.getStartOrientation(), grid.getDirt().length);
		startNode = new Node(null, startState);
	}

	public Node DFS() {
		Node result = null;
		List<Node> closed = new ArrayList<>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(startNode);
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.pop();
			if (currentNode.getState().getAmountOfDirt() == goalStateDirt) {
				result = currentNode;
				break;
			} else {
				List<Node> children = getSuccessorDFS(currentNode);
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

	public void BFS() {
		// TODO Auto-generated method stub
	}

	public void AStar() {
		// TODO Auto-generated method stub
	}

	public List<Node> getSuccessorDFS(Node node) {
		List<Node> children = new ArrayList<>();

		return children;
	}

}
