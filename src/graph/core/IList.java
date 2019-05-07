package graph.core;

/**
 * ADT to represent a List data structure. Also known as a "Sequence" data
 * structure.
 */
public interface IList<T> {

	/**
	 * Get the number of elements in the list.
	 * 
	 * @return
	 */
	public int size();

	/**
	 * Check if the list is empty.
	 * 
	 * @return {@code true} if the list is empty, {@code false} otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Get the first node in the list.
	 * 
	 * @return
	 */
	public INode<T> first();

	/**
	 * Get the last node in the list.
	 * 
	 * @return
	 */
	public INode<T> last();

	/**
	 * Get the node before {@code p} in the list.
	 * 
	 * @param p
	 * @return
	 */
	public INode<T> prev(INode<T> p);

	/**
	 * Get the node after {@code p} in the list.
	 * 
	 * @param p
	 * @return
	 */

	public INode<T> next(INode<T> p);

	/**
	 * Insert element {@code e} as the first element of the list. A new node
	 * will be created at the start of the list, which will have {@code e} as
	 * its element. This node will be placed before any existing first node.
	 * 
	 * @param e
	 * @return The node that the new element {@code e} is stored in.
	 */
	public INode<T> insertFirst(T e);

	/**
	 * Insert element {@code e} as the last element of the list. A new node will
	 * be created at the end of the list, which will have {@code e} as its
	 * element. This node will be placed after any existing last node.
	 * 
	 * @param e
	 * @return The node that the new element {@code e} is stored in.
	 */
	public INode<T> insertLast(T e);

	/**
	 * Insert element {@code e} into the list in the position before node
	 * {@code p}. A new node will be created, which will have {@code e} as its
	 * element. This node will be placed before node {@code p}. If {@code p} was
	 * not the first node in the list, the new node will be placed between
	 * {@code p} and the node before it.
	 * 
	 * @param e
	 * @return The node that the new element {@code e} is stored in.
	 */

	public INode<T> insertBefore(INode<T> p, T e);

	/**
	 * Insert element {@code e} into the list in the position after node
	 * {@code p}. A new node will be created, which will have {@code e} as its
	 * element. This node will be placed after node {@code p}. If {@code p} was
	 * not the last node in the list, the new node will be placed between
	 * {@code p} and the node after it.
	 * 
	 * @param e
	 * @return The node that the new element {@code e} is stored in.
	 */
	public INode<T> insertAfter(INode<T> p, T e);

	/**
	 * Replace the element stored in node {@code p} with {@code e}. This does
	 * not affect the number of elements in the list.
	 * 
	 * @param p
	 * @param e
	 * @return The element that was stored in {@code p} before the replacement.
	 */
	public T replace(INode<T> p, T e);

	/**
	 * Remove node {@code p} from the list.
	 * 
	 * @param p
	 * @return The element that was stored in {@code p}.
	 */
	public T remove(INode<T> p);
	
	/**
	 * Get an iterator that can iterate over the list's elements.
	 * @return The iterator.
	 */
	public IIterator<T> iterator();
}