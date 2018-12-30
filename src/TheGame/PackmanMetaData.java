package TheGame;

/**
* the data of a packman
* @author yael hava and naama hartuv
*
*/

public class PackmanMetaData {

	private double moveAbility, radius;
	private int ID;
	
	/**
	 * constructor
	 * @param s - string line
	 */
	
	public PackmanMetaData(String[] s) {
		moveAbility = Double.parseDouble(s[5]);
		radius = Double.parseDouble(s[6]);
		ID = Integer.parseInt(s[1]);
	}
	
	
	public double getMoveAbility() {
		return moveAbility;
	}

	public double getRadius() {
		return radius;
	}

	public int getID() {
		return ID;
	}
	
}
