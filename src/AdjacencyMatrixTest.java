import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IVertex;
import graph.impl.AdjacencyMatrixGraph;

public class AdjacencyMatrixTest {
	
	//matrix test function
	public static void printMatrix(IEdge<Integer>[][] am,IGraph<String,Integer> g) {
		int i = 0;
		int j = 0;
		while(i<am.length) {
			j = 0;
			while(j<am.length) {
				System.out.print("|");
				if(am[j][i]!=null){
					IVertex<String>[] iv = g.endVertices(am[j][i]);
					System.out.printf("%3s",iv[0]);
					System.out.printf("%3s",iv[1]);
				}
				else {
					System.out.printf("%6s","");
				}
				j++;
			}
			System.out.print("|");
			System.out.println("\n");
			i++;
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		// create some vertexes
		//test: insertVertex() in AdjacencyMatrixGraph
		IGraph<String,Integer> g=new AdjacencyMatrixGraph<String,Integer>();
		IVertex<String> hnl = g.insertVertex( "HNL" );
	    IVertex<String> lax = g.insertVertex( "LAX" );
	    IVertex<String> sfo = g.insertVertex( "SFO" );
	    IVertex<String> ord = g.insertVertex( "ORD" );
	    IVertex<String> dfw = g.insertVertex( "DFW" );
	    IVertex<String> lga = g.insertVertex( "LGA" );
	    IVertex<String> pvd = g.insertVertex( "PVD" );
	    IVertex<String> mia = g.insertVertex( "MIA" );

	    // create some edges
	    //test: insertEdge() in AdjacencyMatrixGraph
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
	    
	    //test:matrix
	    printMatrix(((AdjacencyMatrixGraph<String, Integer>) g).getMatrix(),g);
	    
	    //test: areAdjacent() in AdjacencyMatrixGraph
	    if ( g.areAdjacent( sfo,  ord ) ) {
	    	System.out.println( "SFO and ORD adjacent: correct" );
	    }else {
	    	System.out.println( "SFO and ORD adjacent: incorrect" );
	    }
	      
	    //test: endVertices() in AdjacencyMatrixGraph
	    IVertex<String>[] ends = g.endVertices( laxord );
	    if ( ( ends[0] == lax && ends[1] == ord ) ||
	         ( ends[1] == lax && ends[0] == ord ) ) {
	    	 System.out.println( "End vertices of LAX<->ORD: correct" );
	    }else {
	    	System.out.println( "End vertices of LAX<->ORD: incorrect" );
	    }
	    
	    // test: opposite() in AdjacencyMatrixGraph
	     if ( g.opposite( pvd, lgapvd ) == lga ) {
	    	System.out.println( "Opposite of PVD along LGA<->PVD: correct" ); 
	     }else {
	    	System.out.println( "Opposite of PVD along LGA<->PVD: incorrect" ); 
	     }
	    
	     //test: get the element of one vertex in AdjacencyMatrixGraph 
	     String miaElement = mia.element();
	     System.out.println( "Element of MIA is: " + miaElement );
	      
	     //test: get the element of one edge in AdjacencyMatrixGraph
	     int dfwlaxElement = dfwlax.element();
	     System.out.println( "Distance from DFW to LAX is: " + dfwlaxElement );

	     //test: edges() in AdjacencyMatrixGraph
	     IIterator<IEdge<Integer>> it1=g.edges();
	     while(it1.hasNext()) {
	    	 IEdge<Integer> v = it1.next();
	         System.out.println( v.element() ); 
	     }
	     
	     //test: vertices() in AdjacencyMatrixGraph
	     IIterator<IVertex<String>> it = g.vertices();
	     while( it.hasNext() ) {
	         IVertex<String> v = it.next();
	         System.out.println( v.element() );
	      }
	      
	     //test: replace() in AdjacencyMatrixGraph--for edges
	     g.replace(hnllax, 30);
	     System.out.println("The distance between HNL & LAX is "+ hnllax.element());
	     
	     //test: replace() in AdjacencyMatrixGraph--for vertexes
	     g.replace(pvd, "DVD");
	     System.out.println("The new name of station PVD is "+pvd.element());
	     
	     //test: removeEdge() in AdjacencyMatrixGraph
	     g.removeEdge(ordsfo);
	     if ( g.areAdjacent( sfo,  ord ) ) {
	      System.out.println( "SFO and ORD not adjacent: incorrect" );
	     }else {
	      System.out.println( "SFO and ORD not adjacent: correct" ); 
	     }
	     
	   //test:matrix
		    printMatrix(((AdjacencyMatrixGraph<String, Integer>) g).getMatrix(),g);
		  
	     //test: removeVertex() in AdjacencyMatrixGraph
	     String save = dfw.element();
	     g.removeVertex(dfw);
	     System.out.println("-------------------------------------------------------");
	     
	   //test:matrix
		    printMatrix(((AdjacencyMatrixGraph<String, Integer>) g).getMatrix(),g);
	     
	     //now test adjacency
		 IIterator<IVertex<String>> i=g.vertices();
	     while ( i.hasNext()) {
	    	 IVertex<String> s = i.next();
	    	 if(g.areAdjacent( dfw, s  ) ) {
	    	 System.out.println( "DFW and "+s.element()+" not adjacent: incorrect" ); 
	    	 }else {
	    		 System.out.println( "DFW and "+s.element()+" not adjacent: correct" );
	    	 }
	     }
	     
	     //now test edge iterator
	     IIterator<IEdge<Integer>> j=g.edges(); 
	     while ( j.hasNext()) {
	    	 IEdge<Integer> s = j.next();
	    	 IVertex<String>[] e = g.endVertices(s);
	    	 if(e[0].element()==save || e[1].element()==save) {
	    		 System.out.println("Remove error in edge "+e[0].element()+e[1].element());
	    	 }
	     }
	}
}
