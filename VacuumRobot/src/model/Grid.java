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
	private List<Position> positions;
	private Position[] obstacles;
	private Position[] dirt;
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
	 *            The positions of the obstacles.
	 * @param dirt
	 *            The positions of the dirt.
	 * @param startPosition
	 *            The start position of the robot.
	 * @param startOrientation
	 *            The start orientation of the robot.
	 * 
	 */
	public Grid(int columns, int lines, Position[] obstacles, Position[] dirt, Position startPosition,
			Orientation startOrientation) {
		// TODO test if position of obstacles and dirt are part of the grid.
		this.columns = columns;
		this.lines = lines;
		this.obstacles = obstacles;
		this.dirt = dirt;
		this.positions = generatePositions();
		this.startPosition = startPosition;
		this.startOrientation = startOrientation;
		// TODO maybe record start state instead of position and orientation
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
	 */
	public void setColumns(int columns) {
		// TODO regenerate grid and do all the testing.
		this.columns = columns;
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
	 */
	public void setLines(int lines) {
		// TODO regenerate grid and do all the testing.
		this.lines = lines;
	}

	/**
	 * Returns the list of all positions in the grid.
	 * 
	 * @return The list of all positions in the grid.
	 */
	public List<Position> getPositions() {
		return positions;
	}

	/**
	 * Sets the list of positions in the grid.
	 * 
	 * @param positions
	 *            The new list of positions in the grid.
	 */
	public void setPositions(List<Position> positions) {
		// TODO test if they are a match for the columns, lines and others.
		this.positions = positions;
	}

	/**
	 * Returns the list of all positions with obstacles in the grid.
	 * 
	 * @return The list of all positions with obstacles in the grid.
	 */
	public Position[] getObstacles() {
		return obstacles;
	}

	/**
	 * Sets the list of positions with obstacles in the grid.
	 * 
	 * @param obstacles
	 *            The new list of positions with obstacles in the grid.
	 */
	public void setObstacles(Position[] obstacles) {
		// TODO test if they are according to the grid.
		this.obstacles = obstacles;
	}

	/**
	 * Returns the list of all positions with dirt in the grid.
	 * 
	 * @return The list of all positions with dirt in the grid.
	 */
	public Position[] getDirt() {
		return dirt;
	}

	/**
	 * Sets the list of positions with dirt in the grid.
	 * 
	 * @param dirt
	 *            The new list of positions with dirt in the grid.
	 */
	public void setDirt(Position[] dirt) {
		// TODO test if they are according to the grid.
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
	 */
	public void setStartPosition(Position startPosition) {
		// TODO test if position in the grid.
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
	 * Generate a list with all the positions in the grid;
	 * 
	 * @return The list with all the positions in the grid;
	 */
	private List<Position> generatePositions() {
		List<Position> positions = new ArrayList<>();
		for (int i = 1; i <= columns; i++) {
			for (int j = 1; j <= lines; j++) {
				positions.add(new Position(i, j));
			}
		}
		return positions;
	}

}
