package items;

import utilities.Assets;
import utilities.Constants;

public class WhiteCore extends Core {

	public WhiteCore() {
		super();
		
		sprite = Assets.white_core;
		
	}

	@Override
	public float getDistance() { return Constants.WHITE_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.WHITE_CORE_CONE_DEGREE; }
	
}
