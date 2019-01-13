package Algorithms;

import java.util.ArrayList;

import Geom.Line;
import TheGame.Game;
import graph.Graph;

public class Edge {

	private Line line;
	private Game game;
	private Graph graph;


	public Edge (Line line, Graph graph) {
		this.line = line;
		this.graph = graph;
	}

	public boolean isEdge(ArrayList<Line> lineList, Line line) {
		
		for(int i = 0; i < lineList.size(); i++) {
			if(!lineList.get(i).isEqual(line)) {
				if(lineList.get(i).isIntersect(line)) {
					return false;
				}				
			}
		}
		return true;
	}
		

}

//package Algorithms;
//
//import java.util.ArrayList;
//
//import Geom.Line;
//import TheGame.Game;
//import graph.Graph;
//
//public class Edge {
//
//	private Line line;
//	private Game game;
//	private Graph graph;
//
//
//	public Edge(Line line, Graph graph) {
//		this.line = line;
//		this.graph = graph;
//	}
//
//	public boolean isEdge(ArrayList<Line> lineList, Line line) {
//		for(int i = 0; i < lineList.size(); i++) {
//			if(!lineList.get(i).isEqual(line)) {
//				if(lineList.get(i).isIntersect(line)) {
//					return false;
//				}				
//			}
//		}
//		return true;
//	}
//
//}
