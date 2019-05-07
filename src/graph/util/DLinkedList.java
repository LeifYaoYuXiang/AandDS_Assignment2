package graph.util;

import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;

public class DLinkedList<T> implements IList<T> {

   private int size = 0;
   private DNode first = null;
   private DNode last = null;

   @Override
   public int size() {
      return size;
   }

   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   @Override
   public INode<T> first() {
      return first;
   }

   @Override
   public INode<T> last() {
      return last;
   }

   @Override
   public INode<T> prev( INode<T> n ) {
      DNode node = (DNode) n;
      return node.prev;
   }

   @Override
   public INode<T> next( INode<T> n ) {
      DNode node = (DNode) n;
      return node.next;
   }

   @Override
   public INode<T> insertFirst( T e ) {
      DNode n = new DNode( e );
      n.next = first;
      first = n;
      if ( n.next != null )
         n.next.prev = n;
      if ( size == 0 )
         last = n;
      size++;
      return n;
   }

   @Override
   public INode<T> insertLast( T e ) {
      DNode n = new DNode( e );
      n.prev = last;
      last = n;
      if ( n.prev != null )
         n.prev.next = n;
      if ( size == 0 )
         first = n;
      size++;
      return n;
   }

   @Override
   public INode<T> insertBefore( INode<T> n, T e ) {
      DNode on = (DNode) n;

      if ( on == first ) {
         return insertFirst( e );
      }

      else {
         DNode nn = new DNode( e );
         nn.next = on;
         nn.prev = on.prev;
         on.prev.next = nn;
         on.prev = nn;
         size++;
         return nn;
      }
   }

   @Override
   public INode<T> insertAfter( INode<T> n, T e ) {
      DNode on = (DNode) n;
      if ( on == last ) {
         return insertLast( e );
      }
      else {
         DNode nn = new DNode( e );
         nn.prev = on;
         nn.next = on.next;
         on.next.prev = nn;
         on.next = nn;
         size++;
         return nn;
      }
   }

   @Override
   public T replace( INode<T> n, T e ) {
      DNode node = (DNode) n;
      T toReturn = node.element();
      node.element = e;
      return toReturn;
   }

   @Override
   public T remove( INode<T> n ) {
      DNode on = (DNode) n;
      if ( size == 1 ) {
         first = null;
         last = null;
      }
      else if ( on == first ) {
         first = on.next;
         on.next.prev = null;
      }
      else if ( on == last ) {
         last = on.prev;
         on.prev.next = null;
      }
      else {
         on.next.prev = on.prev;
         on.prev.next = on.next;
      }
      size--;
      return on.element();
   }

   private class DNode implements INode<T> {
      public T element;
      public DNode next;
      public DNode prev;

      public DNode( T e ) {
         element = e;
      }

      public T element() {
         return element;
      }
   }
   
   @Override
   public IIterator<T> iterator() {
	   return new ListIterator<T>( this );
   }

}
