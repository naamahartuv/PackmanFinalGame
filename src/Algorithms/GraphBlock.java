package Algorithms;

import java.util.ArrayList;
import Geom.Line;
import Geom.Point3D;
import TheGame.Block;
import TheGame.Game;

public class GraphBlock {

	private Game game;
	private ArrayList<Point3D> pointList;
	private ArrayList<Line> lineList;


	public GraphBlock(Game game, ArrayList<Line> lineList, ArrayList<Point3D> pointList) {
		this.game = game;
		this.pointList = pointList;
		this.lineList = lineList;
	}


	public void findPoints() {
		for(Block b : game.getBlockList()) {
			pointList.add(new Point3D(b.getPointEnd().x(), b.getPointStart().y()));
			pointList.add(b.getPointStart());
			pointList.add(b.getPointEnd());
			pointList.add(new Point3D(b.getPointStart().x(), b.getPointEnd().y()));
		}
	}

	public void findLines() {
		for (int i = 0; i < pointList.size(); i = i + 4) {	//run over the point list
			for (int j = i; j - i < 3; j++) {				//run over 4 points - which is one block
				for(int k = j + 1; k - i < 4; k++) {
					if(pointList.get(j).x() == pointList.get(k).x() || 
							pointList.get(j).y() == pointList.get(k).y()) {
						lineList.add(new Line(pointList.get(j), pointList.get(k)));
					}
				}
			}
		}
	}



	public void filterPoints() {
		for (int i = 0; i < pointList.size(); i++) {
			for (int j = 0; j < game.getBlockList().size(); j++) {
				if(pointList.get(i).x() > game.getBlockList().get(j).getPointStart().x() ||
						pointList.get(i).x() < game.getBlockList().get(j).getPointEnd().x() ||
						pointList.get(i).y() > game.getBlockList().get(j).getPointEnd().y() ||
						pointList.get(i).y() < game.getBlockList().get(j).getPointStart().y()) {
					pointList.remove(i);
				}
			}
		}
	}

	public ArrayList<Point3D> getPointList() {
		return pointList;
	}


	public ArrayList<Line> getLineList() {
		return lineList;
	}


}
