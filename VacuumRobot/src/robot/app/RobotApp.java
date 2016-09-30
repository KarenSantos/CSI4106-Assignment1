package robot.app;

import model.Grid;
import model.Orientation;
import model.Position;
import model.SearchMethods;

public class RobotApp {

	public static void main(String[] args) {

		Grid grid = generateGrid(4, 4, getPositions("2,2/2,3/3,2)"), getPositions("1,2/2,1/2,4/3,3)"),
				new Position(4, 3), Orientation.WEST);

		search(1, grid);

		printSolution();

	}

	private static void printSolution() {

	}

	private static void search(int method, Grid grid) {
		SearchMethods search = new SearchMethods(grid);
		if (method == 1) {
			search.DFS();
		} else if (method == 2) {
			search.BFS();
		} else if (method == 3) {
			search.AStar();
		}

	}

	private static Grid generateGrid(int columns, int lines, Position[] obstacles, Position[] dirt,
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
	private static Position[] getPositions(String positionsString) {
		Position[] positions = null;
		if (positionsString.length() > 0) {
			String[] pos = positionsString.split("/");
			positions = new Position[pos.length];
			for (int i = 0; i < pos.length; i++) {
				try {
					int x = Integer.parseInt(pos[i].split(",")[0]);
					int y = Integer.parseInt(pos[i].split(",")[1]);
					positions[i] = new Position(x, y);
				} catch (Exception e) {
				}
			}
		}
		return positions;
	}

}
