package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.ConeLight;
import screens.GameScreen;

public class WhiteCrystal extends Crystal {

	public WhiteCrystal(Body b) {
		
		cone_light = new ConeLight(GameScreen.ray_handler, 100, Color.WHITE, 5f, b.getPosition().x, b.getPosition().y, 0f, 10f);
		cone_light.setSoftnessLength(0.7f);
		
	}

}
