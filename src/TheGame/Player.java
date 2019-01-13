package TheGame;

import Geom.Point3D;

/**
 * this class represents the player
 * @author yael hava and naama hartuv
 */

public class Player {

	private Point3D point;
	private int score, ID;
	private double speed, radius;
	
	/**
	 * constructor
	 * @param point - the point
	 */
	
	public Player(Point3D point) {
		this.point = point;
		this.score = 0;
	}
	
	/**
	 * constructor
	 * @param point - the point
	 * @param ID - the id
	 * @param speed - the speed
	 * @param radius - the radius
	 */
	
	public Player(Point3D point, int ID, double speed, double radius) {
		this.score = 0;
		this.point = point;
		this.ID = ID;
		this.speed = speed;
		this.radius = radius;
	}
	
	/**
	 * constructor
	 * @param s - the string create the element
	 */

	public Player(String[] s) {
		this.score = 0;
		point = new Point3D(Double.parseDouble(s[2]), Double.parseDouble(s[3]));
		ID = Integer.parseInt(s[1]);
		speed = Double.parseDouble(s[5]);
		radius = Double.parseDouble(s[6]);
	}
	
	/**
	 * copy constructor
	 * @param newPlayer - player
	 */
	public Player(Player newPlayer) {
		this.score = 0;
		this.point = newPlayer.point;
		this.ID = newPlayer.ID;
		this.speed = newPlayer.speed;
		this.radius = newPlayer.radius;
	}
	
	
	public Point3D getPoint() {
		return point;
	}


	public void setPoint(Point3D point) {
		this.point = point;
	}


	public int getScore() {
		return score;
	}


	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
	
}
