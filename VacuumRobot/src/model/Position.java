package model;

import java.util.Comparator;

/**
 * Class that creates a position with components X and Y;
 * 
 * @author karensaroc
 *
 */
public class Position implements Comparable<Position> {

	private int x;
	private int y;
	private int[] position;

	/**
	 * Creates a position with a value for X and a value for Y.
	 * 
	 * @param x
	 *            The X component of the position.
	 * @param y
	 *            The Y component of the position.
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		position = new int[2];
		position[0] = x;
		position[1] = y;
	}

	/**
	 * Returns the X component of the position.
	 * 
	 * @return The X component of the position.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the X component of the position.
	 * 
	 * @param x
	 *            The new X component of the position.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Returns the Y component of the position.
	 * 
	 * @return The Y component of the position.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the Y component of the position.
	 * 
	 * @param Y
	 *            The new Y component of the position.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Returns the position moving to the specified orientation of this current
	 * position.
	 * 
	 * @param orientation
	 *            The orientation where to show the next position.
	 * @return The position moving to the specified orientation or null if
	 *         orientation not specified.
	 */
	public Position showPositionMoving(Orientation orientation) {
		Position newPosition = null;
		if (orientation == Orientation.WEST) {
			newPosition = new Position(getX() - 1, getY());
		} else if (orientation == Orientation.NORTH) {
			newPosition = new Position(getX(), getY() - 1);
		} else if (orientation == Orientation.EAST) {
			newPosition = new Position(getX() + 1, getY());
		} else if (orientation == Orientation.SOUTH) {
			newPosition = new Position(getX(), getY() + 1);
		}
		return newPosition;
	}

	/**
	 * Returns the position as an array of 2 integers.
	 * 
	 * @return The position.
	 */
	public int[] getPosition() {
		return position;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof Position) {
			Position p = (Position) obj;
			if (p.getX() == this.getX() && p.getY() == this.getY()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Compares two positions by finding the different between x+y of one and
	 * x+y of the other.
	 */
	@Override
	public int compareTo(Position o) {
		// for calculating the minimum distance between robot and dirt
		return Math.abs(this.x) + Math.abs(this.y) - (Math.abs(o.x) + Math.abs(o.y));
	}

	/**
	 * Comparator that compares two positions by finding the different between
	 * x+y of the first and x+y of the second.
	 */
	public static Comparator<Position> positionComparator = new Comparator<Position>() {
		public int compare(Position o1, Position o2) {
			return o1.compareTo(o2);
		}
	};

}
