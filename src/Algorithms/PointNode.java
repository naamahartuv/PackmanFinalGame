package Algorithms;

import Geom.Point3D;

/**
 * this class saves the id of a point and the point
 * @author yael hava and naama hartuv
 *
 */

public class PointNode {

	private Point3D point;
	private String id;
	
	/**
	 * constructor
	 * @param point - the point
	 * @param id - the id
	 */
	
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
