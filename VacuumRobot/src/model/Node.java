package model;

/**
 * Class node that has a depth and a parent node.
 * 
 * @author karensaroc
 *
 */
public class Node {

	private int depth;
	private Node parent;
	private State state;
	private String action;

	/**
	 * Creates a node with a parent node and a depth.
	 * 
	 * @param parent
	 *            The parent node. If null node is a root node.
	 */
	public Node(Node parent) {
		this.parent = parent;
		if (parent == null)
			this.depth = 0;
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
	 * Returns the parent node of this node.
	 * 
	 * @return The parent node.
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

	/**
	 * Returns the action taken in this node.
	 * 
	 * @return The action taken in this node.
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action taken in this node.
	 * 
	 * @param action
	 *            The new action taken in this node.
	 */
	public void setAction(String action) {
		this.action = action;
	}

}
