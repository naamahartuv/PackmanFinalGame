package Threads;

import java.util.ArrayList;

import Algorithms.RunAlgo;
import Coords.MyCoords;
import GUI.MyFrame;
import GUI.MyFrame.DrawFrame;
import Geom.Point3D;
import Robot.Play;
import TheGame.Game;
import TheGame.Map;

public class RoadThread extends Thread{

	private ArrayList<Point3D> roadList;
	private Play play;
	private Game game;
	private DrawFrame myFrame;
	private Map map;
	private RunAlgo algo;


	public RoadThread(Play play, Game game, DrawFrame frame) {
		this.play = play;
		this.game = game;
		this.myFrame = frame;
	}

	@Override
	public void run() {

		//		play.setInitLocation(game.getPlayer().getPoint().x(), game.getPlayer().getPoint().y());
		//		play.start();
		System.out.println("enter run");
		
		
		while(play.isRuning()) 
		{
			System.out.println("enter while");
			algo = new RunAlgo(game);
			algo.algo();
			roadList = algo.getRoad();
			
			for (int i = roadList.size() - 1; i >= 0; i--) 
			{
				System.out.println("enter for");
				MyCoords c = new MyCoords(0, 0, 0);
				double angle = c.azimuth_elevation_dist(game.getPlayer().getPoint(), roadList.get(i))[0];
				map = new Map();
				Point3D playerPoint = game.getPlayer().getPoint();
				Point3D roadPoint = roadList.get(i);
				
				double dis = c.distance3d(playerPoint, roadPoint);
				//	System.out.println("point" + game.getPlayer().getPoint());
				//System.out.println("dis1 " + dis);
				//	System.out.println("ang" + angle);
				
				//RunAlgo algo = new RunAlgo();
				
				
				
				while(dis > 1) {
					//			System.out.println("hii" + i);
					play.rotate(angle);
					//System.out.println("angle" + angle);
					game.update(play);
					playerPoint = game.getPlayer().getPoint();
//					roadPoint = map.GPS2Pixel(roadList.get(i), myFrame.getWidth(), myFrame.getHeight());
					dis = c.distance3d(playerPoint, roadPoint);
					//System.out.println("dis2 " + dis);
					//System.out.println("point2 " + map.GPS2Pixel(roadPoint, myFrame.getWidth(), myFrame.getHeight()));

					try {
						sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					myFrame.repaint();
					
//					if(!(algo.getFruit().isExist(game.getFruitList()))) {
//						break;
//					}

				}
			}
		}
	}


}
