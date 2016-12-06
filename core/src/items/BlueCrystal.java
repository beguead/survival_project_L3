package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.ConeLight;
import screens.GameScreen;

public class BlueCrystal extends Crystal {

	private ConeLight mirror_cone_light;
	
	public BlueCrystal() {
		
	}

	public void setPosition(Vector2 position) {
		
		mirror_cone_light.setPosition(position);
		
	}
	
	public void setDirection(float angle) {
		
		mirror_cone_light.setDirection(angle + 180);
		
	}
	
}
