package lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.PointLight;
import screens.GameScreen;
import utilities.Constants;

public class Aura {

	private PointLight point_light;
	
	public Aura(Body body, Color c, float range) {
		
		point_light = new PointLight(GameScreen.ray_handler, 100, c, range, body.getPosition().x, body.getPosition().y);
		point_light.setXray(false);
		point_light.setSoftnessLength(0.1f);
		point_light.attachToBody(body);
		
	}
	
	public void dispose() { point_light.dispose(); }
	
	public void setDistance(float range) { point_light.setDistance(range); }
	
}
