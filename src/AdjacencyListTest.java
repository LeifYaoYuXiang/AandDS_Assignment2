import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyListGraph;

public class AdjacencyListTest {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		/*
		 * Basic Test: Test some simple case
		 * 
		 * */
			
		// create some vertexes
		//test: insertVertex() in AdjacencyListGraph
		IGraph<String,Integer> g=new AdjacencyListGraph<String,Integer>();
		IVertex<String> hnl = g.insertVertex( "HNL" );
	    IVertex<String> lax = g.insertVertex( "LAX" );
	    IVertex<String> sfo = g.insertVertex( "SFO" );
	    IVertex<String> ord = g.insertVertex( "ORD" );
	    IVertex<String> dfw = g.insertVertex( "DFW" );
	    IVertex<String> lga = g.insertVertex( "LGA" );
	    IVertex<String> pvd = g.insertVertex( "PVD" );
	    IVertex<String> mia = g.insertVertex( "MIA" );

	    // create some edges
	    //test: insertEdge() in AdjacencyListGraph
	    IEdge<Integer> hnllax = g.insertEdge( hnl, lax, 2555 );
	    IEdge<Integer> laxsfo = g.insertEdge( lax, sfo, 337 );
	    IEdge<Integer> ordsfo = g.insertEdge( ord, sfo, 1843 );
	    IEdge<Integer> laxord = g.insertEdge( lax, ord, 1743 );
	    IEdge<Integer> dfwlax = g.insertEdge( dfw, lax, 1233 );
	    IEdge<Integer> ordpvd = g.insertEdge( ord, pvd, 849 );
	    IEdge<Integer> dfwlga = g.insertEdge( dfw, lga, 1387 );
	    IEdge<Integer> dfwmia = g.insertEdge( dfw, mia, 1120 );
	    IEdge<Integer> lgamia = g.insertEdge( lga, mia, 1099 );
	    IEdge<Integer> lgapvd = g.insertEdge( lga, pvd, 142 );
	    
	    //test: areAdjacent() in AdjacencyListGraph
	    if ( g.areAdjacent( sfo,  ord ) ) {
	    	System.out.println( "SFO and ORD adjacent: correct" );
	    }else {
	    	System.out.println( "SFO and ORD adjacent: incorrect" );
	    }
	      
	    //test: endVertices() in AdjacencyListGraph
	    IVertex<String>[] ends = g.endVertices( laxord );
	    if ( ( ends[0] == lax && ends[1] == ord ) ||
	         ( ends[1] == lax && ends[0] == ord ) ) {
	    	 System.out.println( "End vertices of LAX<->ORD: correct" );
	    }else {
	    	System.out.println( "End vertices of LAX<->ORD: incorrect" );
	    }
	    
	    // test: opposite() in AdjacencyListGraph
	     if ( g.opposite( pvd, lgapvd ) == lga ) {
	    	System.out.println( "Opposite of PVD along LGA<->PVD: correct" ); 
	     }else {
	    	System.out.println( "Opposite of PVD along LGA<->PVD: incorrect" ); 
	     }
	    
	     //test: get the element of one vertex in AdjacencyListGraph 
	     String miaElement = mia.element();
	     System.out.println( "Element of MIA is: " + miaElement );
	      
	     //test: get the element of one edge in AdjacencyListGraph
	     int dfwlaxElement = dfwlax.element();
	     System.out.println( "Distance from DFW to LAX is: " + dfwlaxElement );

	     //test: edges() in AdjacencyListGraph
	     IIterator<IEdge<Integer>> it1=g.edges();
	     while(it1.hasNext()) {
	    	 IEdge<Integer> v = it1.next();
	         System.out.println( v.element() ); 
	     }
	     
	     //test: vertices() in AdjacencyListGraph
	     IIterator<IVertex<String>> it = g.vertices();
	     while( it.hasNext() ) {
	         // here I must cast also, since it.next() returns an Object
	         IVertex<String> v = it.next();
	         System.out.println( v.element() );
	      }
	      
	     //test: replace() in AdjacencyListGraph--for edges
	     g.replace(hnllax, 30);
	     System.out.println("The distance between HNL & LAX is "+ hnllax.element());
	     
	     //test: replace() in AdjacencyListGraph--for vertexes
	     g.replace(pvd, "New PVD");
	     System.out.println("The new name of station PVD is "+pvd.element());
	     
	     //test: removeEdge() in AdjacencyListGraph
	     g.removeEdge(ordsfo);
	     if ( g.areAdjacent( sfo,  ord ) ) {
	      System.out.println( "SFO and ORD not adjacent: incorrect" );
	     }else {
	      System.out.println( "SFO and ORD not adjacent: correct" ); 
	     }
		  
	     //test: removeVertex() in AdjacencyListGraph
	     g.removeVertex(dfw);
	     if ( g.areAdjacent( dfw,  lax ) ) {
	    	 System.out.println( "DFW and LAX not adjacent: incorrect" ); 
	     }else {
	      System.out.println( "DFW and LAX not adjacent: correct" );
	     }
		
	}
}
