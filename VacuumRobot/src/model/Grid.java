package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Grid that creates a grid with many positions, obstacles, dirt, and the
 * initial position and orientation of the robot.
 * 
 * @author karensaroc
 *
 */
public class Grid {

	private int columns;
	private int lines;
	private List<Position> obstacles;
	private List<Position> dirt;
	private List<Position> freePositions;
	private Position startPosition;
	private Orientation startOrientation;

	/**
	 * Creates a grid with columns, lines, obstacles, dirt, and the initial
	 * position and orientation of the robot.
	 * 
	 * @param columns
	 *            The number of columns in the grid.
	 * @param lines
	 *            The number of lines in the grid.
	 * @param obstacles
	 *            The list with the positions of the obstacles.
	 * @param dirt
	 *            The list with the positions of the dirt.
	 * @param startPosition
	 *            The start position of the robot.
	 * @param startOrientation
	 *            The start orientation of the robot.
	 * @throws Exception
	 *             If one of the positions entered is not valid in this grid.
	 * 
	 */
	public Grid(int columns, int lines, List<Position> obstacles, List<Position> dirt, Position startPosition,
			Orientation startOrientation) throws Exception {
		this.columns = columns;
		this.lines = lines;
		testPositions(obstacles);
		this.obstacles = obstacles;
		testPositions(dirt);
		this.dirt = dirt;
		this.freePositions = generateFreePositions();
		testPosition(startPosition);
		this.startPosition = startPosition;
		this.startOrientation = startOrientation;
	}

	/**
	 * Returns the number of columns in the grid.
	 * 
	 * @return The number of columns in the grid.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Sets the number of columns in the grid.
	 * 
	 * @param columns
	 *            The new number of columns in the grid.
	 * @throws Exception
	 *             If the positions for the obstacles, dirt and start positions
	 *             are not inside the new number of columns.
	 */
	public void setColumns(int columns) throws Exception {
		this.columns = columns;
		testPositions(this.obstacles);
		testPositions(this.dirt);
		testPosition(startPosition);
	}

	/**
	 * Returns the number of lines in the grid.
	 * 
	 * @return The number of lines in the grid.
	 */
	public int getLines() {
		return lines;
	}

	/**
	 * Sets the number of lines in the grid.
	 * 
	 * @param lines
	 *            The new number of lines in the grid.
	 * @throws Exception
	 *             If the positions for the obstacles, dirt and start positions
	 *             are not inside the new number of lines.
	 */
	public void setLines(int lines) throws Exception {
		this.lines = lines;
		testPositions(this.obstacles);
		testPositions(this.dirt);
		testPosition(startPosition);
	}

	/**
	 * Returns the list of all positions with obstacles in the grid.
	 * 
	 * @return The list of all positions with obstacles in the grid.
	 */
	public List<Position> getObstacles() {
		return obstacles;
	}

	/**
	 * Sets the list of positions with obstacles in the grid.
	 * 
	 * @param obstacles
	 *            The new list of positions with obstacles in the grid.
	 * @throws Exception
	 *             If the new obstacles are not inside the grid specification.
	 */
	public void setObstacles(List<Position> obstacles) throws Exception {
		testPositions(obstacles);
		this.obstacles = obstacles;
	}

	/**
	 * Returns the list of all positions with dirt in the grid.
	 * 
	 * @return The list of all positions with dirt in the grid.
	 */
	public List<Position> getDirt() {
		return dirt;
	}

	/**
	 * Sets the list of positions with dirt in the grid.
	 * 
	 * @param dirt
	 *            The new list of positions with dirt in the grid.
	 * @throws Exception
	 *             If the positions of the dirt are not inside the grid
	 *             specifications.
	 */
	public void setDirt(List<Position> dirt) throws Exception {
		testPositions(dirt);
		this.dirt = dirt;
	}

	/**
	 * Returns the start position of the robot.
	 * 
	 * @return The start position of the robot.
	 */
	public Position getStartPosition() {
		return startPosition;
	}

	/**
	 * Sets the start position of the robot.
	 * 
	 * @param startPosition
	 *            The new start position of the robot.
	 * @throws Exception
	 *             If the new start position is not inside the grid
	 *             specifications.
	 */
	public void setStartPosition(Position startPosition) throws Exception {
		testPosition(startPosition);
		this.startPosition = startPosition;
	}

	/**
	 * Returns the start orientation of the robot.
	 * 
	 * @return The start orientation of the robot.
	 */
	public Orientation getStartOrientation() {
		return startOrientation;
	}

	/**
	 * Sets the start orientation of the robot.
	 * 
	 * @param startOrientation
	 *            The new start orientation of the robot.
	 */
	public void setStartOrientation(Orientation startOrientation) {
		this.startOrientation = startOrientation;
	}

	/**
	 * Returns if a position is allowed or not for the robot in the grid.
	 * 
	 * @param position
	 *            The position to be tested.
	 * @return True if the position is inside the grid and it's not an obstacle
	 *         or false otherwise.
	 */
	public boolean isPositionAllowed(Position position) {
		return freePositions.contains(position);
	}

	/**
	 * Returns the longest possible distance inside the grid.
	 * 
	 * @return The longest possible distance inside the grid.
	 */
	public int getPossibleLongestDistance() {
		return lines + columns - 2;
	}

	/**
	 * Generate a list with all the positions in the grid;
	 * 
	 * @return The list with all the positions in the grid;
	 */
	private List<Position> generateFreePositions() {
		List<Position> positions = new ArrayList<>();
		for (int i = 1; i <= columns; i++) {
			for (int j = 1; j <= lines; j++) {
				Position pos = new Position(i, j);
				if (!getObstacles().contains(pos)) {
					positions.add(pos);
				}
			}
		}
		return positions;
	}

	/**
	 * Tests if the positions in the list are inside the grid.
	 * 
	 * @param positions
	 *            The list of positions.
	 * @throws Exception
	 *             If one of the positions is not in the grid.
	 */
	private void testPositions(List<Position> positions) throws Exception {
		for (Position p : positions) {
			if (p.getX() > getColumns() || p.getY() > getLines()) {
				throw new Exception("This position is invalid for this grid");
			}
		}
	}

	/**
	 * Tests if a position is inside the grid.
	 * 
	 * @param p
	 *            The position.
	 * @throws Exception
	 *             If the position is not inside the grid.
	 */
	private void testPosition(Position p) throws Exception {
		if (p.getX() > getColumns() || p.getY() > getLines()) {
			throw new Exception("This position is invalid for this grid");
		}
	}
}
