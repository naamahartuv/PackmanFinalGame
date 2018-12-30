package TheGame;

import Geom.Point3D;

public class Block {

	private Point3D pointStart, PointEnd;
	private double weight;
	
	public Block(Point3D point1, Point3D point2) {
		this.pointStart = point1;
		this.PointEnd = point2;
	}
	
	public Block(Point3D point1, Point3D point2, double weight) {
		this.pointStart = point1;
		this.PointEnd = point2;
		this.weight = weight;
	}

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
