package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class YellowCore extends Core {
	
	@Override
	public float getDistance() { return Constants.YELLOW_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.YELLOW_CORE_CONE_DEGREE; }
	
	@Override
	public Color getColor() { return Color.GOLD; }
	
}
