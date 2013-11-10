package edu.stanford.scs.difc;

/**
 *
 * An abstract class describing the operations that can be performed on any
 * Label.
 * @author alevy
 *
 */
public abstract class Label<L extends Label<L>> {
  
	/**
	 * Can-flow-to relation (&#8849;). An entity labeled l<sub>1</sub> should
	 * be allowed to affect an entity l<sub>2</sub> only if
	 * l<sub>1</sub>.canFlowTo(l<sub>2</sub>). This relation on labels is at
	 * least a partial order, and satisfies the following laws:
	 * <ol>
	 * <li>Reflexivity: l<sub>1</sub>.canFlowTo(l<sub>1</sub>) for any
	 * l<sub>1</sub>.</li>
     * <li>Antisymmetry: If l<sub>1</sub>.canFlowTo(l<sub>2</sub>) and
     * l<sub>2</sub>.canFlowTo(l<sub>1</sub>) then
     * l<sub>1</sub>.equals(l<sub>2</sub>).</li>
     * <li>Transitivity: If l<sub>1</sub>.canFlowTo(l<sub>2</sub>) and
     * l<sub>2</sub>.canFlowTo(l<sub>3</sub>) then
     * l<sub>1</sub>.canFlowTo(l<sub>3</sub>).</li>
     * </ol>
	 */
	public abstract boolean canFlowTo(L otherLabel);

	/**
	 * Compute the least upper bound, or join, of two labels. When data
	 * carrying two different labels is mixed together in a document, the least
	 * upper bound of the two labels is the lowest safe value with which to
	 * label the result.
	 * <br/>
	 * More formally, for any two labels l1 and l2, if
	 * l<sub>join</sub> = l<sub>1</sub>.leastUpperBount(l<sub>2</sub>), it must
	 * be that:
	 * <ol>
     * <li> l<sub>1</sub>.canFlowTo(l<sub>join</sub>)</li>
     * <li>l<sub>2</sub>.canFlowTo(l<sub>join</sub>)</li>
     * <li>There is no label l /= l<sub>join</sub> such that
     * l<sub>1</sub>.canFlowTo(l), l<sub>2</sub>.canFlowTo(l), and
     * l.canFlowTo(l<sub>join</sub>).</li>
     * </ol>
     * In other words l<sub>join</sub> is the
     * least element to which both l1 and l2 can flow.
	 */
	public abstract L leastUpperBound(L other);

	/**
	 * Greatest lower bound, or meet, of two labels. For any two labels
	 * l<sub>1</sub> and l<sub>2</sub>, if
	 * l<sub>meet</sub> = l<sub>1</sub>.greatestLowerBound(l<sub>2</sub>), it
	 * must be that:
	 * <br/>
	 * <ol>
     * <li>l<sub>meet</sub>.canFlowTo(l<sub>1</sub>)</li>
     * <li>l<sub>meet</sub>.canFlowTo(l<sub>2</sub>)</li>
     * <li>There is no label l != l<sub>meet</sub> such that
     * l.canFlowTo(l<sub>1</sub>), l.canFlowTo(l<sub>2</sub>), and
     * l<sub>meet</sub>.canFlowTo(l).</li>
     * </ol>
     * In other words l<sub>meet</sub> is the
     * greatest element flowing to both l<sub>1</sub> and l<sub>2</sub>. 
	 */
	public abstract L greatestLowerBound(L other);
  
	/**
	 * Alias for leastUpperBound
	 */
	public final L lub(L other) {
		return this.leastUpperBound(other);
	}
  
	/**
	 * Alias for greatestLowerBound
	 */
	public final L glb(L other) {
		return this.greatestLowerBound(other);
	}
}

