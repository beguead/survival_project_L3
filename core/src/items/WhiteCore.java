package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class WhiteCore extends Core {
	
	private static final WhiteCore INSTANCE = new WhiteCore();
	
	private WhiteCore() { super(); }

	@Override
	public float getDistance() { return Constants.WHITE_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.WHITE_CORE_CONE_DEGREE; }
	
	@Override
	public Color getColor() { return Color.WHITE; }
	
    public static WhiteCore getInstance() { return INSTANCE; }
	
}