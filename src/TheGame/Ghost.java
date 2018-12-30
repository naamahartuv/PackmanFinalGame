package TheGame;

import Geom.Point3D;

public class Ghost {

	private Point3D point;
	private int ID;
	private double speed, radius;
	
	public Ghost(Point3D point) {
		this.point = point;
	}

	public Ghost(Point3D point, int ID, double speed, double radius) {
		this.point = point;
		this.ID = ID;
		this.speed = speed;
		this.radius = radius;
	}
	
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
