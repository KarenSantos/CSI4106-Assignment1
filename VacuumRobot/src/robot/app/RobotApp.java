package robot.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.Grid;
import model.Node;
import model.Orientation;
import model.Position;
import model.SearchMethods;
import model.SearchSolution;

/**
 * The RobotApp class has a main method that can initializes a grid, search the
 * solution for that grid according to the search method specified, and prints
 * it to the screen.
 * 
 * @author karensaroc
 *
 */
public class RobotApp {

	public static void main(String[] args) {

		// assignment sample grid
//		Grid grid = generateGrid(4, 4, getPositions("2,2/2,3/3,2"), getPositions("1,2/2,1/3,3/2,4"), new Position(4, 3),
//				Orientation.WEST);

		// bigger grid
//		Grid grid = generateGrid(7, 5, getPositions("1,3/3,2/3,3/3,4/6,2/6,3/5,4"), getPositions("2,4/4,3/6,5/7,1"),
//				new Position(1, 1), Orientation.EAST);
		
		// bigger grid
		Grid grid = generateGrid(4, 4, getPositions("3,2/3,3/3,4"), getPositions("1,1/2,4/4,1"),
				new Position(4, 4), Orientation.WEST);

		// No solution grid.
//		 Grid grid = generateGrid(5, 5,
//		 getPositions("2,1/2,2/2,3/2,4/2,5"),
//		 getPositions("1,4"),
//		 new Position(5, 5), Orientation.NORTH);

		SearchSolution solution = search(1, grid);
		printSolution(solution);

		SearchSolution solution2 = search(2, grid);
		printSolution(solution2);

		SearchSolution solution3 = search(3, grid);
		printSolution(solution3);
	}

	/**
	 * Receives a solution for a search problem and prints the states of that
	 * solution starting from the root node, total cost, solution depth, maximum
	 * depth, and time of that solution. If node solution is null prints "No
	 * Solution".
	 * 
	 * @param solution
	 *            The solution for the search problem.
	 */
	private static void printSolution(SearchSolution solution) {
		if (solution.getSolutionNode() != null) {
			System.out.println("------ " + solution.getSearchMethod() + " ------");
			Stack<Node> allNodes = new Stack<>();
			allNodes.add(solution.getSolutionNode());
			Node node = solution.getSolutionNode();
			while (node.getParent() != null) {
				allNodes.add(node.getParent());
				node = node.getParent();
			}
			while (!allNodes.isEmpty()) {
				Node n = allNodes.pop();
				System.out.println(n.toString());
			}
			System.out.println("Total cost: " + solution.getSolutionNode().getCumulativeCost());
			System.out.println("Depth: " + solution.getSolutionNode().getDepth());
			System.out.println("MaxDepth: " + solution.getMaxDepth());
			System.out.println("Time: " + solution.getDuration() + "ms");
			System.out.println();
		} else {
			System.out.println("No solution was found.");
			System.out.println();
		}
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
	private static SearchSolution search(int method, Grid grid) {
		SearchMethods search = new SearchMethods(grid);
		SearchSolution solution = null;
		if (method == 1) {
			solution = search.DFS();
		} else if (method == 2) {
			solution = search.BFS();
		} else if (method == 3) {
			solution = search.AStar();
		}
		return solution;
	}

	/**
	 * Generates a grid with columns, lines, obstacle and dirt positions, a
	 * start position and orientation.
	 * 
	 * @param columns
	 *            The number of columns on the grid.
	 * @param lines
	 *            The number of lines on the grid.
	 * @param obstacles
	 *            The list with the positions of all obstacles.
	 * @param dirt
	 *            The list with the positions of all dirt.
	 * @param startPosition
	 *            The start position of the robot.
	 * @param orientation
	 *            The start orientation of the robot.
	 * @return Returns the grid generated.
	 */
	private static Grid generateGrid(int columns, int lines, List<Position> obstacles, List<Position> dirt,
			Position startPosition, Orientation orientation) {
		Grid grid = null;
		try {
			grid = new Grid(columns, lines, obstacles, dirt, startPosition, orientation);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
