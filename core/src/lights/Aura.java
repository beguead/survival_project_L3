package lights;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.PointLight;
import screens.GameScreen;

public class Aura {

	private PointLight point_light;
	
	public Aura(Body body, Color c, float range) {
		
		point_light = new PointLight(GameScreen.ray_handler, 100, c, range, body.getPosition().x, body.getPosition().y);
		point_light.setXray(false);
		point_light.setSoftnessLength(0.3f);
		point_light.attachToBody(body);
		
	}
	
	public void dispose() { point_light.dispose(); }
	
	public void setActive(boolean active) { point_light.setActive(active); }
	
	public void setColor(Color color) { point_light.setColor(color); }
	
	public void setDistance(float range) { point_light.setDistance(range); }
	
}
