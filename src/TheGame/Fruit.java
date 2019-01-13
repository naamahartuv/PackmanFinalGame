package TheGame;

import java.util.ArrayList;

import Geom.Point3D;

/**
 * this class represents a fruit in the game
 * @author yael hava and naama hartuv
 */

public class Fruit {

	private FruitMetaData data;
	private Point3D point3D;
	private int ID;
	private double weight;

	/**
	 * constructor
	 */

	public Fruit() {
	}
	
	
	/**
	 * constructor
	 * @param point - the point
	 * @param data - the data
	 */

	public Fruit(Point3D point, FruitMetaData data) {
		this.point3D = point;
		this.data = data;

	}
	
	public boolean isExist(ArrayList<Fruit> fruitList) {
		return fruitList.contains(this);
	}

	/**
	 * constructor
	 * @param point - the point
	 * @param ID - the ID
	 */

	public Fruit(Point3D point, int ID) {
		this.point3D = point;
		//this.pixel = pixel;
		this.ID = ID;
	}

	/**
	 * constructor
	 * @param point - the point
	 */

	public Fruit(Point3D point) {
		this.point3D = point;

	}

	/**
	 * getter for data
	 * @return data
	 */

	public FruitMetaData getFruitData() {
		return data;
	}

	/**
	 * getter for point
	 * @return point
	 */

	public Point3D getPoint3D() {
		return point3D;
	}

	/**
	 * getter for id
	 * @return
	 */

	public int getID() {
		return ID;
	}

	/**
	 * getter for weight
	 * @return
	 */

	public double getWeight() {
		return weight;
	}


}
