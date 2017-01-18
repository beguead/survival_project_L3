package items;

import utilities.Assets;
import utilities.Constants;

public class GreenCore extends Core {

	public GreenCore() {
		
		sprite = Assets.green_core;
		
	}

	@Override
	public float getDistance() { return Constants.GREEN_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.GREEN_CORE_CONE_DEGREE; }
}
