package Algorithms;

import Geom.Point3D;

public class PointNode {

	private Point3D point;
	private String id;
	
	
	public PointNode(Point3D point, String id) {
		this.point = point;
		this.id = id;
	}
	
	public Point3D getPoint() {
		return point;
	}

	public String getId() {
		return id;
	}
	
	
}
