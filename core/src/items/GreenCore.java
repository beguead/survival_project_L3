package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class GreenCore extends Core {

	@Override
	public float getDistance() { return Constants.GREEN_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.GREEN_CORE_CONE_DEGREE; }
	
	@Override
	public Color getColor() { return Color.LIME; }
	
}
