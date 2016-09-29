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

}
