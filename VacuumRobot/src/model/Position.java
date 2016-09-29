package model;

/**
 * Class that creates a position with components X and Y;
 * 
 * @author karensaroc
 *
 */
public class Position {

	/**
	 * The component X of the position.
	 */
	private int x;

	/**
	 * The component Y of the position.
	 */
	private int y;

	/**
	 * The position represented as an array of 2 integers.
	 */
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

}
