package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class PurpleCore extends Core {
	
	@Override
	public float getDistance() { return Constants.PURPLE_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.PURPLE_CORE_CONE_DEGREE; }
	
	@Override
	public Color getColor() { return Color.PURPLE; }
	
}