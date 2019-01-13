package Algorithms;

import java.util.ArrayList;

import Geom.Point3D;
import TheGame.Fruit;
import graph.Node;

public class FastestRoad {

	
	private Fruit fruit;
	private double dis;
	private ArrayList<String> road;
	private ArrayList<Point3D> raodPointList = new ArrayList<Point3D>();
	
	public FastestRoad(Fruit fruit,Node node, ArrayList<PointNode> nodeList ) {
		this.fruit = fruit;
		this.dis = node.getDist();
		this.road = node.getPath();
		for(PointNode p : nodeList) {
			raodPointList.add(p.getPoint());
		}
	}


	public ArrayList<Point3D> getRaodPointList() {
		return raodPointList;
	}


	public Fruit getFruit() {
		return fruit;
	}


	public double getDis() {
		return dis;
	}


	public ArrayList<String> getRoad() {
		return road;
	}
}

//package Algorithms;
//
//import java.util.ArrayList;
//
//import TheGame.Fruit;
//import graph.Node;
//
//public class FastestRoad {
//
//	private Fruit fruit;
//	private double dis;
//	private ArrayList<String> road;
//	
//	
//	public FastestRoad(Fruit fruit, Node node) {
//		this.fruit = fruit;
//		this.dis  = node.getDist();
//		this.road = node.getPath();
//	}
//
//
//	public Fruit getFruit() {
//		return fruit;
//	}
//
//
//	public double getDis() {
//		return dis;
//	}
//
//
//	public ArrayList<String> getRoad() {
//		return road;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//}
