package Algorithms;

import java.util.ArrayList;
import java.util.PriorityQueue;

import Geom.Line;
import Geom.Point3D;
import Robot.Play;
import TheGame.Game;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

public class RunAlgo {

	private Game game;
	private PriorityQueue<FastestRoad> roadPrio;
	private ArrayList<Point3D> pointList;
	private ArrayList<Line> lineList;
	
	
	public RunAlgo(Game game) {
		this.game = game;
		roadPrio = new PriorityQueue<FastestRoad>(game.getFruitList().size(), new DistanceComperator());
		pointList = new ArrayList<Point3D>();
		lineList = new ArrayList<Line>();
	}

	public void run(Play play) {
		GraphBlock blocks = new GraphBlock(game, lineList, pointList);
		blocks.findPoints();
		blocks.findLines();
	
			for (int i = 0; i < game.getFruitList().size(); i++) {
			
				String fruit = "fruit" + i;
				Graph g = new Graph();

				PointNode fruitNode = new PointNode(game.getFruitList().get(i).getPoint3D(), fruit);
				g.add(new Node(fruitNode.getId()));							//create fruit node

				for (int j = 0; j < blocks.getPointList().size(); j++) {		//create blocks nodes
					PointNode blockNode = new PointNode(blocks.getPointList().get(j), "" + j);
					g.add(new Node(blockNode.getId()));
				}

				String player = "player";
				PointNode playerNode = new PointNode(game.getPlayer().getPoint(), player);	//create player node
				g.add(new Node(playerNode.getId()));

				for (int j = 0; j < blocks.getPointList().size(); j++) {	//run over the block's points list
					//create fruit-block line
					Line fruitLine = new Line(game.getFruitList().get(i).getPoint3D(), blocks.getPointList().get(j));	
					Edge fruitEdge = new Edge(fruitLine, g);		
					if(fruitEdge.isEdge(blocks.getLineList(), fruitLine)) {		//check if it's an edge
						//add the fruit edge to the graph
						g.addEdge(fruit, "" + j, 
								game.getFruitList().get(i).getPoint3D().distance2D(blocks.getPointList().get(j)));
					}

					for (int k = 0; k < blocks.getPointList().size(); k++) {
						
						//create block-block line
						Line blockLine = new Line(blocks.getPointList().get(j), blocks.getPointList().get(k));	
						Edge blockEdge = new Edge(blockLine, g);		
						if(blockEdge.isEdge(blocks.getLineList(), blockLine)) {		//check if it's an edge
							//add the block edge to the graph
							g.addEdge("" + k, "" + j,		
									blocks.getPointList().get(k).distance2D(blocks.getPointList().get(j)));
						}
					}

					//create player-block line
					Line playerLine = new Line(game.getPlayer().getPoint(), blocks.getPointList().get(j));
					Edge playerEdge = new Edge(playerLine, g);
					if(playerEdge.isEdge(blocks.getLineList(), playerLine)) {	//check if it's an edge
						//add the player edge to the graph
						g.addEdge(player, "" + j, 
								game.getPlayer().getPoint().distance2D(blocks.getPointList().get(j)));
					}
				}

				//now check if there is a line between the current fruit to the player
				Line playerFruitLine = new Line(game.getFruitList().get(i).getPoint3D(), game.getPlayer().getPoint());
				Edge playerFruitEdge = new Edge(playerFruitLine, g);
				if(playerFruitEdge.isEdge(blocks.getLineList(), playerFruitLine)) {	//check if it's an edge
					//add the player-fruit edge to the graph
					g.addEdge(player, fruit, 
							game.getFruitList().get(i).getPoint3D().distance2D(game.getPlayer().getPoint()) );
					
				}
					Graph_Algo.dijkstra(g, fruit);	//find the fastest road from the fruit to the packman 
					Node roadNode = g.getNodeByName(player);
					FastestRoad fastestRoad = new FastestRoad(game.getFruitList().get(i), roadNode);

					roadPrio.add(fastestRoad);	

				
			}
			
	
			//sets the player point to the fruit's with the fastest road point
			game.getPlayer().setPoint(roadPrio.peek().getFruit().getPoint3D());	
			//remove the fruit with the fastest road from the fruit's list
			game.getFruitList().remove(roadPrio.peek().getFruit());
		}

	




}
