package Algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import org.w3c.dom.NodeList;

import Coords.Cords;
import Coords.Map;
import Coords.MyCoords;
import Geom.Line;
import Geom.Point3D;
import Robot.Play;
import TheGame.Fruit;
import TheGame.Game;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

public class RunAlgo {

	private Game game;
	private PriorityQueue<FastestRoad> roadPrio;
	private ArrayList<Point3D> pointList, road;
	private ArrayList<Line> lineList;
	private Fruit fruit;
	
	
//	public RunAlgo() {
//		this.fruit = new Fruit();
//	}
	
	public RunAlgo(Game game) {

		
		this.game = game;
		this.fruit = new Fruit();
		if(game.getFruitList().size() != 0)
		{
			roadPrio = new PriorityQueue<FastestRoad>(game.getFruitList().size(), new DistanceComperator());
		}
		pointList = new ArrayList<Point3D> ();
		lineList = new ArrayList<Line>();
	}



	public void algo()
	{
		
		Point3D playerPoint = new Point3D(game.getPlayer().getPoint());
		GraphBlock blocks = new GraphBlock(game, lineList, pointList);
		blocks.findPoints();
		blocks.findLines();

		MyCoords c = new MyCoords(0, 0, 0);
		
		ArrayList<PointNode> nodeList = new ArrayList<PointNode>();
		
		for (int i = 0; i < game.getFruitList().size(); i++) 
		{
			
			String fruit = "fruit" + i;
			Graph g = new Graph();

			PointNode fruitNode = new PointNode(game.getFruitList().get(i).getPoint3D(), fruit);
			g.add(new Node(fruitNode.getId()));							//create fruit node
			nodeList.add(fruitNode);

	
			
			for (int j = 0; j < blocks.getPointList().size(); j++) {		//create block nodes
				PointNode blockNode = new PointNode(blocks.getPointList().get(j), "" + j);
				g.add(new Node(blockNode.getId()));
				nodeList.add(blockNode);
			}

			String player = "player";
			PointNode playerNode = new PointNode(playerPoint, player);	//create player node
			g.add(new Node(playerNode.getId()));
			nodeList.add(playerNode);


			for (int j = 0; j < blocks.getPointList().size(); j++) 
			{
				Line fruitLine = new Line(game.getFruitList().get(i).getPoint3D(), blocks.getPointList().get(j));	//create fruit-block line
				Edge fruitEdge= new Edge(fruitLine, g);
				if(fruitEdge.isEdge(blocks.getLineList(), fruitLine)) {	//check if it's an edge
					//add the fruit edge to the graph
					g.addEdge(fruit , "" + j, 
							c.distance3d(game.getFruitList().get(i).getPoint3D(), blocks.getPointList().get(j)));
				}

				for (int k = 0; k < blocks.getPointList().size() ; k++) 
				{
					Line blockLine = new Line(blocks.getPointList().get(j), blocks.getPointList().get(k));	//create block-block line
					Edge blockEdge = new Edge(blockLine, g);
					if(blockEdge.isEdge(blocks.getLineList(), blockLine)) {	//check if it's an edge
						//add the block edge to the graph
						g.addEdge("" + k, "" + j, c.distance3d(blocks.getPointList().get(k), blocks.getPointList().get(j)));
					}
				}

				Line playerLine = new Line(playerPoint, blocks.getPointList().get(j));	//create player-block line
				Edge playerEdge = new Edge(playerLine, g);
				if(playerEdge.isEdge(blocks.getLineList(), playerLine)) {	//check if it's an edge
					//add the player edge to the graph
					g.addEdge(player, "" + j, c.distance3d(	playerPoint, blocks.getPointList().get(j)));
				}


			}
			
			//now check if there is a line between the current fruit to the player
			Line PlayerFruitLine = new Line(game.getFruitList().get(i).getPoint3D(), playerPoint);
			Edge playerFruitEdge = new Edge(PlayerFruitLine, g);
			if(playerFruitEdge.isEdge(blocks.getLineList(), PlayerFruitLine)) 
			{	//check if it's an edge
				//add the player-fruit edge to the graph
				g.addEdge(player, fruit, c.distance3d(game.getFruitList().get(i).getPoint3D(), playerPoint));
			}

			Graph_Algo.dijkstra(g, fruit);	//find the fastest road from the fruit to the packman 

			Node roadNode = g.getNodeByName(player);
			ArrayList<PointNode> roadNodeList = new ArrayList<PointNode>();


//			//System.out.println("******" + i + "******");
//			ArrayList<String> shortestPath = roadNode.getPath();
//			for(int j = 0; j < shortestPath.size(); j++) {
//				System.out.print(" " + shortestPath.get(j));
//
//			}
//			System.out.println();

			for (int j = 0; j < roadNode.getPath().size(); j++)
{
				for (int k = 0; k < nodeList.size(); k++) 
				{

					if(roadNode.getPath().get(j).equals(nodeList.get(k).getId()))
						roadNodeList.add(nodeList.get(k));
				}
			}

			FastestRoad fastestRoad = new FastestRoad(game.getFruitList().get(i), roadNode, roadNodeList);
			roadPrio.add(fastestRoad);

		}

		
		road = new ArrayList<Point3D>();
		
		System.out.println("++++++++++++++++++++");
			System.out.println(roadPrio.size());
		System.out.println("++++++++++++++++++++");
		
		//sets the player point to the fruit's with the fastest road point
		//game.getPlayer().setPoint(roadPrio.peek().getFruit().getPoint3D());
	
		playerPoint = roadPrio.peek().getFruit().getPoint3D();
		//run over the road point list and add the point to the list of the paths
		for (int i = 0; i < roadPrio.peek().getRaodPointList().size(); i++) {
			road.add(roadPrio.peek().getRaodPointList().get(i));
		}
		

		
		this.fruit = roadPrio.peek().getFruit();
		
		//remove the fruit with the fastest road from the fruit's list
	//	game.getFruitList().remove(roadPrio.peek().getFruit());

	}


