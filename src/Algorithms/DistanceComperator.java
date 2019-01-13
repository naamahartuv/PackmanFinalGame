package Algorithms;

import java.util.Comparator;

/**
 * this class compare the fastestRoad objects by distance
 * @author yael hava and naama hartuv
 */

public class DistanceComperator implements Comparator<FastestRoad>{

	/**
	 * compare the fastestRoad objects by distance
	 */
	
	@Override
	public int compare(FastestRoad r1, FastestRoad r2) {
		if(r1.getDis() > r2.getDis()) return 1;
		if(r1.getDis() < r2.getDis()) return -1;
		else {
			return 0;
		}
	}

}
