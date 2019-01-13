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

/**
 * this class is the algorithm of an automatic play 
 * @author yael hava and naama hartuv
 */

public class RunAlgo {

	private Game game;
	private PriorityQueue<FastestRoad> roadPrio;
	private ArrayList<Point3D> pointList, road;
	private ArrayList<Line> lineList;
	
	/**
	 * constructor
	 * @param game
	 */
	
	public RunAlgo(Game game) {

		
		this.game = game;
		if(game.getFruitList().size() != 0){
			roadPrio = new PriorityQueue<FastestRoad>(game.getFruitList().size(), new DistanceComperator());
		}
		pointList = new ArrayList<Point3D> ();
		lineList = new ArrayList<Line>();
	}



	public void algo(){
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


			for (int j = 0; j < blocks.getPointList().size(); j++) {
				//create fruit-block line
				Line fruitLine = new Line(game.getFruitList().get(i).getPoint3D(), blocks.getPointList().get(j));	
				Edge fruitEdge= new Edge(fruitLine, g);
				if(fruitEdge.isEdge(blocks.getLineList(), fruitLine)) {	//check if it's an edge
					//add the fruit edge to the graph
					g.addEdge(fruit , "" + j, 
							c.distance3d(game.getFruitList().get(i).getPoint3D(), blocks.getPointList().get(j)));
				}

				for (int k = 0; k < blocks.getPointList().size() ; k++) {
					//create block-block line
					Line blockLine = new Line(blocks.getPointList().get(j), blocks.getPointList().get(k));	
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
			if(playerFruitEdge.isEdge(blocks.getLineList(), PlayerFruitLine)) {	//check if it's an edge
				//add the player-fruit edge to the graph
				g.addEdge(player, fruit, c.distance3d(game.getFruitList().get(i).getPoint3D(), playerPoint));
			}

			Graph_Algo.dijkstra(g, fruit);	//find the fastest road from the fruit to the packman 

			Node roadNode = g.getNodeByName(player);
			ArrayList<PointNode> roadNodeList = new ArrayList<PointNode>();



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
			
			//sets the player point to the fruit's with the fastest road point
		playerPoint = roadPrio.peek().getFruit().getPoint3D();
			
			//run over the road point list and add the point to the list of the paths
		for (int i = 0; i < roadPrio.peek().getRaodPointList().size(); i++) {
			road.add(roadPrio.peek().getRaodPointList().get(i));
		}
	}


	public ArrayList<Point3D> getRoad() {
		return road;
	}



	
	
}