	public ArrayList<Point3D> getRoad() {
		return road;
	}



	public Fruit getFruit() {
		return fruit;
	}



}

//package Algorithms;
//
//import java.util.ArrayList;
//import java.util.PriorityQueue;
//
//import Geom.Line;
//import Geom.Point3D;
//import Robot.Play;
//import TheGame.Game;
//import graph.Graph;
//import graph.Graph_Algo;
//import graph.Node;
//
//public class RunAlgo {
//
//	private Game game;
//	private PriorityQueue<FastestRoad> roadPrio;
//	private ArrayList<Point3D> pointList;
//	private ArrayList<Line> lineList;
//	
//	
//	public RunAlgo(Game game) {
//		this.game = game;
//		roadPrio = new PriorityQueue<FastestRoad>(game.getFruitList().size(), new DistanceComperator());
//		pointList = new ArrayList<Point3D>();
//		lineList = new ArrayList<Line>();
//	}
//
//	public void run(Play play) {
//		GraphBlock blocks = new GraphBlock(game, lineList, pointList);
//		blocks.findPoints();
//		blocks.findLines();
//	
//			for (int i = 0; i < game.getFruitList().size(); i++) {
//			
//				String fruit = "fruit" + i;
//				Graph g = new Graph();
//
//				PointNode fruitNode = new PointNode(game.getFruitList().get(i).getPoint3D(), fruit);
//				g.add(new Node(fruitNode.getId()));							//create fruit node
//
//				for (int j = 0; j < blocks.getPointList().size(); j++) {		//create blocks nodes
//					PointNode blockNode = new PointNode(blocks.getPointList().get(j), "" + j);
//					g.add(new Node(blockNode.getId()));
//				}
//
//				String player = "player";
//				PointNode playerNode = new PointNode(game.getPlayer().getPoint(), player);	//create player node
//				g.add(new Node(playerNode.getId()));
//
//				for (int j = 0; j < blocks.getPointList().size(); j++) {	//run over the block's points list
//					//create fruit-block line
//					Line fruitLine = new Line(game.getFruitList().get(i).getPoint3D(), blocks.getPointList().get(j));	
//					Edge fruitEdge = new Edge(fruitLine, g);		
//					if(fruitEdge.isEdge(blocks.getLineList(), fruitLine)) {		//check if it's an edge
//						//add the fruit edge to the graph
//						g.addEdge(fruit, "" + j, 
//								game.getFruitList().get(i).getPoint3D().distance2D(blocks.getPointList().get(j)));
//					}
//
//					for (int k = 0; k < blocks.getPointList().size(); k++) {
//						
//						//create block-block line
//						Line blockLine = new Line(blocks.getPointList().get(j), blocks.getPointList().get(k));	
//						Edge blockEdge = new Edge(blockLine, g);		
//						if(blockEdge.isEdge(blocks.getLineList(), blockLine)) {		//check if it's an edge
//							//add the block edge to the graph
//							g.addEdge("" + k, "" + j,		
//									blocks.getPointList().get(k).distance2D(blocks.getPointList().get(j)));
//						}
//					}
//
//					//create player-block line
//					Line playerLine = new Line(game.getPlayer().getPoint(), blocks.getPointList().get(j));
//					Edge playerEdge = new Edge(playerLine, g);
//					if(playerEdge.isEdge(blocks.getLineList(), playerLine)) {	//check if it's an edge
//						//add the player edge to the graph
//						g.addEdge(player, "" + j, 
//								game.getPlayer().getPoint().distance2D(blocks.getPointList().get(j)));
//					}
//				}
//
//				//now check if there is a line between the current fruit to the player
//				Line playerFruitLine = new Line(game.getFruitList().get(i).getPoint3D(), game.getPlayer().getPoint());
//				Edge playerFruitEdge = new Edge(playerFruitLine, g);
//				if(playerFruitEdge.isEdge(blocks.getLineList(), playerFruitLine)) {	//check if it's an edge
//					//add the player-fruit edge to the graph
//					g.addEdge(player, fruit, 
//							game.getFruitList().get(i).getPoint3D().distance2D(game.getPlayer().getPoint()) );
//					
//				}
//					Graph_Algo.dijkstra(g, fruit);	//find the fastest road from the fruit to the packman 
//					Node roadNode = g.getNodeByName(player);
//					FastestRoad fastestRoad = new FastestRoad(game.getFruitList().get(i), roadNode);
//
//					roadPrio.add(fastestRoad);	
//
//				
//			}
//			
//			//sets the player point to the fruit's with the fastest road point
//			game.getPlayer().setPoint(roadPrio.peek().getFruit().getPoint3D());	
//			System.out.println(roadPrio.size());
//			//remove the fruit with the fastest road from the fruit's list
//			game.getFruitList().remove(roadPrio.peek().getFruit());
//			System.out.println("after: " + roadPrio.size());
//
//		}
//
//	
//
//
//
//
//}
