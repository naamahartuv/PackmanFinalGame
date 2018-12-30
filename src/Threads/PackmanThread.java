package Threads;

import java.util.ArrayList;

import GUI.MyFrame;
import Geom.Point3D;
import TheGame.Packman;

/**
 * this class represents every thread of a packman
 * @author yael hava and naama hartuv
 *
 */

public class PackmanThread extends Thread{

	private MyFrame myFrame;
	private Packman packman;

	/**
	 * constructor
	 * @param packman
	 * @param myFrame - the gui
	 */

	public PackmanThread(Packman packman,MyFrame myFrame) {
		this.packman = packman;
		this.myFrame = myFrame;
	}

	/**
	 * runs over the packman's list of fruits to eat
	 */

	@Override
	public void run() {
		ArrayList<Point3D> p = myFrame.packmanSteps(this.packman);

		for (int i = 0; i < p.size(); i++) {
			this.packman.setPoint3D(p.get(i));
			this.myFrame.synchronizedPaint();
			try {
				this.sleep(500);

			} catch (Exception e) {

			}
		}
	}
}