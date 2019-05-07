package graph.impl;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;
import graph.core.IVertex;
import graph.util.DLinkedList;

public class AdjacencyListGraph<V,E> implements IGraph<V,E>{
	/**
	 * Inner class to represent a vertex in an edge list graph implementation
	 */
	private class AdjacencyListVertex implements IVertex<V>{
		// reference to a node in the vertex list
		INode<IVertex<V>> node;
		
		// element stored in this vertex
		V element;
		
		//list of references to edge objects of incident edges
		IList<IEdge<E>> incidentEdges;
		
		public AdjacencyListVertex(V element) {
			this.element=element;
			this.incidentEdges=new DLinkedList<IEdge<E>>();
		}
		
		@Override
		public V element() {
			return this.element;
		}
		
		
		public String toString() {
			return element.toString();
		}
		
		//return the list of sequences of incident edges
		public IList<IEdge<E>> getEdgesList(){
			return this.incidentEdges;
		}
	
	}
	
	/**
	 * Inner class to represent an edge in an edge list graph implementation.
	 *
	 */
	private class AdjacencyListEdge implements IEdge<E>{
		// reference to a node in the edge list
		INode<IEdge<E>> node;
		
		// element stored in this edge
		E element;
		
		// the start and end vertices that this edge connects
		AdjacencyListVertex start,end;
		
		// the references to associated nodes in incidence sequences of vertices
		INode<IEdge<E>> incidentStartNode,incidentEndNode;
		
		// constructor to set the five fields
		public AdjacencyListEdge(AdjacencyListVertex end,AdjacencyListVertex start,E element) {
			this.element=element;
			
			this.start=start;
			this.end=end;
			
			// two vertexes of this edge will put this edge into their 'incident edges'  
			start.getEdgesList().insertLast(this);
			end.getEdgesList().insertLast(this);
			
			// get the references of nodes in the incident edges
			this.incidentStartNode=start.getEdgesList().last();
			this.incidentEndNode=end.getEdgesList().last();
		}
		
		@Override
		public E element() {
			return this.element;
		}
		
		public String toString() {
			return element.toString();
		}
		
	}

	// vertex list
	private IList<IVertex<V>> vertices;
	
	// edge list
	private IList<IEdge<E>> edges;
	
	/**
	 * Constructor
	 */
	public AdjacencyListGraph() {
		// create new (empty) lists of edges and vertices
		this.vertices=new DLinkedList<>();
		this.edges=new DLinkedList<>();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IVertex<V>[] endVertices(IEdge<E> e) {
		// need to cast Edge type to EdgeListEdge
		AdjacencyListEdge alE=(AdjacencyListGraph<V, E>.AdjacencyListEdge) e;
		
		// create new array of length 2 that will contain
		// the edge's end vertices
		IVertex<V>[] endpoints = new IVertex[2];

		// fill array
		endpoints[0] =alE.start;
		endpoints[1] = alE.end;

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

	@SuppressWarnings("rawtypes")
	@Override
	public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
		AdjacencyListVertex alV=(AdjacencyListGraph<V, E>.AdjacencyListVertex) v;
		AdjacencyListVertex alW=(AdjacencyListGraph<V, E>.AdjacencyListVertex) w;
		
		//select a shorter adjacent list and iterate it
		if(alV.getEdgesList().size()<alW.getEdgesList().size()) {
			IIterator<IEdge<E>> iter=alV.getEdgesList().iterator();
			while(iter.hasNext()) {
				//check if the edge connect v & w
				IVertex[] tempVertex=this.endVertices(iter.next());
				if(w.equals(tempVertex[0])||w.equals(tempVertex[1])) {
					return true;
				}
			}
			return false;
			
		}else {
			IIterator<IEdge<E>> iter=alW.getEdgesList().iterator();
			while(iter.hasNext()) {
				//check if the edge connect v & w
				IVertex[] tempVertex=this.endVertices(iter.next());
				if(v.equals(tempVertex[0])||v.equals(tempVertex[1])) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public V replace(IVertex<V> v, V o) {
		AdjacencyListVertex alV=(AdjacencyListGraph<V, E>.AdjacencyListVertex) v;
		V temp = alV.element;

		// do the replacement
		alV.element = o;

		// return the old value
		return temp;
		
	}

	@Override
	public E replace(IEdge<E> e, E o) {
		AdjacencyListEdge alE=(AdjacencyListGraph<V, E>.AdjacencyListEdge) e;
		E temp = alE.element;

		// do the replacement
		alE.element = o;

		// return the old value
		return temp;
	}

	@Override
	public IVertex<V> insertVertex(V o) {
		// create new vertex
		AdjacencyListVertex vertex = new AdjacencyListVertex(o);
		
		// insert the vertex into the vertex list
		// (returns a reference to the new Node that was created)
		INode<IVertex<V>> node = vertices.insertLast(vertex);
		
		// this reference must be stored in the vertex,
		// to make it easier to remove the vertex later.
		vertex.node = node;
		
		// return the new vertex that was created
		return vertex;
	}

	@Override
	public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
		// create new edge object
		AdjacencyListEdge edge=new AdjacencyListEdge((AdjacencyListVertex) v, (AdjacencyListVertex) w,o); 
		
		// insert into the edge list and store the reference to the node
		// in the edge object
		INode<IEdge<E>> n = edges.insertLast(edge);
		edge.node = n;
		
		return edge;
	}

	@Override
	public V removeVertex(IVertex<V> v) {
		AdjacencyListVertex vertex=(AdjacencyListGraph<V, E>.AdjacencyListVertex) v;
		IList<IEdge<E>> Aedges=vertex.incidentEdges;
		IIterator<IEdge<E>> iter=Aedges.iterator();
		//remove all incident edges
		while(iter.hasNext()) {
			this.removeEdge(iter.next());
		}
		
		//remove from vertex list
		vertices.remove(vertex.node);
		
		return vertex.element;
	}

	@Override
	public E removeEdge(IEdge<E> e) {
		AdjacencyListEdge edge=(AdjacencyListGraph<V, E>.AdjacencyListEdge) e;
		
		//remove references from two adjacency lists 
		edge.start.getEdgesList().remove(edge.incidentStartNode);
		edge.end.getEdgesList().remove(edge.incidentEndNode);
		
		//remove from edge list
		edges.remove(edge.node);
		
		return edge.element;
	}

	//iterate the adjacency list of the vertex
	@Override
	public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
		AdjacencyListVertex alV=(AdjacencyListGraph<V, E>.AdjacencyListVertex) v;
		return alV.getEdgesList().iterator();
	}

	@Override
	public IIterator<IVertex<V>> vertices() {
		return this.vertices.iterator();
	}

	@Override
	public IIterator<IEdge<E>> edges() {
		return this.edges.iterator();
	}

}
