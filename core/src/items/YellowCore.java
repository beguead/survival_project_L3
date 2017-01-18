package items;

import utilities.Assets;
import utilities.Constants;

public class YellowCore extends Core {

	public YellowCore() {
		
		sprite = Assets.yellow_core;
		
	}
	
	@Override
	public float getDistance() { return Constants.YELLOW_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.YELLOW_CORE_CONE_DEGREE; }
	
}
