package model;

/**
 * Creates a search solution with the specified search method, a solution node,
 * a maximum depth, and a time duration for that solution.
 * 
 * @author karensaroc
 *
 */
public class SearchSolution {

	private String searchMethod;
	private Node solutionNode;
	private long duration;
	private long startTime;
	private int maxDepth;

	/**
	 * Creates a solution with the specified search method.
	 * 
	 * @param searchMethod
	 *            The specified search method.
	 */
	public SearchSolution(String searchMethod) {
		this.startTime = System.nanoTime();
		this.searchMethod = searchMethod;
	}

	/**
	 * Returns the search method used for this solution.
	 * 
	 * @return The search method used for this solution.
	 */
	public String getSearchMethod() {
		return searchMethod;
	}

	/**
	 * Returns the solution node using the search method specified.
	 * 
	 * @return The solution node using the search method specified or null if no
	 *         solution was found.
	 */
	public Node getSolutionNode() {
		return solutionNode;
	}

	/**
	 * Sets the solution node.
	 * 
	 * @param solution
	 *            The new solution node.
	 */
	public void setSolutionNode(Node solution) {
		this.solutionNode = solution;
		duration = (System.nanoTime() - startTime) / 1000000;
	}

	/**
	 * Returns the duration time in milliseconds for finding this solution.
	 * 
	 * @return The duration time in milliseconds for finding this solution.
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Returns the max depth reached to find this solution.
	 * 
	 * @return The max depth reached to find this solution.
	 */
	public int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * Sets the new max depth if the new one is greater than the current one.
	 * 
	 * @param maxDepth
	 *            The new max depth.
	 */
	public void setMaxDepth(int maxDepth) {
		if (this.maxDepth < maxDepth)
			this.maxDepth = maxDepth;
	}
}
