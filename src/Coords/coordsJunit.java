package Coords;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import Geom.Point3D;

	/**
	 * test unit
	 * @author yael hava and naama hartuv
	 */

class coordsJunit {

	@Test
	void testAdd() {
		Point3D p1 =new Point3D(32.103315, 35.209039, 670);
		Point3D vec =new Point3D(337.6989921, -359.2492069, -20);
		Point3D newPoint;
		MyCoords c = new MyCoords(0, 0, 0);
		newPoint  = c.add(p1, vec);

		Point3D check = new Point3D(32.10635200000035, 35.20522500000041, 650);
		assertEquals(newPoint.x(), check.x());
		assertEquals(newPoint.y(), check.y());
		assertEquals(newPoint.z(), check.z());
	}

	
	@Test
	void testDistance3d() {
		Point3D p1 =new Point3D(32.103315, 35.209039, 670);
		Point3D p2 = new Point3D(32.10635200000035, 35.20522500000041, 650);
		MyCoords c = new MyCoords(0, 0, 0);
		
		double check = c.distance3d(p1, p2);
		double expect = 493.05233183064564;
		assertEquals(check, expect); 
	}
	
	@Test
	void testVector3d() {
		Point3D p1 =new Point3D(32.103315, 35.209039, 670);
		Point3D p2 = new Point3D(32.10635200000035, 35.20522500000041, 650);
		MyCoords c = new MyCoords(0, 0, 0);
		
		Point3D expect = new Point3D(337.6989921000024,-359.2492069000009,-20.0);
		Point3D check = new Point3D(c.vector3D(p1, p2));

		assertEquals(expect.x(), check.x());
		assertEquals(expect.y(), check.y());
		assertEquals(expect.z(), check.z());
		}
	
	
	
	@Test
	void testAzimuth_elevation_dist() {
		Point3D p1 =new Point3D(32.103315, 35.209039, 670);
		Point3D p2 = new Point3D(32.10635200000035, 35.20522500000041, 650);
		MyCoords c = new MyCoords(0, 0, 0);
		
		double[] expect = {313.2304203328631, -2.324763517394867, 493.05233183064564};
		double[] check = c.azimuth_elevation_dist(p1, p2);

		assertEquals(Arrays.toString(expect), Arrays.toString(check)); 
		}
	
	
	@Test
	void testIsValid_GPS_Point() {
		Point3D p1 =new Point3D(32.103315, 35.209039, 670);
		Point3D p2 = new Point3D(-215.87463, 35.20522500000041, 650);
		Point3D p3 = new Point3D(15.87463, 35.20522500000041, -451);

		MyCoords c = new MyCoords(0, 0, 0);
		
		assertTrue(c.isValid_GPS_Point(p1));
		assertFalse(c.isValid_GPS_Point(p2));
		assertFalse(c.isValid_GPS_Point(p3));
	}
	
	
}
