package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SearchMethods {

	private Grid grid;
	private Node startNode;
	private Node goalNode;

	public SearchMethods(Grid grid) {
		this.grid = grid;
		// startNode = new Node(null, )
		// TODO generate start and goal node to suppy the searches.
	}

	public Node DFS() {
		Node result = null;
		List<Node> closed = new ArrayList<>();
		Stack<Node> fringe = new Stack<Node>();
		fringe.push(startNode);
		while (!fringe.isEmpty()) {
			Node currentNode = fringe.pop();
			if (currentNode.getState() == goalNode.getState()) {
				result = currentNode;
				break;
			} else {
				List<Node> children = getSuccessorDFS(currentNode);
				closed.add(currentNode);
				for (Node n : children) {
					if (!closed.contains(n) && !fringe.contains(n)){
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
