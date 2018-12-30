package TheGame;

/**
 * the data of a fruit
 * @author yael hava and naama hartuv
 *
 */

public class FruitMetaData {

	private double weight;
	private int ID;
	
	/**
	 * constructor
	 * @param s - the string line
	 */
	
	public FruitMetaData(String[] s) {
		weight = Double.parseDouble(s[5]);
		ID = Integer.parseInt(s[1]);
	}
	
	
	
	public double getWeight() {
		return weight;
	}

	public int getID() {
		return ID;
	}
	
	
	
}
