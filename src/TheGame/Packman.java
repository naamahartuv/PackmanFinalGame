package TheGame;


import java.util.ArrayList;
import Geom.Point3D;

/**
 * this class represents a packman in the game
 * @author yael hava and naama hartuv
 *
 */


public class Packman {

	private PackmanMetaData data;
	private Point3D point3D;
	private double time = 0;
	private double radius, moveAbility;
	private int ID;

/**
 * constructor
 * @param point - point of packman
 * @param data - data of packman
 */

	public Packman(Point3D point, PackmanMetaData data) {
		this.point3D = point;
		this.data = data;
	}
	
	/**
	 * constructor
	 * @param point
	 * @param radius
	 * @param moveAbility
	 * @param ID
	 */
	
	public Packman(Point3D point, double radius, double moveAbility, int ID) {
		this.point3D = point;
		this.radius = radius;
		this.moveAbility = moveAbility;
		this.ID = ID;
			}
	
	
	public double getRadius() {
		return radius;
	}


	public double getMoveAbility() {
		return moveAbility;
	}


	public PackmanMetaData getPackmanData() {
		return data;
	}

	public Point3D getPoint3D() {
		return point3D;
	}

	public void setPoint3D(Point3D point3d) {
		point3D = point3d;
	}
	
	
	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}


	public int getID() {
		return ID;
	}


}
