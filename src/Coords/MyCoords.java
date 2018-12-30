package Coords;

import java.util.Arrays;

import Geom.Point3D;

/**
 * This class represents a basic coordinate system converter, including:
 * 1. The 3D vector between two lat,lon, alt points 
 * 2. Adding a 3D vector in meters to a global point.
 * 3. convert a 3D vector from meters to polar coordinates
 * @author yael hava and naama hartuv
 */

public class MyCoords implements coords_converter{
	
	private final int EarthRadius = 6371000;
	private  double lonNorm;
	private double x;		
	private double y;
	private double z;

	/**
	 * constructor
	 * @param x - represent the x
	 * @param y - represent the y 
	 * @param z - represent the z
	 */

	public MyCoords(int x, int y, int z) { 		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/** 
	 * computes a new point which is the gps point transformed by a 3D vector (in meters)
	 * @param gps: the gps point
	 * @param local_vector_in_meter: the vector value in mater
	 * @return p: the new point after adding the vector to the gps
	 */

	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double x= ((Math.asin(local_vector_in_meter.x()/EarthRadius))/Math.PI)*180;
		lonNorm = Math.cos((gps.x() * Math.PI)/180);
		double y= ((Math.asin(local_vector_in_meter.y()/(EarthRadius*lonNorm)))/Math.PI)*180;
		double z= local_vector_in_meter.z();
		Point3D p= new Point3D(x + gps.x(),y + gps.y(),z + gps.z());


		return p;
	}
	/**
	 *  computes the 3D distance (in meters) between the two gps like points
	 *  @param gps0 - the first point
	 *  @param gps1 - the second point
	 *  @return the distance between the two points
	 */

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		double distance = 0;
		double x = Math.sin(Math.PI * (gps1.x() - gps0.x()) /180) * EarthRadius;
		lonNorm = Math.cos((gps0.x() * Math.PI)/180);
		double y = Math.sin(Math.PI * (gps1.y() - gps0.y()) /180) * EarthRadius * lonNorm;

		distance = Math.sqrt(x*x + y*y);
		return distance;
	}

	/**
	 *  computes the 3D vector (in meters) between two gps like points
	 *  @param gps0 - the first point
	 *  @param gps1 - the second point  
	 *  @return the vector between the two points
	 */


	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double x = Math.sin(Math.PI * (gps1.x() - gps0.x()) /180) * EarthRadius;
		lonNorm = Math.cos((gps0.x() * Math.PI)/180);
		double y = Math.sin(Math.PI * (gps1.y() - gps0.y()) /180) * EarthRadius * lonNorm;
		double z = gps1.z() - gps0.z();
		Point3D vectorInMeter = new Point3D(x, y, z);		
		return vectorInMeter;
	}

	/** computes the polar representation of the 3D vector be gps0-->gps1 
	 *	@param gps0 - the first point
	 *  @param gps1 - the second point  
	 *  @return the azimuth, elevation and distance between the two points
	 */

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		double deltaY = Math.toRadians(gps1.y()) - Math.toRadians(gps0.y());
		double deltaZ = gps1.z() - gps0.z();

		double azimuthLeft = Math.sin(deltaY) * Math.cos(Math.toRadians(gps1.x()));
		double azimuthRight = (Math.cos(Math.toRadians(gps0.x())) * Math.sin(Math.toRadians(gps1.x()))) -
				(Math.sin(Math.toRadians(gps0.x())) * Math.cos(Math.toRadians(gps1.x())) * Math.cos(deltaY));

		double azimuth = Math.toDegrees(Math.atan2(azimuthLeft, azimuthRight));
		if(azimuth < 0) {
			azimuth += 360;
		}

		double distance = distance3d(gps0, gps1);
		double elevation = Math.toDegrees(Math.asin((deltaZ) / distance));

		double[] azimuthElevationDist = {azimuth, elevation, distance};

		return azimuthElevationDist;
	}

	/**
	 * return true if this point is a valid lat, lon, alt coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p - the point to check if is in the valid range
	 * @return true if is in the range false if not
	 */

	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if((p.x() <= 180 && p.x() >= -180) &&
				(p.y() <= 90 && p.y() >= -90) &&
				(p.z() <= 10000 && p.z() >= -450)) {
			return true;
		}
		return false;
	}

	/**
	 * prints the coords as a string
	 */
	
	public String toString() {			//לא בטוח שצריך ף עשינו אולי רק בשביל המיין
		return "(" + this.x + "," + this.y + "," + this.z + ")";
	}

}