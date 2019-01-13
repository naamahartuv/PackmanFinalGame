package TheGame;

import Geom.Point3D;

/**
 * this class represents a block
 * @author yael hava and naama hartuv
 */

public class Block {

	private Point3D pointStart, PointEnd;
	private double weight;
	
	/**
	 * constructor
	 * @param point1 -  one of the points of the block
	 * @param point2 -  one of the points of the block
	 */
	
	public Block(Point3D point1, Point3D point2) {
		this.pointStart = point1;
		this.PointEnd = point2;
	}
	
	/**
	 * constructor
	 * @param point1 -  one of the points of the block
	 * @param point2 -  one of the points of the block
	 * @param weight - the weight 
	 */
	
	public Block(Point3D point1, Point3D point2, double weight) {
		this.pointStart = point1;
		this.PointEnd = point2;
		this.weight = weight;
	}

	/**
	 * constructor
	 * @param s - the string create the element
	 */
	
	public Block(String[] s) {
		pointStart = new Point3D(Double.parseDouble(s[2]), Double.parseDouble(s[3]));
		PointEnd = new Point3D(Double.parseDouble(s[5]), Double.parseDouble(s[6]));
		weight = Double.parseDouble(s[8]);
	}
	
	public Point3D getPointStart() {
		return pointStart;
	}

	public void setPointStart(Point3D pointStart) {
		this.pointStart = pointStart;
	}

	public Point3D getPointEnd() {
		return PointEnd;
	}

	public void setPointEnd(Point3D pointEnd) {
		PointEnd = pointEnd;
	}
	
	
	
	
	
}
