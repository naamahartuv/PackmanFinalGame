package Algorithms;

import java.util.ArrayList;

import TheGame.Fruit;
import graph.Node;

public class FastestRoad {

	private Fruit fruit;
	private double dis;
	private ArrayList<String> road;
	
	
	public FastestRoad(Fruit fruit, Node node) {
		this.fruit = fruit;
		this.dis  = node.getDist();
		this.road = node.getPath();
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
