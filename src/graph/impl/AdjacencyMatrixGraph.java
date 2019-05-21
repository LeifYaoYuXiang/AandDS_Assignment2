package graph.impl;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;
import graph.core.IVertex;
import graph.util.DLinkedList;


public class AdjacencyMatrixGraph<V,E> implements IGraph<V,E> {
	/**
	 * Inner class to represent a vertex in an edge list graph implementation
	 */
	private class AdjacencyMatrixVertex implements IVertex<V> {
		// reference to a node in the vertex list
		INode<IVertex<V>> node;

		// element stored in this vertex
		V element;
		
		//index stored in this vertex
		int index;

		public AdjacencyMatrixVertex(V element) {
			this.element = element;
		}

		@Override
		public V element() {
			return element;
		}
		
		public int index() {
			return index;
		}

		// It's useful to have a toString() method that can
		// return details about this object, so you can
		// print the object later and get useful information.
		// This one prints the element
		public String toString() {
			return element.toString();
		}
	}

	/**
	 * Inner class to represent an edge in an edge list graph implementation.
	 *
	 */
	private class AdjacencyMatrixEdge implements IEdge<E> {
		// reference to a node in the edge list
		INode<IEdge<E>> node;

		// element stored in this edge
		E element;

		// the start and end vertices that this edge connects
		AdjacencyMatrixVertex start, end;

		// constructor to set the three fields
		public AdjacencyMatrixEdge(AdjacencyMatrixVertex start, AdjacencyMatrixVertex end, E element) {
			this.start = start;
			this.end = end;
			this.element = element;
		}

		@Override
		public E element() {
			return element;
		}

		public String toString() {
			return element.toString();
		}
	}
	
	//AdjacencyMatrix
	@SuppressWarnings("unchecked")
	private IEdge<E>[][] adjacencyMatrix = new IEdge[0][0];
	
	public IEdge<E>[][] getMatrix() {
		return adjacencyMatrix;
	}
	
	// vertex list
	private IList<IVertex<V>> vertices;

	// edge list
	private IList<IEdge<E>> edges;

	/**
	 * Constructor
	 */
	public AdjacencyMatrixGraph() {
		// create new (empty) lists of edges and vertices
		vertices = new DLinkedList<IVertex<V>>();
		edges = new DLinkedList<IEdge<E>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IVertex<V>[] endVertices(IEdge<E> e) {
		// need to cast Edge type to AdjacencyMatrixEdge
		AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;

		// create new array of length 2 that will contain
		// the edge's end vertices
		IVertex<V>[] endpoints = new IVertex[2];

		// fill array
		endpoints[0] = edge.start;
		endpoints[1] = edge.end;

		return endpoints;
	}

	@Override
	public IVertex<V> opposite(IVertex<V> v, IEdge<E> e) {
		// find end points of Edge e
		IVertex<V>[] endpoints = endVertices(e);

		// return the end point that is not v
		if (endpoints[0].equals(v)) {
			return endpoints[1];
		} else if (endpoints[1].equals(v)) {
			return endpoints[0];
		}

		// Problem! e is not connected to v.
		throw new RuntimeException("Error: cannot find opposite vertex.");
	}

	@Override
	public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
		if(adjacencyMatrix[((AdjacencyMatrixVertex) v).index()][((AdjacencyMatrixVertex) w).index()]!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public V replace(IVertex<V> v, V x) {
		AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) v;
		// store old element that we should return
		V temp = vertex.element;

		// do the replacement
		vertex.element = x;

		// return the old value
		return temp;
	}

	@Override
	public E replace(IEdge<E> e, E x) {
		AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;
		E temp = edge.element;
		edge.element = x;
		return temp;
	}

	@Override
	public IVertex<V> insertVertex(V v) {
		// create new vertex
		AdjacencyMatrixVertex vertex = new AdjacencyMatrixVertex(v);
	
		// insert the vertex into the vertex list
		// (returns a reference to the new Node that was created)
		INode<IVertex<V>> node = vertices.insertLast(vertex);
		
		//Keep record of the vertex's index
		vertex.index = vertices.size()-1;
		//System.out.println(vertex.index);

		// this reference must be stored in the vertex,
		// to make it easier to remove the vertex later.
		vertex.node = node;
		//vertex.printIndex();
		
		//resize the matrix
		adjacencyMatrix = resize();

		// return the new vertex that was created
		return vertex;
	}
	
	//resize function for removing vertex
	@SuppressWarnings("unchecked")
	public IEdge<E>[][] resize(int p) {
		int size = adjacencyMatrix.length;
		IEdge<E>[][] newam = new IEdge[size-1][size-1];
		int i = 0;
		int j = 0;
		while(i<size-1) {
			j = 0;
			while(j<size-1) {
				if(i<p && j<p) {
					newam[i][j] = adjacencyMatrix[i][j];
				}
				else if(i<p && j>=p) 
					newam[i][j] = adjacencyMatrix[i][j+1];
				else if(i>=p && j<p)
					newam[i][j] = adjacencyMatrix[i+1][j];
				else
					newam[i][j] = adjacencyMatrix[i+1][j+1];
				j++;
			}
			i++;
		}
		return newam;
	}
		
	//resize function for inserting vertex
	@SuppressWarnings("unchecked")
	public IEdge<E>[][] resize() {
		int size = adjacencyMatrix.length;
		IEdge<E>[][] newam = new IEdge[size+1][size+1];
		int i = 0;
		int j = 0;
		if(size!=0) {
			while(i<size) {
				j = 0;
				while(j<size) {
					newam[i][j] = adjacencyMatrix[i][j];
					j++;
				}
				i++;
			}
		}
		else {
			newam = new IEdge[1][1];
		}
		return newam;
	}
	
	@Override
	public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
		// create new edge object
		AdjacencyMatrixVertex vam = (AdjacencyMatrixVertex) v;
		AdjacencyMatrixVertex wam = (AdjacencyMatrixVertex) w;
		AdjacencyMatrixEdge edge = new AdjacencyMatrixEdge(vam, wam, o);
//		System.out.println(vam.element);
//		System.out.println(vam.index);
//		System.out.println(wam.element);
//		System.out.println(wam.index);
//		System.out.println(edge.element);
		adjacencyMatrix[vam.index][wam.index] = edge;
		adjacencyMatrix[wam.index][vam.index] = edge;

		// insert into the edge list and store the reference to the node
		// in the edge object
		INode<IEdge<E>> n = edges.insertLast(edge);
		edge.node = n;
		return edge;
	}

