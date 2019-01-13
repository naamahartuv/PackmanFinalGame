package Threads;

import java.util.ArrayList;

import Algorithms.RunAlgo;
import Coords.Map;
import Coords.MyCoords;
import GUI.MyFrame;
import GUI.MyFrame.DrawFrame;
import Geom.Point3D;
import Robot.Play;
import TheGame.Game;

public class RoadThread extends Thread{

	private ArrayList<Point3D> roadList;
	private Play play;
	private Game game;
	private DrawFrame myFrame;
	private RunAlgo algo;


	public RoadThread(Play play, Game game, DrawFrame frame) {
		this.play = play;
		this.game = game;
		this.myFrame = frame;
	}

	@Override
	public void run() {
		
		while(play.isRuning()) 
		{
			algo = new RunAlgo(game);
			algo.algo();
			roadList = algo.getRoad();
			
			for (int i = roadList.size() - 1; i >= 0; i--) 
			{
				MyCoords c = new MyCoords(0, 0, 0);
				double angle = c.azimuth_elevation_dist(game.getPlayer().getPoint(), roadList.get(i))[0];
				
				Point3D playerPoint = game.getPlayer().getPoint();
				Point3D roadPoint = roadList.get(i);
				
				double dis = c.distance3d(playerPoint, roadPoint);
				
			
				while(dis > 1) {
					play.rotate(angle);
					game.update(play);
					playerPoint = game.getPlayer().getPoint();
					dis = c.distance3d(playerPoint, roadPoint);
			
					try {
						sleep(40);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					myFrame.repaint();
					

				}
			}
		}
	}


}
