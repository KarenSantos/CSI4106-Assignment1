package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Grid that creates a grid with many positions, obstacles and dirt.
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

	/**
	 * Creates a grid with columns, lines, obstacles and dirt.
	 * 
	 * @param columns
	 *            The number of columns in the grid.
	 * @param lines
	 *            The number of lines in the grid.
	 * @param obstacles
	 *            The positions of the obstacles.
	 * @param dirt
	 *            The positions of the dirt.
	 */
	public Grid(int columns, int lines, Position[] obstacles, Position[] dirt) {
		this.columns = columns;
		this.lines = lines;
		this.obstacles = obstacles;
		this.dirt = dirt;
		this.positions = generatePositions();
	}
	
	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}



	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}



	public Position[] getObstacles() {
		return obstacles;
	}

	public void setObstacles(Position[] obstacles) {
		this.obstacles = obstacles;
	}



	public Position[] getDirt() {
		return dirt;
	}

	public void setDirt(Position[] dirt) {
		this.dirt = dirt;
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
