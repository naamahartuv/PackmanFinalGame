package TheGame;

import Geom.Point3D;

/**
 * this class represents a block
 * @author yael hava and naama hartuv
 */

public class Ghost {

	private Point3D point;
	private int ID;
	private double speed, radius;
	
	public Ghost(Point3D point) {
		this.point = point;
	}

	/**
	 * constructor
	 * @param point - the point
	 * @param ID - the id
	 * @param speed - the speed
	 * @param radius - the radius
	 */
	
	public Ghost(Point3D point, int ID, double speed, double radius) {
		this.point = point;
		this.ID = ID;
		this.speed = speed;
		this.radius = radius;
	}
	
	/**
	 * constructor
	 * @param s - the string create the element
	 */
	
	public Ghost(String[] s) {
		point = new Point3D(Double.parseDouble(s[2]), Double.parseDouble(s[3]));
		ID = Integer.parseInt(s[1]);
		speed = Double.parseDouble(s[5]);
		radius = Double.parseDouble(s[6]);
	}
	
	public Point3D getPoint() {
		return point;
	}

	public void setPoint(Point3D point) {
		this.point = point;
	}

	public int getID() {
		return ID;
	}

	public double getSpeed() {
		return speed;
	}

	public double getRadius() {
		return radius;
	}
	
	
	
	
	
}