	@Override
	public V removeVertex(IVertex<V> v) {
		// first find all incident edges and remove those
		IIterator<IEdge<E>> ed = edges();
		while (ed.hasNext()) {
			IEdge<E> e = ed.next();
			IVertex<V>[] iv = endVertices(e);
			if(iv[0].element()==v.element()||iv[1].element()==v.element()) {
				removeEdge(e);
			}
		}

		// now we can remove the vertex from the vertex list
		AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) v;
		int temp = vertex.index;
		//System.out.println(temp);
		vertices.remove(vertex.node);
		
		//resize the adjacency matrix
		adjacencyMatrix = resize(temp);
		
		//change indices of vertices
		 IIterator<IVertex<V>> t = vertices.iterator();
		 IVertex<V> a = t.next();
	      while( t.hasNext() && ((AdjacencyMatrixVertex) a).index()<temp) {
	    	  //((AdjacencyMatrixVertex) a).printIndex();
	    	  a = t.next();
	      }
	      while(t.hasNext()) {
	    	  ((AdjacencyMatrixVertex) a).index--;
	    	 // ((AdjacencyMatrixVertex) a).printIndex();
	    	  a = t.next();
	      }
	      ((AdjacencyMatrixVertex) a).index--;
    	  //((AdjacencyMatrixVertex) a).printIndex();

		// return the element of the vertex that was removed
		return vertex.element;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public E removeEdge(IEdge<E> e) {
		// remove edge from edge list and return its element
		IVertex[] temp = endVertices(e);
		adjacencyMatrix[((AdjacencyMatrixVertex) temp[0]).index()][((AdjacencyMatrixVertex) temp[1]).index()] = null;
		adjacencyMatrix[((AdjacencyMatrixVertex) temp[1]).index()][((AdjacencyMatrixVertex) temp[0]).index()] = null;
		AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;
		edges.remove(edge.node);
		edge.start=null;
		edge.end=null;
		return edge.element;
	}

	@Override
	public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
		// strategy:
		// find all edges that are connected to v and
		// add them to "list".
		// Later, use the iterator() method in List to
		// get an iterator over these edges.
		IList<IEdge<E>> list = new DLinkedList<IEdge<E>>();

		int i = 0;
		while (i<adjacencyMatrix.length) {
			AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) adjacencyMatrix[0][i];
			if(edge!=null) {
				if (edge.start.equals(v))
					list.insertLast(edge);
				else if (edge.end.equals(v))
					list.insertLast(edge);
			}
			i++;
		}
		return list.iterator();
	}

	@Override
	public IIterator<IVertex<V>> vertices() {
		return vertices.iterator();
	}

	@Override
	public IIterator<IEdge<E>> edges() {
		return edges.iterator();
	}
}
