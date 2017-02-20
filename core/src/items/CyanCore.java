package items;

import com.badlogic.gdx.graphics.Color;

import utilities.Constants;

public class CyanCore extends Core {
	
	private static final CyanCore INSTANCE = new CyanCore();
	
	private CyanCore() { super(); }

	@Override
	public float getDistance() { return Constants.CYAN_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.CYAN_CORE_CONE_DEGREE; }
	
	@Override
	public Color getColor() { return Color.CYAN; }
	
    public static CyanCore getInstance() { return INSTANCE; }
	
}
