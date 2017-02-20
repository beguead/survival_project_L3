package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class OrangeCore extends Core {

	@Override
	public float getDistance() { return Constants.ORANGE_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.ORANGE_CORE_CONE_DEGREE; }
	
	@Override
	public Color getColor() { return Color.ORANGE; }
	
}
