package graph.impl;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;
import graph.core.IVertex;
import graph.util.DLinkedList;

public class AdjacencyListGraph<V,E> implements IGraph<V,E>{
	
	private class AdjacencyListVertex implements IVertex<V>{
		
		INode<IVertex<V>> node;
		V element;
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
		
		public IList<IEdge<E>> getEdgesList(){
			return this.incidentEdges;
		}
	
	}
	
	private class AdjacencyListEdge implements IEdge<E>{
		
		INode<IEdge<E>> node;
		E element;
		AdjacencyListVertex start,end;
		INode<IEdge<E>> incidentStartNode,incidentEndNode;
		
		public AdjacencyListEdge(AdjacencyListVertex end,AdjacencyListVertex start,E element) {
			this.element=element;
			
			this.start=start;
			this.end=end;
			
			start.getEdgesList().insertLast(this);
			end.getEdgesList().insertLast(this);
			
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
	
	private IList<IVertex<V>> vertices;
	private IList<IEdge<E>> edges;
	
	public AdjacencyListGraph() {
		this.vertices=new DLinkedList<>();
		this.edges=new DLinkedList<>();	
	}
	
	@Override
	public IVertex<V>[] endVertices(IEdge<E> e) {
		AdjacencyListEdge alE=(AdjacencyListGraph<V, E>.AdjacencyListEdge) e;
		
		IVertex<V>[] endpoints = new IVertex[2];

		// fill array
		endpoints[0] =alE.start;
		endpoints[1] = alE.end;

		
		// TODO Auto-generated method stub
		return endpoints;
	}

	@Override
	public IVertex<V> opposite(IVertex<V> v, IEdge<E> e) {
		IVertex<V>[] endpoints = endVertices(e);

		// return the end point that is not v
		if (endpoints[0].equals(v)) {
			return endpoints[1];
		} else if (endpoints[1].equals(v)) {
			return endpoints[0];
		}

		// Problem! e is not connected to v.
		throw new RuntimeException("Error: cannot find opposite vertex.");
		// TODO Auto-generated method stub
	}

	@Override
	public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
		AdjacencyListVertex alV=(AdjacencyListGraph<V, E>.AdjacencyListVertex) v;
		AdjacencyListVertex alW=(AdjacencyListGraph<V, E>.AdjacencyListVertex) w;
		
		if(alV.getEdgesList().size()<alW.getEdgesList().size()) {
			IIterator<IEdge<E>> iter=alV.getEdgesList().iterator();
			while(iter.hasNext()) {
				IVertex[] tempVertex=this.endVertices(iter.next());
				if(w.equals(tempVertex[0])||w.equals(tempVertex[1])) {
					return true;
				}
			}
			return false;
			
		}else {
			IIterator<IEdge<E>> iter=alW.getEdgesList().iterator();
			while(iter.hasNext()) {
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
		AdjacencyListVertex vertex = new AdjacencyListVertex(o);
		INode<IVertex<V>> node = vertices.insertLast(vertex);
		vertex.node = node;
		
		return vertex;
	}

	@Override
	public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
		AdjacencyListEdge edge=new AdjacencyListEdge((AdjacencyListVertex) v, (AdjacencyListVertex) w,o); 
		INode<IEdge<E>> n = edges.insertLast(edge);
		edge.node = n;
		return edge;
		
		
	}

	@Override
	public V removeVertex(IVertex<V> v) {
		AdjacencyListVertex vertex=(AdjacencyListGraph<V, E>.AdjacencyListVertex) v;
		IList<IEdge<E>> Aedges=vertex.incidentEdges;
		IIterator<IEdge<E>> iter=Aedges.iterator();
		while(iter.hasNext()) {
			this.removeEdge(iter.next());
		}
		vertices.remove(vertex.node);
		
		return vertex.element;
	}

	@Override
	public E removeEdge(IEdge<E> e) {
		AdjacencyListEdge edge=(AdjacencyListGraph<V, E>.AdjacencyListEdge) e;
		edge.start.getEdgesList().remove(edge.incidentStartNode);
		edge.end.getEdgesList().remove(edge.incidentEndNode);

		edges.remove(edge.node);
		
		return edge.element;
	}

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
