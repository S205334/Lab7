package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.dizionario.dao.DizionarioDAO;

public class Model {
	
	private UndirectedGraph<String, DefaultEdge> graph;
	private List<String> recVisited;
	
	public void creaGrafo(int n) {
		graph = new SimpleGraph<>(DefaultEdge.class);
		List<String> words = searchWords(n);
		
		for(String w : words) 
			graph.addVertex(w);
				
		for(String w : words) {
			for(String w2 : words) {
				int diff = 0;
				
				for(int i = 0; i < w.length(); i++) 
					if(w.charAt(i) != w2.charAt(i)) 
						diff++;
			
				if(diff == 1)
					graph.addEdge(w, w2);
			}
			
		}
		
		System.out.println(graph.vertexSet());
		// System.out.println(graph.edgeSet());
	}
	
	public List<String> searchVicini(String parola) {
		
		List<String> vicini = new ArrayList<String>();
		
		if (!graph.containsVertex(parola))
			return null;
		
		for(String w : Graphs.neighborListOf(graph, parola))
			vicini.add(w);
		
		return vicini;
	}
	
	public List<String> searchConn(String p) {
		recVisited = new ArrayList<>();
		
		if (!graph.containsVertex(p))
			return null;
		
		recursiveVisit(p);
		
		return recVisited;
	}
	
	public void recursiveVisit(String p) {
		
		if (recVisited.contains(p))
			return;
		recVisited.add(p);
		
		for (String c : Graphs.neighborListOf(graph, p)) 
			recursiveVisit(c);
			
	}
	
	public List<String> searchWords(int n) {
		DizionarioDAO dao = new DizionarioDAO();
		return dao.searchWords(n);
	}
	
	/* public static void main(String[] args) {
		Model m = new Model();
				
		m.creaGrafo(m.searchWords(4));
		
		System.out.println(m.searchVicini("casa"));
		System.out.println(m.searchConn("casa"));
		
	} */

}
