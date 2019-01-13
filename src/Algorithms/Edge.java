package Algorithms;

import java.util.ArrayList;

import Geom.Line;
import TheGame.Game;
import graph.Graph;

/**
 * this class reprisents an edge at the graph that does'nt intersect with another
 * @author yael hava and naama hartuv
 *
 */


public class Edge {

	private Line line;
	private Game game;
	private Graph graph;

	/**
	 * constructor
	 * @param line - the line
	 * @param graph - the graph
	 */

	public Edge (Line line, Graph graph) {
		this.line = line;
		this.graph = graph;
	}

	
	/**
	 * check if one line intersects another line - if not - create an edge
	 * @param lineList - the list of all lines
	 * @param line - the line to check if intersects
	 * @return - if intersect or not
	 */
	
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

