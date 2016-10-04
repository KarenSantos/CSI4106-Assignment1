package robot.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.Grid;
import model.Node;
import model.Orientation;
import model.Position;
import model.SearchMethods;

public class RobotApp {

	public static void main(String[] args) {


//Grid grid = generateGrid(4, 4, getPositions("2,2/2,3/3,2/4,2/4,3"), getPositions("1,2/2,1/3,3"),
//		new Position(3, 4), Orientation.WEST);
Grid grid = generateGrid(4, 4, getPositions("2,2/2,3/3,2"), getPositions("1,2/2,1/3,3/4,2"),
		new Position(3, 4), Orientation.WEST);

Node solution = search(3, grid);


		printSolution(solution);

	}

	/**
	 * Receives a node solution for a search problem and prints the states of
	 * that solution starting from the root node. If node solution is null
	 * prints "No Solution".
	 * 
	 * @param solution
	 *            The node solution of the search problem.
	 */
	private static void printSolution(Node solution) {
		
		int totalCost=0;
		if (solution != null) {
			System.out.println();
			Stack<Node> allNodes = new Stack<>();
			allNodes.add(solution);
			Node node = solution;
			
			while (node.getParent() != null) {
				totalCost+=node.getState().getAction().getEngery();
				allNodes.add(node.getParent());
				node = node.getParent();
			}
			while (!allNodes.isEmpty()) {
				
				System.out.println(allNodes.pop().getState().toString());
			}
		} else {
			System.out.println("No solution was found.");
			return;
		}
		System.out.println("total cost: "+totalCost);
		System.out.println("depth: "+solution.getDepth());
	}

	/**
	 * Generates a search for the specified grid and the specified search
	 * method.
	 * 
	 * @param method
	 *            The search method integer. 1 for Depth-first search, 2 for
	 *            Breath-first search and 3 for A* search.
	 * @param grid
	 *            The grid where the search will be applied.
	 * @return The node solution for that specified search.
	 */
	private static Node search(int method, Grid grid) {
		SearchMethods search = new SearchMethods(grid);
		Node result = null;
		if (method == 1) {
			result = search.DFS();
		} else if (method == 2) {
			result = search.BFS();
		} else if (method == 3) {
			result = search.AStar();
		}
		return result;
	}

	/**
	 * 
	 * @param columns
	 * @param lines
	 * @param obstacles
	 * @param dirt
	 * @param startPosition
	 * @param orientation
	 * @return
	 */
	private static Grid generateGrid(int columns, int lines, List<Position> obstacles, List<Position> dirt,
			Position startPosition, Orientation orientation) {
		Grid grid = new Grid(columns, lines, obstacles, dirt, startPosition, orientation);
		return grid;
	}

	/**
	 * Returns an array of positions from a string with positions separated by /
	 * and each position separated by commas. Example of string: "1,2/3,2/5,6"
	 * 
	 * @param positionsString
	 *            The string with the positions separated by / and each position
	 *            separated with commas.
	 * @return The array of positions or null if no positions were identified in
	 *         the string.
	 */
	private static List<Position> getPositions(String positionsString) {
		List<Position> positions = new ArrayList<>();
		if (positionsString.length() > 0) {
			String[] pos = positionsString.split("/");
			for (int i = 0; i < pos.length; i++) {
				try {
					int x = Integer.parseInt(pos[i].split(",")[0]);
					int y = Integer.parseInt(pos[i].split(",")[1]);
					positions.add(new Position(x, y));
				} catch (Exception e) {
				}
			}
		}
		return positions;
	}

}
