package items;

import com.badlogic.gdx.graphics.Color;

import box2dLight.ConeLight;
import screens.GameScreen;
import utilities.Assets;
import utilities.Constants;

public class BlueCore extends Core {
	
	private ConeLight mirror_light;

	public BlueCore() {
		
		sprite = Assets.blue_core;
		mirror_light = new ConeLight(GameScreen.ray_handler, 25, Color.BLUE, 0f, body.getPosition().x, body.getPosition().y, 0f, 0f);
		mirror_light.setSoftnessLength(0.1f);
		
	}

	@Override
	public float getDistance() { return Constants.WHITE_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.WHITE_CORE_CONE_DEGREE; }
	
	public void setDirection(float direction) { mirror_light.setDirection(direction); }
	
}
