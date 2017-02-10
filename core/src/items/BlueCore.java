package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import box2dLight.ConeLight;
import screens.GameScreen;
import utilities.Constants;

public final class BlueCore extends Core {
	
	private ConeLight mirror_light;
	
	public BlueCore() {
		super();
		
		mirror_light = new ConeLight(GameScreen.ray_handler, 100, getColor(), getDistance(), body.getPosition().x, body.getPosition().y, 0, getConeDegree());
		mirror_light.setSoftnessLength(0.1f);
		mirror_light.setActive(false);
		
	}

	@Override
	public float getDistance() { return Constants.BLUE_CORE_DISTANCE; }

	@Override
	public float getConeDegree() { return Constants.BLUE_CORE_CONE_DEGREE; }

	@Override
	public Color getColor() { return Color.ROYAL; }
	
	public void setMirrorLightActive(boolean active) { mirror_light.setActive(active); }
	
	public void majMirrorLightDirection(float direction) { mirror_light.setDirection(direction + 180); }
	
	@Override
	public void setPosition(Vector2 position) {
		super.setPosition(position);
		
		mirror_light.setPosition(position);
		
	}
	
}
