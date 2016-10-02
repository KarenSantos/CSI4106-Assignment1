package model;

/**
 * Class node that has a depth, a parent node, and a state.
 * 
 * @author karensaroc
 *
 */
public class Node {

	private Node parent;
	private int depth;
	private State state;

	/**
	 * Creates a node with a parent node, a depth, and a state.
	 * 
	 * @param parent
	 *            The parent node. If null node is a root node.
	 * @param state
	 *            The state of the node.
	 */
	public Node(Node parent, State state) {
		this.parent = parent;
		if (parent == null)
			this.depth = 1;
		else
			this.depth = parent.getDepth() + 1;
	}

	/**
	 * Returns the depth of the node.
	 * 
	 * @return The depth of the node.
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Returns the parent node of this node, or null if the node is a root.
	 * 
	 * @return The parent node or null if node is a root.
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Sets the parent node of this node.
	 * 
	 * @param parent
	 *            The new parent node.
	 */
	public void setParent(Node parent) {
		this.parent = parent;
		this.depth = parent.getDepth() + 1;
	}

	/**
	 * Returns the state this node.
	 * 
	 * @return The state of this node.
	 */
	public State getState() {
		return state;
	}

	/**
	 * Sets the state of this node.
	 * 
	 * @param state
	 *            The new state of this node.
	 */
	public void setState(State state) {
		this.state = state;
	}

}
