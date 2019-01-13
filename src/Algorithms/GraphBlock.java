package Algorithms;


import java.util.ArrayList;

import Geom.Line;
import Geom.Point3D;
import TheGame.Block;
import TheGame.Game;

/**
 * this class represents the dots and lines of the blocks on the graph
 * @author yael hava and naama hartuv
 *
 */


public class GraphBlock {


	private Game game;
	private ArrayList<Point3D> pointList;
	private ArrayList<Line> lineList;
	
	public ArrayList<Point3D> getPointList() {
		return pointList;
	}

	public ArrayList<Line> getLineList() {
		return lineList;
	}

	
	/**
	 * constructor 
	 * @param game - game
	 * @param lineList - the list with the nodes with the points
	 * @param pointList - the list with the points of the block
	 */
	
	
	public GraphBlock (Game game, ArrayList<Line> lineList, ArrayList<Point3D> pointList) {
		this.game = game; 
		this.pointList = pointList;
		this.lineList = lineList;
	}
	
	/**
	 * finds the points of all the blocks
	 */
	
	public void findPoints() {
		for(Block b : game.getBlockList()) {
			pointList.add(new Point3D(b.getPointEnd().x(), b.getPointStart().y()));
			pointList.add(b.getPointStart());
			pointList.add(b.getPointEnd());
			pointList.add(new Point3D(b.getPointStart().x(), b.getPointEnd().y()));
			
		}
	}
	
	/**
	 * finds the lines between all the points
	 */
	
	public void findLines() {
		for (int i = 0; i < pointList.size(); i=i+4) {
			for (int j = i; j - i < 3 ; j++) {
				for (int k = j+1; k - i< 4 ; k++) {
					if(pointList.get(j).x()== pointList.get(k).x() || pointList.get(j).y()== pointList.get(k).y() )
					lineList.add(new Line(pointList.get(j),pointList.get(k)));
					
				}	
			}
		}
	}
}

