package Algorithms;

import java.util.Comparator;

public class DistanceComperator implements Comparator<FastestRoad>{

	@Override
	public int compare(FastestRoad r1, FastestRoad r2) {
		if(r1.getDis() > r2.getDis()) return 1;
		if(r1.getDis() < r2.getDis()) return -1;		
		return 0;
	}

}
