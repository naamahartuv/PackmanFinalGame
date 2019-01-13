package Coords;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Geom.Point3D;

/**
 * this class represents a map, and converts between pixsels and packmans
 * @author yael hava and naama hartuv
 *
 */

public class Map {

	private Point3D startPoint = new Point3D(35.212418,32.105765);
	private Point3D endPoint = new Point3D(35.202484,32.101874);
	private BufferedImage map;

	
	/**
	 * constructor
	 * @param path - the image path
	 */
	
	public Map(String path) {
		try {
			map = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map() {
	}

	/**
	 * calclulates the normal of a point
	 * @param point the point we wants to normalize
	 * @param start - start point
	 * @param end - end point
	 * @return - the normalize of the point
	 */
	
	private Point3D normalPoint(Point3D point, Point3D start ,Point3D end) { ///////done
		double x = Math.abs((point.x()-end.x())/(start.x()-end.x()));
		double y = Math.abs((point.y()-end.y())/(start.y()-end.y()));

		return new Point3D(x, y);
	}
	
	/**
	 * convert from pixel to coordinate
	 * @param current - the point to convert
	 * @param Xstart - x of start point
	 * @param Ystart - y of start point
	 * @return - the coordinate of the point
	 */

	public Point3D pixel2GPS(Point3D current, int Xstart, int Ystart) { 
		Point3D temp =normalPoint(current, new Point3D(Xstart, Ystart), new Point3D(0, 0));
		Point3D result = new Point3D(temp.x() * (Math.abs(startPoint.x() - endPoint.x())) + endPoint.x(),
				temp.y() * (Math.abs(startPoint.y() - endPoint.y())) + endPoint.y());

		double fix = startPoint.y() - result.y();
		result = new Point3D(endPoint.y() + fix, result.x());
	
		return result;
	}
	
/**
 * convert from coordinate to pixel
 * @param current - the point to convert
 * @param start- start point
 * @param end- end point
 * @return the point in pixel
 */
	public Point3D GPS2Pixel(Point3D current,int start, int end) {
		current = new Point3D(current.y(), current.x());
		Point3D temp = normalPoint(current, startPoint, endPoint);

		Point3D newPoint = new Point3D(temp.x() * (Math.abs(start)), temp.y() * (Math.abs(end)));
		if(temp.y() >= 0.5) {
			double fix = end - newPoint.y();
			newPoint = new Point3D(newPoint.x(), fix);
		}
		else {
			double fix = newPoint.y();
			newPoint = new Point3D(newPoint.x(), end - fix);
		}
		Point3D pixelPoint = new Point3D(newPoint.x(), newPoint.y());
		return pixelPoint;
	}

	
	/**
	 * distance between 2 pixels
	 * @param p1 - point 1
	 * @param p2 - point 2
	 * @return - the distance
	 */
	
	public double pixelDistance (Point3D p1, Point3D p2) {
			Point3D p1GPS = pixel2GPS(p1, map.getWidth(), map.getHeight());
			Point3D p2GPS = pixel2GPS(p2, map.getWidth(), map.getHeight());
			MyCoords m = new MyCoords(0, 0, 0);
			double dis = m.distance3d(p1GPS, p2GPS);
			
			return dis;
		}
	
	/**
	 * calculates the angel 
	 * @param p1 - point 1
	 * @param p2 - point 2
	 * @return the angel
	 */
		
		public double pixelAngle (Point3D p1, Point3D p2) {
			Point3D p1GPS= pixel2GPS(p1, map.getWidth(), map.getHeight());
			Point3D p2GPS= pixel2GPS(p2, map.getWidth(), map.getHeight());
			MyCoords m= new MyCoords(0, 0, 0);
			double[] AED= m.azimuth_elevation_dist(p1GPS, p2GPS);
		
			return AED[0];
		}
	
	
	public BufferedImage getMap() {
		return map;
	}



}