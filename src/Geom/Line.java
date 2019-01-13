package Geom;

import java.awt.geom.Line2D;

/**
 * this class represents a line 
 * @author yael hava and naama hartuv
 *
 */

public class Line {

	private Point3D point1 , point2;

	
	public Point3D getPoint1() {
		return point1;
	}

	public Point3D getPoint2() {
		return point2;
	}

	/**
	 * constructor
	 * @param point1 - one edge of the line
	 * @param point2 - the other edge of the line
	 */
	
	public Line (Point3D point1, Point3D point2) {
		this.point1= point1;
		this.point2 = point2;
	}
	
	/**
	 * checks if the lines are the same line
	 * @param line - the line
	 * @return - if it the same or not
	 */
	
	public boolean isEqual (Line line) {
		if(this.point1==line.point1 && this.point2==line.point2)
			return true;
		return false;
	}

	/**
	 * checks if 2 lines intersects
	 * @param line - the line
	 * @return - if intersects or not
	 */
	
	public boolean isIntersect(Line line) {
			boolean intersect=Line2D.linesIntersect(this.point1.x(),this.point1.y() ,this.point2.x(), this.point2.y(),
					line.point1.x(),line.point1.y() ,line.point2.x(), line.point2.y());
			if((intersect==true) && !(this.point1==line.point1 || this.point1==line.point2 || 
					this.point2==line.point1 || this.point2==line.point2)) {
				return true;
			}
		return false;
	}


}

