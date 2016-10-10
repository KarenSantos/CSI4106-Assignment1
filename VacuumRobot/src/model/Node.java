package model;

/**
 * Class node that has a parent node, a depth, a state, an action, a cumulative
 * cost and a function cost.
 * 
 * @author karensaroc
 *
 */
public class Node implements Comparable<Node> {

	private Node parent;
	private int depth;
	private State state;
	private Action action;
	private int cumulativeCost;
	private int functionCost;

	/**
	 * Creates a node with a parent node, a depth, a state, an action, and a
	 * cumulative cost from the parent node plus the action energy. The function
	 * cost is initialized as zero.
	 * 
	 * @param parent
	 *            The parent node. If null node is a root node.
	 * @param state
	 *            The state of the node.
	 * @param action
	 *            The action taken to get to this node.
	 */
	public Node(Node parent, State state, Action action) {
		this.parent = parent;
		this.state = state;
		this.action = action;
		this.functionCost = 0;
		setParent(parent);
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
	 * Sets the parent node of this node, updating the depth and cumulative cost
	 * accordingly.
	 * 
	 * @param parent
	 *            The new parent node.
	 */
	public void setParent(Node parent) {
		if (parent == null) {
			this.depth = 1;
			this.cumulativeCost = 0;
		} else {
			this.depth = parent.getDepth() + 1;
			this.cumulativeCost = parent.getCumulativeCost() + action.getEngery();
		}
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
	 * Returns the action taken on the previous state to get to the current
	 * state.
	 * 
	 * @return The action taken on the previous state to get to the current
	 *         state.
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the action taken on the previous state to get to the current state.
	 * 
	 * @param action
	 *            The action taken on the previous state to get to the current
	 *            state.
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * Returns the cumulative cost from the root until this node.
	 * 
	 * @return The cumulative cost from the root until this node.
	 */
	public int getCumulativeCost() {
		return cumulativeCost;
	}

	/**
	 * Returns the node's function cost.
	 * 
	 * @return The node's function cost.
	 */
	public int getFunctionCost() {
		return functionCost;
	}

	/**
	 * Sets the node's function cost.
	 * 
	 * @param functionCost
	 *            The new function cost.
	 */
	public void setFunctionCost(int functionCost) {
		this.functionCost = functionCost;
	}

	/**
	 * Returns a string with the state and the action of the node.
	 */
	@Override
	public String toString() {
		return state.toString() + ", " + this.action;
	}

	/**
	 * Two nodes are equal if they have the same state.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null && obj instanceof Node) {
			Node n = (Node) obj;
			if (n.getState().equals(this.getState())) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * Compares a node with this node according to the node's function cost.
	 */
	@Override
	public int compareTo(Node o) {
		int result = 0;
		if (this.getFunctionCost() <= o.getFunctionCost())
			result = -1;
		else
			result = 1;
		return result;
	}
}
